/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIKorisnikLogin.Osluskivaci;

import GUIKorisnikLogin.GUIKontrolerLogin;
import GUIKorisnikRegistracija.GUIKontrolerRegistracija;
import GUIKorisnikRegistracija.JFX02;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class OsluskivacRegistracijaKorisnika implements EventHandler {

    GUIKontrolerLogin konguiLogin;

    public OsluskivacRegistracijaKorisnika(GUIKontrolerLogin konguiLogin) {
        this.konguiLogin = konguiLogin;
    }

    @Override
    public void handle(Event t) {
        konguiLogin.registrujKorisnika();
    }

}
