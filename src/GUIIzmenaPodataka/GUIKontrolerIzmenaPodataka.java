/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//IV predavanje cas II
package GUIIzmenaPodataka;

import DomenskiObjekat.Korisnik;
import GUIIzmenaPodataka.Osluskivaci.OsluskivacSacuvajIzmene;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javafx.scene.control.Alert;

/**
 *
 * @author user
 */
public class GUIKontrolerIzmenaPodataka {

    FXMLDocumentController fxcon;
    Socket soket;
    ObjectOutputStream out;
    ObjectInputStream in;
    TransferObjekat.GenerickiTransferObjekat gto;

    public GUIKontrolerIzmenaPodataka(FXMLDocumentController fxcon) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, IOException, FileNotFoundException, ClassNotFoundException {
        this.fxcon = fxcon;

        this.fxcon.sacuvajIzmene.setOnAction(new OsluskivacSacuvajIzmene(this));
        soket = new Socket("127.0.0.1", 8189);
        gto = new TransferObjekat.GenerickiTransferObjekat();
    }

    public void poruka(String poruka) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Poruka:");
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(poruka);
        infoAlert.showAndWait();
    }

    String nazivSOIzmeniKorisnika() {
        return "izmeniKorisnika";
    }

  

    private boolean popunjenaPolja() {
        String sifra = this.fxcon.sifra.getText();
        String ime = this.fxcon.korisnickoIme.getText();

        return (!sifra.isEmpty() && !ime.isEmpty());
    }

    public void pozivSO(String nazivSO) {
        gto.setNazivOperacije(nazivSO);
        try {
            out = new ObjectOutputStream(soket.getOutputStream());
            in = new ObjectInputStream(soket.getInputStream());
        } catch (IOException ex) {
            System.err.println("Greska prilikom otvaranja in i/ili out tokova");
        }
        try {
            out.writeObject(gto);
        } catch (IOException ex) {

            System.err.println("Greska prilikm slanja objekta kod out write metode");
        }

        try {
            gto = (TransferObjekat.GenerickiTransferObjekat) in.readObject();
        } catch (IOException ex) {
            System.err.println("Greska kod ucitavanja objekta in read object metoda" + ex.getStackTrace());
        } catch (ClassNotFoundException ex) {

            System.err.println("Klasa nije pronadjena");
        }
    }

    public void izmeniKorisnika() {
        if (popunjenaPolja()) {

            Korisnik izmenjenKorisnik = vratiKorisnika();
            gto.setDK(izmenjenKorisnik);

            pozivSO(nazivSOIzmeniKorisnika());//slanje neulogovanog korisnika na server

            if (gto.getSignal()) {
                poruka("Sistem je zapamtio promene.");

                this.fxcon.zatvoriFormu();
            } else {
                poruka("Sistem ne moze da zapamtio promene nad podacima");

            }
        } else {
            poruka("Nije uneto korisnicko ime ili sifra.");
        }
    }

    

    public void zatvoriFormu() {
        fxcon.zatvoriFormu();
    }

    void prikaziKorinsika(Korisnik ulogovanKorisnik) {
        fxcon.ime.setText(ulogovanKorisnik.getIme());
        fxcon.prezime.setText(ulogovanKorisnik.getPrezime());

    }

    private Korisnik vratiKorisnika() {
        String novoKoirnsickoIme = fxcon.korisnickoIme.getText();
        String novaSifra = fxcon.sifra.getText();

        fxcon.getUlogovanKorisnik().setKorisnickoIme(novoKoirnsickoIme);
        fxcon.getUlogovanKorisnik().setSifra(novaSifra);
        return fxcon.getUlogovanKorisnik();

    }
}
