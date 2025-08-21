/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIKorisnikRegistracija;

import GUIKorisnikLogin.*;
import DomenskiObjekat.GeneralDObject;
import Server_client.Korisnik;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.DatePicker;

/**
 *
 * @author Sinisa
 */
public class KonverterGUIDK {

    public static boolean konvertujGUIUDK(GUIKorisnikRegistracija.FXMLDocumentController fxcon,
            Korisnik gdo) {
        if (fxcon == null || gdo == null) {
            return false;
        }

        boolean ok = true;
        try {
            for (Field f : fxcon.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                Object control;
                try {
                    control = f.get(fxcon);
                } catch (Exception e) {
                    continue;
                }
                if (control == null) {
                    continue; // fx:id ne postoji na ovoj formi
                }
                for (Field dk : gdo.getClass().getDeclaredFields()) {
                    dk.setAccessible(true);
                    if (!dk.getName().equals(f.getName())) {
                        continue;
                    }

                    String fxType = f.getType().getName();
                    String dkType = dk.getType().getName();

                    try {
                        // TextField -> String
                        if (fxType.equals("javafx.scene.control.TextField")
                                && dkType.equals("java.lang.String")) {
                            String txt = ((javafx.scene.control.TextField) control).getText();
                            dk.set(gdo, txt);
                        } // PasswordField -> String
                        else if (fxType.equals("javafx.scene.control.PasswordField")
                                && dkType.equals("java.lang.String")) {
                            String txt = ((javafx.scene.control.PasswordField) control).getText();
                            dk.set(gdo, txt);
                        } // ComboBox -> int/Integer (selectedIndex; dozvoli -1 => null/clear)
                        else if (fxType.equals("javafx.scene.control.ComboBox")
                                && (dkType.equals("int") || dkType.equals("java.lang.Integer"))) {
                            javafx.scene.control.ComboBox<?> cb = (javafx.scene.control.ComboBox<?>) control;
                            int idx = cb.getSelectionModel().getSelectedIndex();
                            if (dk.getType() == int.class) {
                                dk.setInt(gdo, Math.max(idx, 0)); // za primitivu ne može null
                            } else {
                                dk.set(gdo, (idx >= 0) ? Integer.valueOf(idx) : null);
                            }
                        }
                    } catch (Exception oneFieldFail) {
                        ok = false; // označi problem, ali ne ruši sve
                        Logger.getLogger(KonverterGUIDK.class.getName()).log(Level.WARNING,
                                "Neuspešno mapiranje polja: " + dk.getName(), oneFieldFail);
                    }

                    break; // našli match za ovo ime; dalje na sledeću kontrolu
                }
            }
        } catch (Exception e) {
            Logger.getLogger(KonverterGUIDK.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        return ok;
    }

    public static boolean konvertujDKUGUI(GUIKorisnikLogin.FXMLDocumentController fxcon, Korisnik gdo) {
        if (gdo == null || fxcon == null) {
            return false;
        }

        try {

            for (Field f : fxcon.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                for (Field dk : gdo.getClass().getDeclaredFields()) {
                    dk.setAccessible(true);
                    if (!dk.getName().equals(f.getName())) {
                        continue;
                    }

                    String fxType = f.getType().getName();
                    String dkType = dk.getType().getName();

                    if (fxType.equals("javafx.scene.control.TextField") && dkType.equals("int")) {
                        Integer broj = (Integer) dk.get(gdo);
                        ((javafx.scene.control.TextField) f.get(fxcon)).setText(broj != null ? String.valueOf(broj) : "");
                    } else if (fxType.equals("javafx.scene.control.TextField") && dkType.equals("java.lang.String")) {
                        Object val = dk.get(gdo);
                        ((javafx.scene.control.TextField) f.get(fxcon)).setText(val != null ? val.toString() : "");
                    } else if (fxType.equals("javafx.scene.control.PasswordField") && dkType.equals("java.lang.String")) {
                        Object val = dk.get(gdo);
                        ((javafx.scene.control.PasswordField) f.get(fxcon)).setText(val != null ? val.toString() : "");
                    } else if (fxType.equals("javafx.scene.control.ComboBox") && dkType.equals("int")) {
                        Integer idx = (Integer) dk.get(gdo);
                        if (idx != null && idx >= 0) {
                            ((javafx.scene.control.ComboBox<?>) f.get(fxcon)).getSelectionModel().select(idx);
                        }
                    }
                }
            }
            return true;

        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(KonverterGUIDK.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static LocalDate konvertujUtilDateULocalDate(java.util.Date input) {
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date sqlDate = java.sql.Date.valueOf(sm.format(input));
        LocalDate date = sqlDate.toLocalDate();
        return date;
    }

    public static java.sql.Date konvertujLocalDateUSqlDate(LocalDate input) {
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = java.sql.Date.valueOf(input);
        return java.sql.Date.valueOf(sm.format(date));
    }

}
