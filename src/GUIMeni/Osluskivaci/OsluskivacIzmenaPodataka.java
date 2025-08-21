/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIMeni.Osluskivaci;

import GUIMeni.GUIKontrolerMeni;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 *
 * @author nodas
 */
public class OsluskivacIzmenaPodataka implements EventHandler {

    GUIKontrolerMeni kongui;
    
    public OsluskivacIzmenaPodataka(GUIMeni.GUIKontrolerMeni kongui) {
        this.kongui = kongui;
    }
    
    @Override
    public void handle(Event t) {
        
        kongui.izmenaPodataka();
    }    
}
