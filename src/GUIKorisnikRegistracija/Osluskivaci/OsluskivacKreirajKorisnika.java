/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIKorisnikRegistracija.Osluskivaci;

import GUIKorisnikRegistracija.GUIKontrolerRegistracija;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 *
 * @author user
 */
public class OsluskivacKreirajKorisnika implements EventHandler {

    GUIKontrolerRegistracija kongui;

    public OsluskivacKreirajKorisnika(GUIKontrolerRegistracija kongui) {
        this.kongui = kongui;
    }

    @Override
    public void handle(Event event) {
        try {
            kongui.registrujKorisnika();
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(OsluskivacKreirajKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(OsluskivacKreirajKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
