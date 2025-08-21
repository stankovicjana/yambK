package GUIIzmenaPodataka;

import DomenskiObjekat.Korisnik;
import GUIKorisnikRegistracija.*;
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
public class JFX06 extends Application {

    FXMLDocumentController con;
    Korisnik ulogovanKorisnik;

    public JFX06(Korisnik ulogovanKorisnik) {
        this.ulogovanKorisnik = ulogovanKorisnik;
    }

    @Override
    public void start(Stage stage) throws Exception {

        String resourcePath = "FXMLDocument.fxml";
        URL location = getClass().getResource(resourcePath);
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = fxmlLoader.load();
        con = (FXMLDocumentController) fxmlLoader.getController();

        con.postaviStage(stage);
        con.setUlogovanKorisnik(ulogovanKorisnik);
        con.postaviPodatke();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("CSS/stylesheet.css");
        stage.setScene(scene);
        stage.setTitle("Izmeni nalog");
        stage.show();

    }

    public FXMLDocumentController getController() {
        return con;
    }

}
