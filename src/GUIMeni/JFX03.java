package GUIMeni;

import DomenskiObjekat.Korisnik;
import TransferObjekat.GenerickiTransferObjekat;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Map;
import java.util.function.Consumer;
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
public class JFX03 extends Application {

    FXMLDocumentController con;
    Korisnik ulogovanKorisnik;
    Socket soket;
    ObjectOutputStream out;
    ObjectInputStream in;


    public JFX03(Korisnik korisnik) {
        this.ulogovanKorisnik = korisnik;
    }

    public JFX03(Korisnik ulogovanKorisnik, ObjectInputStream in, ObjectOutputStream out) {
        this.ulogovanKorisnik = ulogovanKorisnik;
        this.in = in;
        this.out = out;
    }
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));

        Parent root = loader.load();

        GUIMeni.FXMLDocumentController controller = loader.getController();

        con = loader.getController(); // Učitani FXML kontroler

        // Postavljanje komunikacije
        // Postavljanje ulogovanog korisnika
        con.postaviUlogovanogKorisnika(ulogovanKorisnik);
        System.out.println("U JFX03" +this.in + this.out );
        con.setIn(in);
        con.setOut(out);
        //con.postaviSoket(out, in);
        controller.initData();

        con.postaviStage(stage); // ako treba kontroleru stage
        Scene scene = new Scene(root);
        scene.getStylesheets().add("CSS/stylesheet.css");

        stage.setScene(scene);
        stage.setTitle("Yamb - Glavni meni");
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public GUIMeni.FXMLDocumentController getController() {
        return con;
    }

    public void zatvoriFormu() {

        con.stage.close();
    }

//    public void postaviUlogovanogKorisnika(Long id) {
//        con.idKorisnika = id;
//        System.out.println("GUIGlavniMeni.JFX03.postaviUlogovanogKorisnika() njegov ID je: " + con.idKorisnika);
//    }
//
//    public Long vratiUlogovanogKorisnika() {
//        return con.idKorisnika;
//    }
    public Korisnik vratiKorisnika() {
        return ulogovanKorisnik;
    }

}
