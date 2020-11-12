import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class Avatar extends Application {

    @Override
    public void start(Stage stage) throws Exception{


//        Parent root = FXMLLoader.load(getClass().getResource("avatar.fxml"));

        // get controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("avatar.fxml"));
        Parent root= loader.load();
        Controller c = loader.getController();
        c.addSaveButton(stage);
        c.handleResizeEvent(stage);

        Scene scene = new Scene(root);
        stage.setMinWidth(800);
        stage.setMinHeight(400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}