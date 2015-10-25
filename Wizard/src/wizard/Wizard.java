/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard;

import java.awt.Dimension;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Wizard extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("res/Orange.css").toExternalForm());
            primaryStage.setScene(scene);

            final Dimension d = new Dimension(1010, 600);
            primaryStage.setScene(scene);
            primaryStage.resizableProperty().set(false);
            primaryStage.setWidth(d.width);
            primaryStage.setHeight(d.height);
            primaryStage.setTitle("FFmpeg Wizard Interface");

            primaryStage.getIcons().add(new Image(Wizard.class.getResourceAsStream("res/ffmpeg_icon.jpg")));

            primaryStage.show();
                            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
