/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIKorisnikLogin;

import DomenskiObjekat.GeneralDObject;
import DomenskiObjekat.Korisnik;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sinisa
 */
public class KonverterGUIDK {

    // javafx.scene.control.PasswordField
    public static boolean konvertujGUIUDK(GUIKorisnikRegistracija.FXMLDocumentController fxcon, GeneralDObject gdo) throws IllegalArgumentException, IllegalAccessException {
        for (Field f : fxcon.getClass().getDeclaredFields()) {
            for (Field dk : gdo.getClass().getDeclaredFields()) {
                if (dk.getName().equals(f.getName())) {
                    if (f.getType().getName().equals("javafx.scene.control.TextField") && dk.getType().getName().equals("java.lang.String")) {
                        try {  // kor.setSifra(fxcon.sifra.getText());
                            dk.set(gdo, ((javafx.scene.control.TextField) f.get(fxcon)).getText());
                        } catch (IllegalArgumentException | IllegalAccessException ex) {
                            Logger.getLogger(KonverterGUIDK.class.getName()).log(Level.SEVERE, null, ex);
                            return false;
                        }
                    }

                    if (f.getType().getName().equals("javafx.scene.control.PasswordField") && dk.getType().getName().equals("java.lang.String")) {
                        try {
                            dk.set(gdo, ((javafx.scene.control.PasswordField) f.get(fxcon)).getText());
                        } catch (IllegalArgumentException | IllegalAccessException ex) {
                            Logger.getLogger(KonverterGUIDK.class.getName()).log(Level.SEVERE, null, ex);
                            return false;
                        }
                    }

                    if (f.getType().getName().equals("javafx.scene.control.DatePicker") && dk.getType().getName().equals("java.util.Date")) {
                        try {  // kor.setDatumRodjenja( konvertujLocalDateUSqlDate(fxcon.datumRodjenja.getValue()));
                            dk.set(gdo, konvertujLocalDateUSqlDate((LocalDate) ((javafx.scene.control.DatePicker) f.get(fxcon)).getValue()));
                            //dk.set(gdo,konvertujLocalDateUSqlDate((LocalDate) ((javafx.scene.control.TextField)f.get(fxcon)).getText()));
                        } catch (IllegalArgumentException | IllegalAccessException ex) {
                            Logger.getLogger(KonverterGUIDK.class.getName()).log(Level.SEVERE, null, ex);
                            return false;
                        }
                    }
                    if (f.getType().getName().equals("javafx.scene.control.ComboBox")
                            && dk.getType().getName().equals("int")) {
                        javafx.scene.control.ComboBox<?> cb = (javafx.scene.control.ComboBox<?>) f.get(fxcon);
                        int idx = cb.getSelectionModel().getSelectedIndex();
                        dk.set(gdo, idx);
                    }

                }
            }
        }
        return true;
    }

    public static boolean konvertujDKUGUI(GeneralDObject gdo, FXMLDocumentController fxcon) {
        if (gdo == null || fxcon == null) {
            return false;
        }

        try {
            // 1) Ako je konkretno Korisnik – najdirektnije:
            if (gdo instanceof Korisnik) {
                Korisnik k = (Korisnik) gdo;

                if (fxcon.korisnickoIme != null) {
                    fxcon.korisnickoIme.setText(k.getKorisnickoIme() != null ? k.getKorisnickoIme() : "");
                }

                if (fxcon.sifra != null) {
                    fxcon.sifra.setText(k.getSifra() != null ? k.getSifra() : "");
                }

                return true;
            }

            // 2) Fallback: refleksija po imenima polja ("korisnickoIme", "sifra")
            Field fUser = gdo.getClass().getDeclaredField("korisnickoIme");
            fUser.setAccessible(true);
            Object korisnickoIme = fUser.get(gdo);

            Field fPass = gdo.getClass().getDeclaredField("sifra");
            fPass.setAccessible(true);
            Object sifra = fPass.get(gdo);

            if (fxcon.korisnickoIme != null) {
                fxcon.korisnickoIme.setText(korisnickoIme != null ? korisnickoIme.toString() : "");
            }

            if (fxcon.sifra != null) {
                fxcon.sifra.setText(sifra != null ? sifra.toString() : "");
            }

            return true;

        } catch (NoSuchFieldException | IllegalAccessException e) {
            Logger.getLogger(KonverterGUIDK.class.getName()).log(Level.SEVERE, null, e);
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
