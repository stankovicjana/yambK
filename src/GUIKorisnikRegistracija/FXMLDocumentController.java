package GUIKorisnikRegistracija;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLDocumentController {

    @FXML
    public TextField ime;

    @FXML
    public TextField prezime;

    @FXML
    public TextField korisnickoIme;

    @FXML
    public PasswordField sifra;

    @FXML
    public Button registrujSe;

    @FXML
    private DatePicker datum;          // mapira se na model: java.util.Date datum
    @FXML
    ComboBox<String> statusIndex;
    public GUIKontrolerRegistracija kngui;

    @FXML
    public void initialize() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, IOException, FileNotFoundException, ClassNotFoundException {
        statusIndex.setItems(FXCollections.observableArrayList(
                "Novo", "Aktivno", "Blokirano"
        ));
        statusIndex.getSelectionModel().select(0); // default 0 -> upisaće se kao 0 u model

        try {
            kngui = new GUIKontrolerRegistracija(this);
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

}
