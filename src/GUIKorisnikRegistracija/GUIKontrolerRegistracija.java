/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//IV predavanje cas II
package GUIKorisnikRegistracija;

import DomenskiObjekat.GeneralDObject;
import GUIKorisnikLogin.FXUtils;
import GUIKorisnikLogin.JFX01;
import GUIKorisnikRegistracija.Osluskivaci.OsluskivacKreirajKorisnika;
import Server_client.GenerickiKontrolerServer;
import Server_client.GenerickiKontrolerServer_Service;
import Server_client.GenerickiTransferObjekat;
import Server_client.Korisnik;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class GUIKontrolerRegistracija {

    FXMLDocumentController fxcon;
    public GenerickiTransferObjekat gto;
    GenerickiKontrolerServer_Service service;
    GenerickiKontrolerServer kal;

    public GUIKontrolerRegistracija(FXMLDocumentController fxcon) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, IOException, FileNotFoundException, ClassNotFoundException {
        this.fxcon = fxcon;
        service = new GenerickiKontrolerServer_Service();//umesto soketa ovde se kreira server
        kal = service.getGenerickiKontrolerServerPort();//ovu klasu generise veb servis
        gto = new GenerickiTransferObjekat();

        this.fxcon.registrujSe.setOnAction(new OsluskivacKreirajKorisnika(this));
    }

    public void poruka(String poruka) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Poruka:");
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(poruka);
        infoAlert.showAndWait();
    }

    String nazivSORegistruj() {
        return "registrujKorisnika";
    }

    public void pozivSO(String nazivSO) {
        if (nazivSO.equals("registrujKorisnika")) {
            gto = kal.registrujKorisnika(gto);
        }

    }

    String transferObjekatPoruka() {
        return gto.getPoruka();
    }

    private boolean popunjenaPolja() {
        String korIme = this.fxcon.korisnickoIme.getText();
        String ime = this.fxcon.ime.getText();
        String prezime = this.fxcon.prezime.getText();
        String sifra = this.fxcon.sifra.getText();
        return (!korIme.isEmpty() && !ime.isEmpty() && !sifra.isEmpty() && !prezime.isEmpty());
    }

    private Korisnik popuniKorisnika() {
        Korisnik k = new Korisnik();
        /* kor.setKorisnickoIme(fxcon.korisnickoIme.getText());
        kor.setSifra(fxcon.sifra.getText());
        kor.setIme(fxcon.ime.getText());
        kor.setPrezime(fxcon.prezime.getText());
        kor.setStatusIndex(fxcon.statusIndex.getSelectionModel().getSelectedIndex());
        kor.setBrojPoena(0);*/
        k.setIme(fxcon.ime.getText());
        k.setPrezime(fxcon.prezime.getText());
        k.setBrojPoena(0);
        k.setKorisnickoIme(FXUtils.coalesce(fxcon.korisnickoIme.getText(), ""));
        k.setSifra(FXUtils.coalesce(fxcon.sifra.getText(), ""));
        k.setStatusIndex(FXUtils.selectedTo(fxcon.statusIndex, s -> {
            if (s == null) {
                return 0;
            }
            switch (s) {
                case "Novo":
                    return 0;
                case "Aktivno":
                    return 1;
                case "Blokirano":
                    return 2;
                default:
                    return 0;
            }
        }, 0));

        return k;
    }

    public boolean napuniObjekatIzForme() {
        try {
            /*if (gto.getDK() == null) {
                newDK();
            }
            GeneralDObject gdo = gto.getDK();
            if (!KonverterGUIDK.konvertujGUIUDK(fxcon, gdo)) {
                naPrvi();
                return false;
            }*/
            Korisnik gdo = gto.gdo;
            if (!KonverterGUIDK.konvertujGUIUDK(fxcon, gdo)) {
                return false;
            }
        } catch (Exception e) {
            poruka("Nisu ispravno uneta polja forme!!!");
        }
        return true;
    }

    public void registrujKorisnika() throws IllegalArgumentException, IllegalAccessException {

        gto.setGdo(popuniKorisnika());
        if (popunjenaPolja() && napuniObjekatIzForme()) {

            Korisnik neregistrovanKorisnik = popuniKorisnika();
            gto.setGdo(neregistrovanKorisnik);
            pozivSO(nazivSORegistruj());

            if (gto.isSignal()) {
                poruka("Sistem je zapamtio igrača.");
                try {
                    JFX01 jfx01 = new JFX01();
                    jfx01.setGto(gto);
                    jfx01.start(new Stage());
                    fxcon.zatvoriFormu();

                } catch (Exception ex) {
                    Logger.getLogger(GUIKontrolerRegistracija.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                poruka("Sistem ne može da zapamti novog igrača");
            }
        } else {

            poruka("Unesite sva polja!");
        }

    }

}
