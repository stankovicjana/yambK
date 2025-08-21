/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIKorisnikLogin;

import DomenskiObjekat.GeneralDObject;
import DomenskiObjekat.Korisnik;
import GUIIgra.JFX07;
import GUIKorisnikLogin.Osluskivaci.OsluskivacRegistracijaKorisnika;
import GUIKorisnikLogin.Osluskivaci.OsluskivacPrijavaKorisnika;
import GUIKorisnikRegistracija.JFX02;
import GUIMeni.JFX03;
import TransferObjekat.GenerickiTransferObjekat;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class GUIKontrolerLogin {

    FXMLDocumentController fxcon;
    Socket soket;
    ObjectOutputStream out;
    ObjectInputStream in;
    GenerickiTransferObjekat gto;

    public GUIKontrolerLogin(FXMLDocumentController fxcon) throws IOException {
        this.fxcon = fxcon;//referenca na komponente

//        this.fxcon.kreirajKorisnika.setOnAction(new OsluskivacRegistracijaKorisnika(this));
        //      this.fxcon.prijaviKorisnika.setOnAction(new OsluskivacPrijavaKorisnika(this));
        soket = new Socket("127.0.0.1", 8189);
        gto = new GenerickiTransferObjekat();
        Korisnik k = new Korisnik();
        k.setIDKorisnika(1L);
        k.setIme("pakica");
        k.setKorisnickoIme("pakic");
        k.setSifra("makica");

        napuniFormuIzObjekta(k);
        this.fxcon.sifra.setFocusTraversable(false);//da polje sifra ne bude u fokusu
        //poveziSeSaServerom();
        this.fxcon.sifra.setFocusTraversable(false);//da polje sifra ne bude u fokusu

    }

    public void poruka(String poruka) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Poruka:");
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(poruka);
        infoAlert.showAndWait();
    }

    private String nazivSOPrijaviKorisnika() {
        return "prijaviKorisnika";
    }

    private boolean popunjenaPolja() {
        String sifra = this.fxcon.sifra.getText();
        String ime = this.fxcon.korisnickoIme.getText();

        return (!sifra.isEmpty() && !ime.isEmpty());
    }

    public void prijaviKorisnika() throws IOException {
        if (popunjenaPolja()) {

            Korisnik neulogovanKorisnik = popuniKorisnika();
            gto.setDK(neulogovanKorisnik);
            System.out.println("Korisnik koji se prijavljuje korisnicko ime: " + neulogovanKorisnik.getKorisnickoIme() + " i sifra: " + neulogovanKorisnik.getSifra());

            pozivSO(nazivSOPrijaviKorisnika());//slanje neulogovanog korisnika na server

            if (gto.getSignal()) {
                //JFX03 jfx03 = new JFX03((Korisnik) gto.getDK(), in, out);//glavni meni

                JFX07 jfx07 = new JFX07((Korisnik) gto.getDK(), in, out);//glavni meni
                poruka("Igrač je uspešno ulogovan u sistem");

                try {
                    jfx07.start(new Stage());
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
            out.flush();

        } catch (IOException ex) {

            System.err.println("Greska prilikm slanja objekta kod out write metode");
        }

        try {
            gto = (GenerickiTransferObjekat) in.readObject();
        } catch (IOException ex) {
            System.err.println("Greska kod ucitavanja objekta in read object metoda" + ex.getStackTrace());
        } catch (ClassNotFoundException ex) {

            System.err.println("Klasa nije pronadjena");
        }
    }

    public void registrujKorisnika() {
        JFX02 jfx02 = new JFX02();
        try {

            jfx02.start(new Stage());
            this.fxcon.zatvoriFormu();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }

    private Korisnik popuniKorisnika() {
        Korisnik kor = new Korisnik();
        kor.setSifra(fxcon.sifra.getText());
        kor.setKorisnickoIme(fxcon.korisnickoIme.getText());

        return kor;
    }

    public void zatvoriFormu() {
        fxcon.zatvoriFormu();
    }

    public boolean napuniFormuIzObjekta(GeneralDObject gdo) {
        try {
            if (!KonverterGUIDK.konvertujDKUGUI(gdo, fxcon)) {
                return false;
            }
        } catch (NumberFormatException e) {
            poruka("Ne moze da napuni formu iz objekta!!!");
        }
        return true;
    }

    public void napuniPraznuFormu() {
        for (Field f : fxcon.getClass().getDeclaredFields()) {
            if (f.getType().getName().equals("javafx.scene.control.TextField") || f.getType().getName().equals("javafx.scene.control.PasswordField")) {
                try {
                    ((javafx.scene.control.TextField) f.get(fxcon)).setText("");
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    //Logger.getLogger(OpstiGUIKontroler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void newDK() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void autowireButtons(Object controller) {
        Class<?> clazz = controller.getClass();

        for (Field f : clazz.getDeclaredFields()) {
            if (!Button.class.isAssignableFrom(f.getType())) {
                continue;
            }

            try {
                f.setAccessible(true);
                Button b = (Button) f.get(controller);
                if (b == null) {
                    continue;
                }

                // fx:id -> onXxx ime metode
                String id = f.getName(); // npr. "prijaviKorisnika"
                String methodName = "on" + Character.toUpperCase(id.charAt(0)) + id.substring(1);

                Method m = null;
                try {
                    m = clazz.getDeclaredMethod(methodName, ActionEvent.class);
                } catch (NoSuchMethodException ignore) {
                    try {
                        m = clazz.getDeclaredMethod(methodName);
                    } catch (NoSuchMethodException e) {
                        /* nema */ }
                }
                if (m == null) {
                    continue; // nema odgovarajuće metode
                }
                m.setAccessible(true);
                final Method mm = m; // effectively final za lambda

                b.setOnAction(ev -> {
                    try {
                        if (mm.getParameterCount() == 1) {
                            mm.invoke(controller, ev);
                        } else {
                            mm.invoke(controller);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static Method findMethod(Class<?> c, String name) {
        Class<?> cur = c;
        while (cur != null) {
            for (Method m : cur.getDeclaredMethods()) {
                if (m.getName().equals(name)) {
                    return m;
                }
            }
            cur = cur.getSuperclass();
        }
        return null;
    }

   
}
