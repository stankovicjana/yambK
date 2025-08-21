package GUIKorisnikLogin;

import DomenskiObjekat.Korisnik;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLDocumentController {

    @FXML
    public TextField korisnickoIme;
    @FXML
    public PasswordField sifra;
    @FXML
    public Button prijaviKorisnika;
    @FXML
    public Button kreirajKorisnika;
    @FXML
    GUIKontrolerLogin kngui;

    @FXML
    public void initialize() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, IOException {
        kngui = new GUIKontrolerLogin(this);
        kngui.autowireButtons(this);
    }

    public Stage stage;

    void postaviStage(Stage stage) {
        this.stage = stage;
    }

    public void zatvoriFormu() {
        stage.close();
    }

    @FXML
    private void onKreirajKorisnika(ActionEvent e) {
        kngui.registrujKorisnika();
    }

    @FXML
    private void onPrijaviKorisnika() throws IOException {
        kngui.prijaviKorisnika();

    }

}
