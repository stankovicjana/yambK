/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIKorisnikLogin;
import javafx.scene.control.ComboBox;
import java.lang.reflect.Field;
import java.util.function.Function;
/**
 *
 * @author stank
 */
public class FXUtils {

    private FXUtils() {
    }

    /**
     * Vrati value ako nije null, inače fallback (radi za bilo koji T).
     */
    public static <T> T coalesce(T value, T fallback) {
        return value != null ? value : fallback;
    }

    /**
     * Tip-bezbedno dohvaćanje @FXML kontrole po imenu polja.
     */
    public static <T> T fx(Object controller, String fxId, Class<T> type) {
        try {
            Field f = controller.getClass().getDeclaredField(fxId);
            f.setAccessible(true);
            Object val = f.get(controller);
            return type.isInstance(val) ? type.cast(val) : null;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    /**
     * Uzmi selektovanu stavku iz ComboBox-a i mapiraj je u tip T (sa
     * fallback-om).
     */
    public static <S, T> T selectedTo(ComboBox<S> cb, Function<S, T> mapper, T fallback) {
        S sel = cb == null ? null : cb.getSelectionModel().getSelectedItem();
        return (sel == null) ? fallback : mapper.apply(sel);
    }

}
