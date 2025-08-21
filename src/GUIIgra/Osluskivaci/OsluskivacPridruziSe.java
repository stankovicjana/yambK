/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIIgra.Osluskivaci;

import DomenskiObjekat.Igra;
import GUIIgra.FXMLDocumentController.GameRow;
import GUIIgra.GUIKontrolerIgra;
import GUIIzmenaPodataka.GUIKontrolerIzmenaPodataka;
import GUIKorisnikLogin.GUIKontrolerLogin;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

/**
 *
 * @author stank
 */
public class OsluskivacPridruziSe implements EventHandler {

    GUIKontrolerIgra kongui;

    public OsluskivacPridruziSe(GUIKontrolerIgra kongui) {
        this.kongui = kongui;
    }

    @Override
    public void handle(Event event) {
        try {
            GameRow selektovan = kongui.fxcon.gamesTable.getSelectionModel().getSelectedItem();

            if (selektovan == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Upozorenje");
                alert.setHeaderText(null);
                alert.setContentText("Morate izabrati igru iz liste pre nego što se pridružite.");
                alert.showAndWait();
                return;
            }
            Igra igra = new Igra();
            igra.setId(Long.parseLong(selektovan.getGameId()));

            if (kongui.fxcon.korisnik.getIme().equals(selektovan.getHost())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Upozorenje");
                alert.setHeaderText(null);
                alert.setContentText("Ne mozete igrati sami sa sobom");
                alert.showAndWait();
                return;
            }

            kongui.pridruziSe(igra);
        } catch (Exception ex) {
            Logger.getLogger(OsluskivacPridruziSe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
