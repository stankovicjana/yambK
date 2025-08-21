/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIMeni.Osluskivaci;

import javafx.event.Event;
import javafx.event.EventHandler;

/**
 *
 * @author user
 */
public class OsluskivacPravilaIgre implements EventHandler{

    GUIMeni.GUIKontrolerMeni kongui;

    public OsluskivacPravilaIgre(GUIMeni.GUIKontrolerMeni kongui) {
        this.kongui = kongui;
    }


    @Override
    public void handle(Event t) {

       kongui.pravilaIgre();
    }
    
}
