/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard_interface.controller;

import wizard_interface.MainController;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class CenterPanelController {

    private MainController main;
    private int currState;
    @FXML
    Text splashText;
    @FXML
    Button nextBtn;
    @FXML
    Button backBtn;
    @FXML
    Button startBtn;
    @FXML
    HBox btnBounds;
    @FXML
    VBox centerVbox;
    @FXML
    AnchorPane centerPanel;

    /* Splash screen */
    @FXML
    ImageView splashLogo;

    public void init(MainController inMainController) {
        main = inMainController;
        currState = 0;
        centerPanel.setId("centerPanel");
        splashText.textProperty().set(
                "FFmpeg is the leading multimedia framework, able to decode, encode, transcode, mux, demux,"
                + "stream, filter and play pretty much anything that humans and machines have created.\n\n"
                + "It supports the most obscure ancient formats up to the cutting edge"
                + "No matter if they were designed by some standards committee, the"
                + "community or a corporation.");
        splashText.setFont(Font.font("Verdana", 16));

        btnBounds.getChildren().remove(0, 2); // remove start and back for now, then add back when start is pressed
        centerVbox.setAlignment(Pos.CENTER);
        centerVbox.setSpacing(30);
        btnBounds.setAlignment(Pos.CENTER);
        btnBounds.setSpacing(200);

        startBtn.setVisible(true);
        nextBtn.setVisible(false);
        backBtn.setVisible(false);
        main.setProgressBarState(0);

    }

    public void startBtnPressed() {
        System.out.println("Start button pressed");
        btnBounds.getChildren().addAll(backBtn, nextBtn);
        btnBounds.getChildren().remove(startBtn);
        centerVbox.getChildren().removeAll(splashLogo, splashText);
        nextBtn.setVisible(true);
        backBtn.setVisible(true);
        main.setProgressBarState(1);
        currState++;
    }

    public void nextBtnPressed() {
        if (currState < 4) {
            currState++;
        }
        main.setProgressBarState(currState);
    }

    public void backBtnPressed() {
        if (currState > 1) {
            currState--;
        }
        main.setProgressBarState(currState);
    }

}
