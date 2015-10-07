/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard_ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class CenterPanelController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private Button nextBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button startBtn;

    /* Splash screen */
    @FXML
    private ImageView splashLogo;
    @FXML
    private Text splashText;

    @FXML
    private VBox centerVbox;

    @FXML
    private HBox btnBounds;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set the style of the center panels background, done here so children don't inherit
        pane.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-background-radius: 0 15 15 0;");
        // centerVbox.setAlignment(Pos.CENTER);

        splashText.textProperty().set(
                "FFmpeg is the leading multimedia framework, able to decode, encode, \n"
                + "transcode, mux, demux, stream, filter and play pretty much anything \n"
                + "that humans and machines have created.\n"
                + "\n"
                + "It supports the most obscure ancient formats up to the cutting edge.\n"
                + "No matter if they were designed by some standards committee, the \n"
                + "community or a corporation.");

        assert centerVbox != null : "centerVbox is not null";
        btnBounds.getChildren().remove(0, 2); // remove start and back for now, then add back when start is pressed
        centerVbox.setAlignment(Pos.CENTER);
        centerVbox.setSpacing(30);
        btnBounds.setAlignment(Pos.CENTER);
        btnBounds.setSpacing(200);
       // centerVbox.getChildren().addAll(splashLogo, splashText);
        

        // pane.getChildren().add(centerVbox);
        startBtn.setVisible(true);
        nextBtn.setVisible(false);
        backBtn.setVisible(false);
    }

    public void startBtnPressed() {
        System.out.println("Start button pressed");
        btnBounds.getChildren().addAll(nextBtn, backBtn);
        btnBounds.getChildren().remove(startBtn);
        centerVbox.getChildren().removeAll(splashLogo, splashText);
        nextBtn.setVisible(true);
        backBtn.setVisible(true);
    }

    public void nextBtnPressed() {
        System.out.println("next btn pressed");
        nextBtn.setStyle("-fx-opacity: .8");
        backBtn.setStyle("-fx-opacity: .8");
    }

    public void backBtnPressed() {
        System.out.println("back btn pressed");
        nextBtn.setStyle("-fx-opacity: .2");
        backBtn.setStyle("-fx-opacity: .2");
    }

}
