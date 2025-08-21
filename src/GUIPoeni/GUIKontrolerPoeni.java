/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIPoeni;


import javafx.scene.control.Alert;


/**
 *
 * @author Sinisa
 */
public class GUIKontrolerPoeni {

    FXMLDocumentController fxcon;

    public GUIKontrolerPoeni(FXMLDocumentController fxcon) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        this.fxcon = fxcon;
      
    }
  
    public void poruka(String poruka) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Poruka:");
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(poruka);
        infoAlert.showAndWait();
    }

   

    
   
    
}
