package GUIPoeni;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLDocumentController {

    GUIKontrolerPoeni kngui;
    
    @FXML
    public Label labelaIgrac;
    @FXML
    public Label labelaBrojPoena;
    @FXML
    public Button ok;
    public Stage stage;

    @FXML
    public void izlazIzPrograma() {
        stage.close();

    }

    @FXML
    public void initialize() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, IOException {
        kngui = new GUIKontrolerPoeni(this);

    }

    void postaviStage(Stage stage) {
        this.stage = stage;
    }

    public void zatvoriFormu() {
        stage.close();
    }


    void prikaziPoene(DomenskiObjekat.Korisnik ulogovanKorisnik) {
        this.labelaIgrac.setText(ulogovanKorisnik.getIme() + " " + ulogovanKorisnik.getPrezime());
        this.labelaBrojPoena.setText(ulogovanKorisnik.getBrojPoena() + "");
    }

}
