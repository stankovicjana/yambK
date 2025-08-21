/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIIgra.Osluskivaci;

import GUIIgra.GUIKontrolerIgra;
import GUIIzmenaPodataka.GUIKontrolerIzmenaPodataka;
import GUIKorisnikLogin.GUIKontrolerLogin;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 *
 * @author stank
 */
public class OsluskivacOsveziListu implements EventHandler {

    GUIKontrolerIgra kongui;

    public OsluskivacOsveziListu(GUIKontrolerIgra kongui) {
        this.kongui = kongui;
    }

    @Override
    public void handle(Event event) {
        try {
            kongui.osveziListu();
        } catch (Exception ex) {
            Logger.getLogger(OsluskivacOsveziListu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
