/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard_interface.controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
//import wizard_interface.MainController;
import wizard_interface.controller.*;
import wizard_interface.model.AbstractProject;
import wizard_interface.model.AudioProject;
import wizard_interface.model.VideoProject;


public class MainController implements Initializable {


    @FXML NavigationPanelController navPanelController;
    @FXML CenterPanelController centerPanelController;
    @FXML RightPanelController rightPanelController;
    
    @FXML AnchorPane mainAnchor;
    
    private AbstractProject currProject;
    
    
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
    
    public void createAudioProject(){
        currProject = new AudioProject();
    }
    
    public void createVideoProject(){
        currProject = new VideoProject();
    }
    
    public void setInputFiles(List<File> inList){
        currProject.setInputFiles(inList);
    }
    
    public void setOutputDirectory(File inDirectory){
        currProject.setOutputLocation(inDirectory);
    }

    public File getOutputDirectory() {
        return currProject.getOutputDirectory();
    }

    public List<File> getInputFiles() {
        return currProject.getInputFiles();
    }
    
}
