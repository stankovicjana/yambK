package GUIPravilaIgre;

import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLDocumentController {

   @FXML
    public Button ok;
    @FXML
    public TextArea uputstva;
    public Stage stage;

    @FXML
    public void izlazIzPrograma() {
        stage.close();

    }

    void postaviStage(Stage stage) {
        this.stage = stage;
    }

    public void zatvoriFormu() {
        stage.close();
    }

}
