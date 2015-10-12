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

    /* Different types of projects available to the user */
    public enum ProjectType {

        AUDIO_PROJECT, VIDEO_PROJECT;
    }

    /* Fields used for inter-controller communication*/
    @FXML
    NavigationPanelController navPanelController;
    @FXML
    CenterPanelController centerPanelController;
    @FXML
    RightPanelController rightPanelController;

    @FXML
    AnchorPane mainAnchor;

    /* The current project being manipulated by the user */
    private AbstractProject currProject;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        navPanelController.init(this);
        centerPanelController.init(this);
        rightPanelController.init(this);
        mainAnchor.setId("background"); // make sure id matches css #
    }

    public void setProgressBarState(int inState) {
        navPanelController.setPanelState(inState);
    }

    public void createAudioProject() {
        currProject = new AudioProject();
    }

    public void createVideoProject() {
        currProject = new VideoProject();
    }

    public void setInputFiles(List<File> inList) {
        currProject.setInputFiles(inList);
    }

    public void setOutputDirectory(File inDirectory) {
        currProject.setOutputLocation(inDirectory);
    }

    public File getOutputDirectory() {
        return currProject.getOutputDirectory();
    }

    public List<File> getInputFiles() {
        return currProject.getInputFiles();
    }

    /* Update all audio settings of the current project */
    public void setAudioProjectSettings(String currProfile, String currFormat, String currCodec, String currChannels, String currBitrate, String currSampleRate) {
        AudioProject currAudioProj = (AudioProject) currProject;
        currAudioProj.setOutputFileType(currFormat);
        currAudioProj.setBitrate(currBitrate);
        currAudioProj.setCodec(currCodec);
        currAudioProj.setChannels(currChannels);
        currAudioProj.setProfile(currProfile);
        currAudioProj.setSampleRate(currSampleRate);
    }

    public void setProfile(String inProfile) {
        currProject.setProfile(inProfile);
    }

    public String getProfile() {
        return currProject.getProfile();
    }

    public String getBitrate() {
        return ((AudioProject) currProject).getBitrate();
    }

    public void setBitrate(String inBitrate) {
        ((AudioProject) currProject).setBitrate(inBitrate);
    }

    public String getChannels() {
        return ((AudioProject) currProject).getChannels();
    }

    public void setChannels(String inChannels) {
        ((AudioProject) currProject).setChannels(inChannels);
    }

    public String getSampleRate() {
        return ((AudioProject) currProject).getSampleRate();
    }

    public void setSampleRate(String inSampleRate) {
        ((AudioProject) currProject).setSampleRate(inSampleRate);
    }

    public String getOutputFileType() {
        return currProject.getOutputFileType();
    }

    public void setOutputFileType(String inFileType) {
        currProject.setOutputFileType(inFileType);
    }

    public String getCodec() {
        return ((AudioProject) currProject).getCodec();
    }

    public void setCodec(String inCodec) {
        ((AudioProject) currProject).setCodec(inCodec);
    }

    /* Return the current projects type (Audio or video) as an enumerated ProjectType */
    public ProjectType getProjectType() {
        ProjectType type = null;
        if (currProject instanceof AudioProject) {
            type = ProjectType.AUDIO_PROJECT;
        } else if (currProject instanceof VideoProject) {
            type = ProjectType.VIDEO_PROJECT;;
        } else {
            System.out.println("this should never go off, delete me at some point");
        }
        return type;
    }

}
