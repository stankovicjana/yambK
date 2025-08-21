/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIIzmenaPodataka.Osluskivaci;

import GUIIzmenaPodataka.GUIKontrolerIzmenaPodataka;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 *
 * @author user
 */
public class OsluskivacSacuvajIzmene implements EventHandler {

   GUIKontrolerIzmenaPodataka kongui;

    public OsluskivacSacuvajIzmene(GUIKontrolerIzmenaPodataka kongui) {
        this.kongui = kongui;
    }

    @Override
    public void handle(Event event) {
        kongui.izmeniKorisnika();
    }
}
