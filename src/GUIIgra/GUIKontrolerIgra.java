/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIIgra;

import DomenskiObjekat.Korisnik;
import GUIIgra.FXMLDocumentController.GameRow;
import GUIIgra.Osluskivaci.OsluskivacKreirajIgru;
import GUIIgra.Osluskivaci.OsluskivacOsveziListu;
import GUIMeni.JFX03;
import TransferObjekat.GenerickiTransferObjekat;
import DomenskiObjekat.Igra;
import GUIIgra.Osluskivaci.OsluskivacPridruziSe;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author stank
 */
public class GUIKontrolerIgra {

    Socket soket;
    ObjectOutputStream out;
    ObjectInputStream in;
    GenerickiTransferObjekat gto;
    int mat[][];
    public final FXMLDocumentController fxcon;

    Korisnik ulogovanKorisnik;

    public GUIKontrolerIgra(FXMLDocumentController fxcon) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, IOException {
        this.fxcon = fxcon;
        this.gto = new GenerickiTransferObjekat();
        this.ulogovanKorisnik = fxcon.korisnik;

        this.fxcon.btnCreate.setOnAction(new OsluskivacKreirajIgru(this));
        this.fxcon.btnRefresh.setOnAction(new OsluskivacOsveziListu(this));
        this.fxcon.btnJoin.setOnAction(new OsluskivacPridruziSe(this));

        //fxcon.next.setOnAction(new OsluskivacZavrsiPotez(this));
        //fxcon.najava.setOnAction(e -> handleNajaviPolje());
    }

    public void setSoket(Korisnik k, ObjectInputStream in, ObjectOutputStream out) {
        this.in = in;
        this.ulogovanKorisnik = k;
        this.out = out;
    }

    public void kreirajIgru() throws IOException {
        Igra igra = new Igra();
        igra.setDomacinId(ulogovanKorisnik.idKorisnik);
        gto.gdo = igra;
        
       // gto.setKorisnik(ulogovanKorisnik);

        pozivSO(nazivSOKreirajIgru());//slanje neulogovanog korisnika na server

        if (gto.getSignal()) {
            JFX03 jfx03 = new JFX03(ulogovanKorisnik, in, out);//glavni meni
            try {
                jfx03.start(new Stage());
                this.fxcon.zatvoriFormu();

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                System.out.println(ex.getMessage());
            }
        } else {
            poruka("Sistem ne može da nađe igrača na osnovu unetih vrednosti za prijavljivanje");

        }

    }

    private String nazivSOKreirajIgru() {
        return "zapocniIgru";
    }

    public void pozivSO(String nazivSO) throws IOException {
        gto.setNazivOperacije(nazivSO);

        out.writeObject(gto);
        out.flush();

        try {
            gto = (GenerickiTransferObjekat) in.readObject();
        } catch (IOException ex) {
            System.err.println("Greska kod ucitavanja objekta in read object metoda" + ex.getStackTrace());
        } catch (ClassNotFoundException ex) {

            System.err.println("Klasa nije pronadjena");
        }
    }

    public void poruka(String poruka) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Poruka:");
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(poruka);
        infoAlert.showAndWait();
    }

    private String nazivSOOsveziIgre() {
        return "osveziIgre";
    }

    public void osveziListu() throws IOException {
        pozivSO(nazivSOOsveziIgre());

        if (gto.getSignal()) {
            List<Igra> igre = (List<Igra>) gto.getIgre();

            fxcon.games.clear(); // prazni staru listu
            for (Igra i : igre) {
                fxcon.games.add(new GameRow(
                        String.valueOf(i.getId()),
                        i.getDomacinIme().toString(), // ili String.valueOf(i.getDomacinId())
                        (i.getGostId() == 0 ? "Nije zapoceto" : i.getGostIme()),
                        i.getStatus().name(),
                        i.getCreatedAt().toString()
                ));
            }
        } else {
            poruka("Sistem ne može da nađe igre");

        }
    }

    private String nazivSOPridruziSe() {
        return "pridruziSe";
    }

    public void pridruziSe(Igra igra) throws IOException {
        igra.setGostId(ulogovanKorisnik.idKorisnik);
        igra.setStatus(Igra.StatusIgre.STARTED);
        gto.setGdo(igra);                  
        pozivSO(nazivSOPridruziSe());

        if (gto.getSignal()) {
            JFX03 jfx03 = new JFX03(ulogovanKorisnik, in, out);//glavni meni
            try {
                jfx03.start(new Stage());
                this.fxcon.zatvoriFormu();

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                System.out.println(ex.getMessage());
            }
        } else {
            poruka("Sistem ne može da nađe igrača na osnovu unetih vrednosti za prijavljivanje");

        }
    }

}
