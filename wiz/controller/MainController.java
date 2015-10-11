/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard_interface;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import wizard_interface.controller.*;
import wizard_interface.MainController;


public class MainController implements Initializable {


    @FXML NavigationPanelController navPanelController;
    @FXML CenterPanelController centerPanelController;
    @FXML RightPanelController rightPanelController;
    
    @FXML AnchorPane mainAnchor;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        navPanelController.init(this);
        centerPanelController.init(this);
        rightPanelController.init(this);
        mainAnchor.setId("background"); // make sure id matches css #
    }    
    
    public void setProgressBarState(int inState){
        navPanelController.setPanelState(inState);
    }
    
    
}
