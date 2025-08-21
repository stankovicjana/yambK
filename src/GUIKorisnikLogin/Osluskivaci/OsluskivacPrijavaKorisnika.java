/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIKorisnikLogin.Osluskivaci;

import GUIKorisnikLogin.GUIKontrolerLogin;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class OsluskivacPrijavaKorisnika implements EventHandler {

//    GUIKorisnikPrijavljivanje.JFX02 jfx02;
//    GUIKorisnikPrijavljivanje.FXMLDocumentController fxmldc;
//    GUIKorisnik.FXMLDocumentController fxmldc01;//log in forma
//    Stage s;
        GUIKontrolerLogin kongui;


    public OsluskivacPrijavaKorisnika(GUIKontrolerLogin kongui) {
        this.kongui = kongui;//kontroler log in forme
    }

    @Override
    public void handle(Event event) {
            try {
                kongui.prijaviKorisnika();
                
//        if (jfx02 == null) {
//            jfx02 = new GUIKorisnikPrijavljivanje.JFX02();
//            s = new Stage();
//        }
//        try {
//            jfx02.start(s);
//            odrediFXMLDocumentController();
//            fxmldc01.zatvoriFormu();
//            //jfx01.zatvoriFormu();
//            // if (jfx01 == null) {
//            //   jfx01 = new GUIKorisnik.JFX01();
//            // }
//            // fxmldc01 = jfx01.getController();
//            // fxmldc01.zatvoriFormu();
//        } catch (Exception ex) {
//            Logger.getLogger(OsluskivacPrijavaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
//        }
            } catch (IOException ex) {
                Logger.getLogger(OsluskivacPrijavaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

//    public GUIKorisnikPrijavljivanje.FXMLDocumentController vratiFXMLDocumentController() {
//        return fxmldc;
//    }
//
//    void odrediFXMLDocumentController() {
//        fxmldc = jfx02.getController();
//    }

}
