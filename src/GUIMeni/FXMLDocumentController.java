/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIMeni;

import DomenskiObjekat.Korisnik;
import TransferObjekat.GenerickiTransferObjekat;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import ssfx1.*;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
    public MenuItem zapocniIgru;
    @FXML
    public MenuItem pravilaIgre;
    @FXML
    public MenuItem poeniKorisnika;
    @FXML
    private Menu izlaz;
    @FXML
    private MenuItem izlazIzPrograma;
    Stage stage;
    Korisnik ulogovanKorisnik;
    GUIKontrolerMeni kngui;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    @FXML
    private Label statusLabel;

    @FXML
    public GridPane jambTable;
    @FXML
    public Label rollCountLabel;
    @FXML
    public Label sumLabel;
    @FXML
    public ToggleButton dice0, dice1, dice2, dice3, dice4, dice5;

    @FXML
    public Button next;
    @FXML
    public Button najava;

    public Korisnik korisnik;
    public boolean start;

    @FXML
    public void initialize() {
    }

    public void initData() throws NoSuchFieldException {
        try {
            kngui = new GUIKontrolerMeni(this);
            kngui.setSoket(in, out);
            kngui.ulogovaniKorisnik = ulogovanKorisnik;
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        kngui.init();

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public void zatvoriFormu() {
        stage.close();
    }

    @FXML
    public void izlazIzPrograma() {
        stage.close();
    }

    @FXML
    private void handleRollDice() {
        if (kngui != null) {
            kngui.rollDice();
        }
    }

    @FXML
    private void handleFinishGame() {
        if (kngui != null) {
            kngui.handleFinishGame();
        }
    }

    @FXML
    private void handleNajaviPolje() {
        if (kngui != null) {
            kngui.handleNajaviPolje();
        }
    }

    @FXML
    private void next() {

    }

    public void postaviStatus(String tekst) {
        statusLabel.setText(tekst);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
        Platform.exit();
        System.exit(0);
    }

    public void postaviUlogovanogKorisnika(Korisnik kor) {
        this.ulogovanKorisnik = kor;
    }

    public Korisnik vratiUlogovanogKorisnika() {
        return ulogovanKorisnik;
    }

    void postaviStage(Stage stage) {
        this.stage = stage;
    }

    void postaviSoket(ObjectOutputStream out, ObjectInputStream in) {
        if (kngui != null) {
            System.out.println("contorleru" + this.in + this.out);

            kngui.setSoket(in, out);

        }
    }

}
