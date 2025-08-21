package GUIIzmenaPodataka;

import DomenskiObjekat.Korisnik;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLDocumentController {

    Korisnik ulogovanKorisnik;

    @FXML
    public Label ime;

    @FXML
    public Label prezime;

    @FXML
    public TextField korisnickoIme;

    @FXML
    public PasswordField sifra;

    @FXML
    public Button sacuvajIzmene;

    public GUIKontrolerIzmenaPodataka kngui;

    @FXML
    public void initialize() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, IOException, FileNotFoundException, ClassNotFoundException {
        try {
            kngui = new GUIKontrolerIzmenaPodataka(this);
            kngui.prikaziKorinsika(ulogovanKorisnik);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }

    public Stage stage;

    void postaviStage(Stage stage) {
        this.stage = stage;
    }

    public void zatvoriFormu() {
        stage.close();
    }

    public Korisnik getUlogovanKorisnik() {
        return ulogovanKorisnik;
    }

    public void setUlogovanKorisnik(Korisnik ulogovanKorisnik) {
        this.ulogovanKorisnik = ulogovanKorisnik;
    }

    void postaviPodatke() {
        this.ime.setText(ulogovanKorisnik.getIme());
        this.prezime.setText(ulogovanKorisnik.getPrezime());
        this.korisnickoIme.setText(ulogovanKorisnik.getKorisnickoIme());

    }

}
