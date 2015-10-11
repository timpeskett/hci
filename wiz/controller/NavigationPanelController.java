/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard_interface.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import wizard_interface.MainController;

/**
 *
 * @author Chris
 */
public class NavigationPanelController {

    private MainController main;

    @FXML
    HBox type;
    @FXML
    HBox files;
    @FXML
    HBox settings;
    @FXML
    HBox finalise;
    @FXML
    Polyline arrow1;
    @FXML
    Polyline arrow2;
    @FXML
    Polyline arrow3;
    @FXML Text progressText;

    private int currState;

    public void init(MainController inMainController) {
        main = inMainController;
        currState = 0;
    }

    public void setPanelState(int inState) {
        System.out.println("Changing state to " + inState);
        if (inState == 0) {
            progressText.setOpacity(0);
            type.setOpacity(0);
            arrow1.setOpacity(0);
            files.setOpacity(0);
            settings.setOpacity(0);
            finalise.setOpacity(0);
            arrow2.setOpacity(0);
            arrow3.setOpacity(0);
            currState = 0;
        } else if (inState == 1) {
            progressText.setOpacity(1);
            type.setOpacity(1.0);
            arrow1.setOpacity(1.0);
            files.setOpacity(0.2);
            settings.setOpacity(0.2);
            finalise.setOpacity(0.2);
            arrow2.setOpacity(0.2);
            arrow3.setOpacity(0.2);
            currState = 1;
        } else if (inState == 2) {
            progressText.setOpacity(1);
            type.setOpacity(1.0);
            arrow1.setOpacity(1.0);
            files.setOpacity(1.0);
            settings.setOpacity(0.2);
            finalise.setOpacity(0.2);
            arrow2.setOpacity(1.0);
            arrow3.setOpacity(0.2);
            currState = 2;
        } else if (inState == 3) {
            progressText.setOpacity(1);
            type.setOpacity(1.0);
            arrow1.setOpacity(1.0);
            files.setOpacity(1.0);
            settings.setOpacity(1.0);
            finalise.setOpacity(0.2);
            arrow2.setOpacity(1.0);
            arrow3.setOpacity(1.0);
            currState = 3;
        } else if (inState == 4) {
            progressText.setOpacity(1);
            type.setOpacity(1.0);
            arrow1.setOpacity(1.0);
            files.setOpacity(1.0);
            settings.setOpacity(1.0);
            finalise.setOpacity(1.0);
            arrow2.setOpacity(1.0);
            arrow3.setOpacity(1.0);
            currState = 4;
        }
    }

}
