package GUIPoeni;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author user
 */
public class JFX05 extends Application {

    FXMLDocumentController con;

    @Override
    public void start(Stage stage) throws Exception {

        String resourcePath = "FXMLDocument.fxml";
        URL location = getClass().getResource(resourcePath);
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = fxmlLoader.load();
        con = (FXMLDocumentController) fxmlLoader.getController();

        con.postaviStage(stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Registracija");
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public GUIPoeni.FXMLDocumentController getController() {
        return con;
    }

    public void zatvoriFormu() {
        con.stage.close();
    }

   
    public void postaviPodatke(DomenskiObjekat.Korisnik ulogovanKorisnik) {
        con.prikaziPoene(ulogovanKorisnik);
    }
}
