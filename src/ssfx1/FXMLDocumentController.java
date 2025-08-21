/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssfx1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * FXML Controller class
 *
 * @author stank
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private MenuBar MainMenu;
    @FXML
    private Menu igra;
    @FXML
    private MenuItem zapocniIgru;
    @FXML
    private MenuItem pravilaIgre;
    @FXML
    private MenuItem poeniKorisnika;
    @FXML
    private Menu izlaz;
    @FXML
    private MenuItem izlazIzPrograma;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void zapocniIgru(ActionEvent event) {
    }

    @FXML
    private void pravilaIgre(ActionEvent event) {
    }

    @FXML
    private void poeniKorisnika(ActionEvent event) {
    }

    @FXML
    private void izlazIzPrograma(ActionEvent event) {
    }
    
}
