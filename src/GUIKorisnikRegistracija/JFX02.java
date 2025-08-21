package GUIKorisnikRegistracija;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class JFX02 extends Application {

    GUIKorisnikRegistracija.FXMLDocumentController con;

    @Override
    public void start(Stage stage) throws Exception {

        String resourcePath = "FXMLDocument.fxml";
        URL location = getClass().getResource(resourcePath);
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = fxmlLoader.load();
        con = (FXMLDocumentController) fxmlLoader.getController();

        con.postaviStage(stage);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("CSS/stylesheet.css");
        stage.setScene(scene);
        stage.setTitle("Registruj se");
        stage.show();

    }

    public FXMLDocumentController getController() {
        return con;
    }

}
