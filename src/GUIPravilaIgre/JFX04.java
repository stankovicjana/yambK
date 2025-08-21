package GUIPravilaIgre;

import GUIPravilaIgre.FXMLDocumentController;
import java.net.URL;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author user
 */
public class JFX04 extends Application {

    FXMLDocumentController con;

    @Override
    public void start(Stage stage) throws Exception {

        String resourcePath = "FXMLDocument.fxml";
        URL location = getClass().getResource(resourcePath);
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = fxmlLoader.load();
        con = (FXMLDocumentController) fxmlLoader.getController();

        con.postaviStage(stage);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("CSS/stylesheet.css");
        stage.setScene(scene);
        stage.setTitle("Pravila igre");
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


}
