/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard.controller;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Chris
 */
public class SplashController {

    SimpleIntegerProperty state;
    @FXML private AnchorPane splashPane;
    @FXML private Button startBtn;
    
    
    
    
    void init(SimpleIntegerProperty inState) {
        state = inState;
        System.out.println("Splash controller created.");
    }
    
    public void startBtnPressed(){
        System.out.println("splash pressed");
        state.set(state.add(1).get());
    }

    public void hide() {
        splashPane.setVisible(false);
    }


}
