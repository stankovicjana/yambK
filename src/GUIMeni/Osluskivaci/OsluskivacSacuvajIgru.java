/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIMeni.Osluskivaci;

import GUIMeni.GUIKontrolerMeni;
import GUIIzmenaPodataka.GUIKontrolerIzmenaPodataka;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 *
 * @author stank
 */
public class OsluskivacSacuvajIgru implements EventHandler{
     GUIKontrolerMeni kongui;

    public OsluskivacSacuvajIgru(GUIKontrolerMeni kongui) {
        this.kongui = kongui;
    }

    @Override
    public void handle(Event event) {
       kongui.handleFinishGame();
    }
}
