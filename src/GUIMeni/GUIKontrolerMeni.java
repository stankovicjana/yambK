package GUIMeni;

import DomenskiObjekat.Korisnik;
import GUIIzmenaPodataka.JFX06;
import GUIKorisnikLogin.JFX01;
import GUIMeni.Osluskivaci.OsluskivacPoeni;
import GUIMeni.Osluskivaci.OsluskivacPravilaIgre;
import GUIMeni.Osluskivaci.OsluskivacSacuvajIgru;
import GUIPoeni.JFX05;
import GUIPravilaIgre.JFX04;
import TransferObjekat.GenerickiTransferObjekat;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUIKontrolerMeni {

    enum ColumnRule {
        TOP_DOWN, BOTTOM_UP, FREE, ANNOUNCEMENT
    }

    Socket soket;
    ObjectOutputStream out;
    ObjectInputStream in;
    GenerickiTransferObjekat gto;
    Korisnik ulogovaniKorisnik;
    public FXMLDocumentController fxcon;
    private static final int COLUMN_COUNT = 5;

    ToggleButton[] diceButtons = new ToggleButton[6];
    int[] diceValues = new int[6];

    int rollsLeft = 3;
    boolean hasPlayedThisTurn = false;

    boolean najavaAktivna = false;
    TextField najavljenoPolje = null;

    TextField lastClickedField = null;

    boolean allowChooseAfterThird = false;

    Map<TextField, ColumnRule> fieldRules = new HashMap<>();

    private static final boolean[] ROWS_PLAYABLE = {
        true, true, true, true, true, true, // 0..5 -> 1..6
        false, // 6 -> Σ (gornja)
        true, true, // 7 MAX, 8 MIN
        false, // 9 -> Σ (MAX-MIN suma)
        true, true, true, true, // 10..13 -> KENTA, FUL, POKER, JAMB
        false // 14 -> Σ (ukupna)
    };

    Random random = new Random();

    public GUIKontrolerMeni(FXMLDocumentController fxcon) throws IOException {
        this.fxcon = fxcon;
        this.gto = new GenerickiTransferObjekat();

        // Akcije iz menija / dugmadi
        this.fxcon.pravilaIgre.setOnAction(new OsluskivacPravilaIgre(this));
        this.fxcon.poeniKorisnika.setOnAction(new OsluskivacPoeni(this));
        // ako koristiš "Sledeći"
        // this.fxcon.next.setOnAction(new OsluskivacZavrsiPotez(this));

        // “Najavi polje” -> naš handler
        this.fxcon.najava.setOnAction(e -> handleNajaviPolje());
    }

    public void init() {
        diceButtons[0] = fxcon.dice0;
        diceButtons[1] = fxcon.dice1;
        diceButtons[2] = fxcon.dice2;
        diceButtons[3] = fxcon.dice3;
        diceButtons[4] = fxcon.dice4;
        diceButtons[5] = fxcon.dice5;

        startNewTurn();     // single-player start
        setupTable();
        updateRollLabel();
        updateSumLabel();

        for (ToggleButton btn : diceButtons) {
            btn.setDisable(false);
            btn.setOnAction(e -> updateSumLabel());
        }
    }

    private void startNewTurn() {
        rollsLeft = 3;
        hasPlayedThisTurn = false;
        najavaAktivna = false;
        najavljenoPolje = null;
        lastClickedField = null;
        allowChooseAfterThird = false;

        for (int i = 0; i < 6; i++) {
            diceButtons[i].setDisable(false);
            diceButtons[i].setSelected(false);
            diceButtons[i].setText("?");
            diceValues[i] = 0;
        }
        // Najava je dozvoljena tek nakon prvog bacanja
        fxcon.najava.setDisable(true);

        updateRollLabel();
        updateSumLabel();
    }

    private void updateSumLabel() {
        int sum = 0;
        for (int i = 0; i < 6; i++) {
            if (diceButtons[i].isSelected()) {
                sum += diceValues[i];
            }
        }
        fxcon.sumLabel.setText(String.valueOf(sum));
    }

    private void updateRollLabel() {
        fxcon.rollCountLabel.setText(String.valueOf(rollsLeft));
    }

    private void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Obaveštenje");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    private void setupTable() {
        String[] columns = {"↓", "↑", "↕", "N"};
        String[] rows = {
            "1", "2", "3", "4", "5", "6", "Σ", "MAX", "MIN", "Σ",
            "KENTA", "FUL", "POKER", "JAMB", "Σ"
        };

        GridPane jambTable = fxcon.jambTable;
        jambTable.getChildren().clear();
        fieldRules.clear();

        // Header kolone
        for (int col = 0; col < columns.length; col++) {
            Label header = new Label(columns[col]);
            jambTable.add(header, col + 1, 0);
        }

        // Redovi + polja
        for (int row = 0; row < rows.length; row++) {
            Label rowHeader = new Label(rows[row]);
            jambTable.add(rowHeader, 0, row + 1);

            for (int col = 0; col < columns.length; col++) {
                TextField field = new TextField();
                field.setEditable(true);
                field.setPrefWidth(50);

                // Zaključaj Σ redove
                if (row == 6 || row == 9 || row == 14) {
                    field.setEditable(false);
                }

                // Pravilo kolone
                ColumnRule rule;
                switch (col) {
                    case 0:
                        rule = ColumnRule.TOP_DOWN;
                        break;
                    case 1:
                        rule = ColumnRule.BOTTOM_UP;
                        break;
                    case 2:
                        rule = ColumnRule.FREE;
                        break;
                    case 3:
                        rule = ColumnRule.ANNOUNCEMENT;
                        break;
                    default:
                        rule = ColumnRule.FREE;
                }
                fieldRules.put(field, rule);

                // Klik na polje
                field.setOnMouseClicked(event -> {
                    lastClickedField = field;

                    // Ako smo završili 3. bacanje bez najave -> klik upisuje (ako je polje dozvoljeno)
                    if (allowChooseAfterThird && isFieldAllowed(field) && field.isEditable() && field.getText().isEmpty()) {
                        int[] chosenFive = getSelectedFiveDiceOrAutoPick();
                        int rezultat = izracunajPoPolju(chosenFive, field);
                        field.setText(String.valueOf(rezultat));
                        hasPlayedThisTurn = true;
                        allowChooseAfterThird = false;
                        startNewTurn();
                    }
                });

                jambTable.add(field, col + 1, row + 1);
            }
        }
    }

    // Provera da li sme da se popuni zadato polje (po pravilima kolone)
    private boolean isFieldAllowed(TextField field) {
        ColumnRule rule = fieldRules.get(field);
        if (rule == null) {
            return false;
        }

        Integer rowIdxObj = GridPane.getRowIndex(field);
        Integer colIdxObj = GridPane.getColumnIndex(field);
        if (rowIdxObj == null || colIdxObj == null) {
            return false;
        }

        int rowIndex = rowIdxObj - 1; // jer 0 je header red
        int colIndex = colIdxObj - 1; // jer 0 je header kolona

        switch (rule) {
            case TOP_DOWN:
                return rowIndex == getNextTopDownRow(colIndex);
            case BOTTOM_UP:
                return rowIndex == getNextBottomUpRow(colIndex);
            case FREE:
                return true;
            case ANNOUNCEMENT:
                // Najava mora biti aktvna i baš to polje najavljeno
                return najavaAktivna && field == najavljenoPolje;
            default:
                return false;
        }
    }

    // Sledeći validan red u TOP_DOWN (prvi prazni od vrha koji je igriv)
    private int getNextTopDownRow(int col) {
        for (int r = 0; r < ROWS_PLAYABLE.length; r++) {
            if (ROWS_PLAYABLE[r] && isRowEmpty(r, col)) {
                return r;
            }
        }
        return -1;
    }

    // Sledeći validan red u BOTTOM_UP (prvi prazni od dna koji je igriv)
    private int getNextBottomUpRow(int col) {
        for (int r = ROWS_PLAYABLE.length - 1; r >= 0; r--) {
            if (ROWS_PLAYABLE[r] && isRowEmpty(r, col)) {
                return r;
            }
        }
        return -1;
    }

    // Da li je polje u [row,col] prazno
    private boolean isRowEmpty(int row, int col) {
        for (Map.Entry<TextField, ColumnRule> e : fieldRules.entrySet()) {
            TextField tf = e.getKey();
            Integer r = GridPane.getRowIndex(tf);
            Integer c = GridPane.getColumnIndex(tf);
            if (r == null || c == null) {
                continue;
            }
            int rr = r - 1, cc = c - 1;
            if (rr == row && cc == col) {
                return tf.getText().isEmpty();
            }
        }
        return false;
    }

    public void rollDice() {
        if (rollsLeft == 0 || hasPlayedThisTurn) {
            return;
        }

        for (int i = 0; i < 6; i++) {
            // zadržane (selected) ne menjamo
            if (!diceButtons[i].isSelected()) {
                diceValues[i] = random.nextInt(6) + 1;
                diceButtons[i].setText(String.valueOf(diceValues[i]));
            }
        }

        // posle prvog bacanja -> dozvoli dugme "Najavi polje"
        if (rollsLeft == 3) {
            fxcon.najava.setDisable(false);
        }

        rollsLeft--;
        updateRollLabel();
        updateSumLabel();

        if (rollsLeft == 0) {
            // Posle 3. bacanja
            if (najavaAktivna && najavljenoPolje != null) {
                // Auto-upis u najavljeno polje
                int[] chosenFive = getSelectedFiveDiceOrAutoPick();
                int rezultat = izracunajPoPolju(chosenFive, najavljenoPolje);
                najavljenoPolje.setText(String.valueOf(rezultat));
                hasPlayedThisTurn = true;
                startNewTurn();
            } else {
                // Bez najave -> korisnik ručno klikne dozvoljeno polje
                allowChooseAfterThird = true;
                showAlert("Izaberi polje za upis rezultata.");
            }
        }
    }

    // Biramo tačno 5 kockica: prioritet selektovanih, pa prve preostale
    private int[] getSelectedFiveDiceOrAutoPick() {
        int countSelected = 0;
        for (ToggleButton btn : diceButtons) {
            if (btn.isSelected()) {
                countSelected++;
            }
        }

        int[] chosen = new int[5];
        int idx = 0;

        // selektovane
        if (countSelected > 0) {
            for (int i = 0; i < 6 && idx < 5; i++) {
                if (diceButtons[i].isSelected()) {
                    chosen[idx++] = diceValues[i];
                }
            }
        }
        // dopuna
        for (int i = 0; i < 6 && idx < 5; i++) {
            if (!diceButtons[i].isSelected()) {
                chosen[idx++] = diceValues[i];
            }
        }
        return chosen;
    }

    // Računanje po polju
    private int izracunajPoPolju(int[] kocke, TextField polje) {
        Integer rowIndexObj = GridPane.getRowIndex(polje);
        if (rowIndexObj == null) {
            return 0;
        }
        int red = rowIndexObj - 1;
        int zbir = 0;

        switch (red) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                int target = red + 1;
                for (int k : kocke) {
                    if (k == target) {
                        zbir += k;
                    }
                }
                break;
            case 7: // MAX
                for (int k : kocke) {
                    zbir += k;
                }
                break;
            case 8: // MIN
                for (int k : kocke) {
                    zbir += k;
                }
                break;
            case 10: // KENTA
                java.util.Arrays.sort(kocke);
                String s = java.util.Arrays.toString(kocke);
                if (s.equals("[1, 2, 3, 4, 5]") || s.equals("[2, 3, 4, 5, 6]")) {
                    int brojBacanja = 3 - rollsLeft + 1; // 1 = prvi put, 2 = drugi, 3 = treći
                    if (brojBacanja == 1) {
                        zbir = 66;
                    } else if (brojBacanja == 2) {
                        zbir = 56;
                    } else {
                        zbir = 46;
                    }
                }
                break;
            case 11: // FUL
                if (jeFul(kocke)) {
                    for (int k : kocke) {
                        zbir += k;
                    }
                    zbir += 30;
                }
                break;
            case 12: // POKER
                if (jePoker(kocke)) {
                    for (int k : kocke) {
                        zbir += k;
                    }
                    zbir += 40;
                }
                break;
            case 13: // JAMB
                if (jeJamb(kocke)) {
                    for (int k : kocke) {
                        zbir += k;
                    }
                    zbir += 50;
                }
                break;
            default:
                zbir = 0;
        }
        return zbir;
    }

    private boolean jePoker(int[] k) {
        for (int i = 1; i <= 6; i++) {
            int cnt = 0;
            for (int v : k) {
                if (v == i) {
                    cnt++;
                }
            }
            if (cnt == 4 || cnt == 5) {
                return true;
            }
        }
        return false;
    }

    private boolean jeJamb(int[] k) {
        int first = k[0];
        for (int v : k) {
            if (v != first) {
                return false;
            }
        }
        return true;
    }

    private boolean jeFul(int[] k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int v : k) {
            map.put(v, map.getOrDefault(v, 0) + 1);
        }
        return map.containsValue(3) && map.containsValue(2);
    }

    void handleNajaviPolje() {
        // Najava je dozvoljena tek nakon prvog bacanja
        if (rollsLeft == 3) {
            showAlert("Najava je moguća tek nakon prvog bacanja.");
            return;
        }
        if (lastClickedField == null) {
            showAlert("Klikni prvo na polje koje želiš da najaviš.");
            return;
        }
        if (!lastClickedField.isEditable()) {
            showAlert("To polje je zaključano (Σ/MAX/MIN). Odaberi neko drugo.");
            return;
        }
        if (!lastClickedField.getText().isEmpty()) {
            showAlert("To polje već ima vrednost. Odaberi prazno polje.");
            return;
        }

        // Najava sme samo u 4. koloni (ANNOUNCEMENT),
        // ali BEZ ograničenja redosleda (bilo koji red).
        ColumnRule rule = fieldRules.get(lastClickedField);
        if (rule != ColumnRule.ANNOUNCEMENT) {
            showAlert("Najava je dostupna samo u četvrtoj koloni (N).");
            return;
        }

        najavljenoPolje = lastClickedField;
        najavaAktivna = true;
        showAlert("Polje je uspešno najavljeno! Posle trećeg bacanja biće automatski upisano.");
    }

    public void next() {
    }

    public void setMyTurn(boolean turn) {
        startNewTurn();
    }

    private int izracunajKrajnjiRezultat() {
        GridPane jambTable = fxcon.jambTable;

        // Kolone počinju od 1 (0 je labela redova)
        for (int col = 1; col <= COLUMN_COUNT - 1; col++) {
            int sumaPrvih6 = 0;
            for (int row = 1; row <= 6; row++) { // 1-6 red
                TextField tf = getTextFieldAt(jambTable, col, row);
                sumaPrvih6 += parseIntSafe(tf.getText());
            }
            getTextFieldAt(jambTable, col, 7).setText(String.valueOf(sumaPrvih6)); // Σ prvi deo

            // MAX i MIN
            int max = parseIntSafe(getTextFieldAt(jambTable, col, 8).getText());
            int min = parseIntSafe(getTextFieldAt(jambTable, col, 9).getText());
            int brojJedinica = parseIntSafe(getTextFieldAt(jambTable, col, 1).getText());
            int sumaMaxMin = (max - min) * brojJedinica;
            getTextFieldAt(jambTable, col, 10).setText(String.valueOf(sumaMaxMin)); // Σ drugi deo

            // KENTA, FUL, POKER, JAMB
            int kenta = parseIntSafe(getTextFieldAt(jambTable, col, 11).getText());
            int ful = parseIntSafe(getTextFieldAt(jambTable, col, 12).getText());
            int poker = parseIntSafe(getTextFieldAt(jambTable, col, 13).getText());
            int jamb = parseIntSafe(getTextFieldAt(jambTable, col, 14).getText());
            int sumaSpec = kenta + ful + poker + jamb;
            getTextFieldAt(jambTable, col, 15).setText(String.valueOf(sumaSpec)); // Σ treći deo
        }

        // Konačni rezultat (sabiranje svih Σ)
        int konacno = 0;
        for (int col = 1; col <= COLUMN_COUNT - 1; col++) {
            konacno += parseIntSafe(getTextFieldAt(jambTable, col, 7).getText());
            konacno += parseIntSafe(getTextFieldAt(jambTable, col, 10).getText());
            konacno += parseIntSafe(getTextFieldAt(jambTable, col, 15).getText());
        }

        fxcon.sumLabel.setText("Konačan rezultat: " + konacno);
        return konacno;
    }

// Pomoćna metoda za bezbedno parsiranje
    private int parseIntSafe(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }

// Pomoćna metoda za pronalaženje TextField-a u GridPane-u
    private TextField getTextFieldAt(GridPane grid, int col, int row) {
        for (javafx.scene.Node node : grid.getChildren()) {
            Integer c = GridPane.getColumnIndex(node);
            Integer r = GridPane.getRowIndex(node);
            if (c != null && r != null && c == col && r == row && node instanceof TextField) {
                return (TextField) node;
            }
        }
        return new TextField();
    }

    public void handleFinishGame() {
        int poeni = izracunajKrajnjiRezultat();
        try {
            ulogovaniKorisnik.setBrojPoena(poeni);
            gto.gdo = new Korisnik();
            gto.gdo = ulogovaniKorisnik;
            pozivSO(nazivSOSacuvajIgru());

            if (gto.getSignal()) {
                poruka("Sistem je uspesno uneo poene");
            } else {
                poruka("Sistem ne može da nađe igrača na osnovu unetih vrednosti za prijavljivanje");

            }
        } catch (IOException ex) {
            Logger.getLogger(GUIKontrolerMeni.class.getName()).log(Level.SEVERE, null, ex);
        }
        showAlert("Igra završena! Rezultat je izračunat.");
    }

    public void pravilaIgre() {
        try {
            System.out.println("Ovo su pravila igre");
            JFX04 jfx04 = new JFX04();
            jfx04.start(new Stage());
        } catch (Exception ex) {
            System.err.println("Greska prilikom pozivanja pravila igre u GUIKontrolerGlavniMeni " + Arrays.toString(ex.getStackTrace()));
        }
    }

    public void poruka(String poruka) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Poruka:");
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(poruka);
        infoAlert.showAndWait();
    }

    public boolean porukaDaNe(String poruka) {
        Alert alert = new Alert(AlertType.CONFIRMATION, poruka, ButtonType.YES, ButtonType.NO);
        alert.setTitle("Poruka:");
        alert.setHeaderText(null);
        alert.showAndWait();
        return (alert.getResult() == ButtonType.YES);
    }

    public void zapocniIgru(Korisnik ulogovanKorisnik) {

    }

    public void prikaziPoene() {
        try {
            JFX05 jfx05 = new JFX05();
            jfx05.start(new Stage());
            jfx05.postaviPodatke(fxcon.vratiUlogovanogKorisnika());
        } catch (Exception ex) {
            System.err.println("Greska u GUIKontrolerGlavniMeni, metoda prikazi moj profil" + Arrays.toString(ex.getStackTrace()));
        }
    }

    private String nazivSOUpisiPoene() {
        return "upisiPoene";
    }

    private String nazivSOSacuvajIgru() {
        return "sacuvajIgru";
    }

    public void pozivSO(String nazivSO) throws IOException {
        gto.setNazivOperacije(nazivSO);

        out.reset();          // poništi cache referenci u streamu

        out.writeObject(gto);
        out.flush();

        try {
            gto = (GenerickiTransferObjekat) in.readObject();
        } catch (IOException ex) {
            System.err.println("Greska kod ucitavanja objekta in read object metoda" + ex.getStackTrace());
        } catch (ClassNotFoundException ex) {

            System.err.println("Klasa nije pronadjena");
        }
    }

    void upisiPoene(Korisnik ulogovanKorisnik) {
        gto.setDK(ulogovanKorisnik);
        if (gto.getSignal()) {
            poruka("Poeni su uspesno uneti. Ukupan broj poena je: " + fxcon.vratiUlogovanogKorisnika().getBrojPoena());
        } else {
            poruka("Poeni nisu uspesno uneti!");
        }
    }

    public void izmenaPodataka() {
        try {
            JFX06 jfx06 = new JFX06(fxcon.vratiUlogovanogKorisnika());
            jfx06.start(new Stage());
        } catch (Exception ex) {
            System.err.println("Greska prilikom pozivanja izmene podataka u GUIKontrolerGlavniMeni " + Arrays.toString(ex.getStackTrace()));
        }
    }

    public Socket getSoket() {
        return soket;
    }

    public void setSoket(ObjectInputStream in, ObjectOutputStream out) {
        this.in = in;
        this.out = out;
        System.out.println("U meniju" + this.in + this.out);

    }

}
