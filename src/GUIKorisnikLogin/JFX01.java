package GUIKorisnikLogin;

import DomenskiObjekat.Korisnik;
import Server_client.GenerickiTransferObjekat;
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
public class JFX01 extends Application {

    FXMLDocumentController con;
    GenerickiTransferObjekat gto;

    public FXMLDocumentController getCon() {
        return con;
    }

    public void setCon(FXMLDocumentController con) {
        this.con = con;
    }

    public GenerickiTransferObjekat getGto() {
        return gto;
    }

    public void setGto(GenerickiTransferObjekat gto) {
        this.gto = gto;
    }

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
        stage.setTitle("Prijavljivanje na sistem");
        if (gto != null) {
            GUIKorisnikRegistracija.KonverterGUIDK.konvertujDKUGUI(con, gto.gdo);
        }
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public GUIKorisnikLogin.FXMLDocumentController getController() {
        return con;
    }

    public void zatvoriFormu() {
        con.stage.close();
    }
}
