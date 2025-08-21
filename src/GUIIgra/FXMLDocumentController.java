package GUIIgra;

import DomenskiObjekat.Korisnik;
import GUIKorisnikLogin.GUIKontrolerLogin;
import GUIMeni.GUIKontrolerMeni;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FXMLDocumentController {

    public ObjectOutputStream out;
    public ObjectInputStream in;
    public Stage stage;

    public Korisnik korisnik;
    public boolean start;
    Korisnik ulogovanKorisnik;
    GUIKontrolerIgra kngui;

    public void initData() {

        try {
            kngui = new GUIKontrolerIgra(this);
            kngui.setSoket(korisnik, in, out);
            btnRefresh.fire(); 
            colGameId.setCellValueFactory(c -> c.getValue().gameIdProperty());
            colHost.setCellValueFactory(c -> c.getValue().hostProperty());
            colPlayers.setCellValueFactory(c -> c.getValue().playersProperty());
            colStatus.setCellValueFactory(c -> c.getValue().statusProperty());
            colCreated.setCellValueFactory(c -> c.getValue().createdProperty());

            gamesTable.setItems(games);
            gamesTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            // Početno učitavanje
            handleRefreshGames();
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public void postaviUlogovanogKorisnika(Korisnik kor) {
        this.ulogovanKorisnik = kor;
    }

    void postaviStage(Stage stage) {
        this.stage = stage;
    }

    public void zatvoriFormu() {
        stage.close();
    }

    @FXML
    public void izlazIzPrograma() {
        stage.close();
    }

    @FXML
    public TableView<GameRow> gamesTable;
    @FXML
    private TableColumn<GameRow, String> colGameId;
    @FXML
    private TableColumn<GameRow, String> colHost;
    @FXML
    private TableColumn<GameRow, String> colPlayers;
    @FXML
    private TableColumn<GameRow, String> colStatus;
    @FXML
    private TableColumn<GameRow, String> colCreated;

    @FXML
    public Button btnRefresh;
    @FXML
    public Button btnCreate;
    @FXML
    public Button btnJoin;

    @FXML
    private Label statusLabel;
    public final ObservableList<GameRow> games = FXCollections.observableArrayList();
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @FXML
    public void handleRefreshGames() {

    }

    @FXML
    public void handleCreateGame() {

    }

    @FXML
    public void handleJoinGame() {

    }

    @FXML
    private void initialize() {

    }

    void postaviSoket(ObjectOutputStream out, ObjectInputStream in) {
        if (kngui != null) {
            kngui.setSoket(ulogovanKorisnik, in, out);

        }
    }

    public static class GameRow {

        private final javafx.beans.property.SimpleStringProperty gameId
                = new javafx.beans.property.SimpleStringProperty(this, "gameId");
        private final javafx.beans.property.SimpleStringProperty host
                = new javafx.beans.property.SimpleStringProperty(this, "host");
        private final javafx.beans.property.SimpleStringProperty players
                = new javafx.beans.property.SimpleStringProperty(this, "players");
        private final javafx.beans.property.SimpleStringProperty status
                = new javafx.beans.property.SimpleStringProperty(this, "status");
        private final javafx.beans.property.SimpleStringProperty created
                = new javafx.beans.property.SimpleStringProperty(this, "created");

        public GameRow(String gameId, String host, String players, String status, String created) {
            setGameId(gameId);
            setHost(host);
            setPlayers(players);
            setStatus(status);
            setCreated(created);
        }

        public String getGameId() {
            return gameId.get();
        }

        public void setGameId(String v) {
            gameId.set(v);
        }

        public javafx.beans.property.StringProperty gameIdProperty() {
            return gameId;
        }

        public String getHost() {
            return host.get();
        }

        public void setHost(String v) {
            host.set(v);
        }

        public javafx.beans.property.StringProperty hostProperty() {
            return host;
        }

        public String getPlayers() {
            return players.get();
        }

        public void setPlayers(String v) {
            players.set(v);
        }

        public javafx.beans.property.StringProperty playersProperty() {
            return players;
        }

        public String getStatus() {
            return status.get();
        }

        public void setStatus(String v) {
            status.set(v);
        }

        public javafx.beans.property.StringProperty statusProperty() {
            return status;
        }

        public String getCreated() {
            return created.get();
        }

        public void setCreated(String v) {
            created.set(v);
        }

        public javafx.beans.property.StringProperty createdProperty() {
            return created;
        }
    }

}
