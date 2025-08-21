package GUIIgra;

import DomenskiObjekat.Korisnik;
import GUIIgra.FXMLDocumentController;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.URL;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author user
 */
public class JFX07 extends Application {

    FXMLDocumentController con;

    ObjectOutputStream out;
    ObjectInputStream in;
    Korisnik korisnik;
    public boolean start;

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    @Override
    public void start(Stage stage) throws Exception {
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));

        Parent root = loader.load();

        GUIIgra.FXMLDocumentController controller = loader.getController();
        con = loader.getController(); // Učitani FXML kontroler

        con.postaviSoket(out, in);
        con.initData();
        // Postavljanje ulogovanog korisnika
        con.postaviUlogovanogKorisnika(korisnik);

        // Prosleđivanje callback mape
        // Pokretanje slušanja poruka (ili u kontroleru automatski u postaviSoket)
        // GUI deo
        con.postaviStage(stage); // ako treba kontroleru stage
        Scene scene = new Scene(root);
        scene.getStylesheets().add("CSS/stylesheet.css");

        stage.setScene(scene);
        stage.setTitle("Yamb - Glavni meni");
        stage.show();*/
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));

        Parent root = loader.load();

        GUIIgra.FXMLDocumentController controller = loader.getController();

        controller.setIn(in);
        controller.setOut(out);
        controller.korisnik = korisnik;
        controller.start = start;
        controller.initData();

        Scene scene = new Scene(root);
        stage.setTitle("client");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public JFX07(Korisnik k, ObjectInputStream in, ObjectOutputStream out) {
        this.out = out;
        this.in = in;
        korisnik = k;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

}
