/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard.controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import wizard.model.AbstractProject;
import wizard.model.AudioProject;
import wizard.model.VideoProject;

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

    public void setOutputFilename(String inFilename) {
        currProject.setOutputFileName(inFilename);
    }

    public StringProperty getOutputFilename() {
        return currProject.getOutputFileName();
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

    public void setVideoProjectSettings(String currProfile, String currFormat, String currCodec) {
        VideoProject currVideoProj = (VideoProject) currProject;
        currVideoProj.setOutputFileType(currFormat);
        currVideoProj.setCodec(currCodec);
        currVideoProj.setProfile(currProfile);
    }

    public void setProfile(String inProfile) {
        currProject.setProfile(inProfile);
    }

    public StringProperty getProfile() {
        return currProject.getProfile();
    }

    public StringProperty getBitrate() {
        if (currProject instanceof AudioProject) {
            return ((AudioProject) currProject).getBitrate();
        } else {
            return ((VideoProject) currProject).getBitrate();
        }

    }

    public void setBitrate(String inBitrate) {
        ((AudioProject) currProject).setBitrate(inBitrate);
    }

    public StringProperty getChannels() {
        return ((AudioProject) currProject).getChannels();
    }

    public void setChannels(String inChannels) {
        ((AudioProject) currProject).setChannels(inChannels);
    }

    public StringProperty getSampleRate() {
        return ((AudioProject) currProject).getSampleRate();
    }

    public void setSampleRate(String inSampleRate) {
        ((AudioProject) currProject).setSampleRate(inSampleRate);
    }

    public StringProperty getOutputFileType() {
        return currProject.getOutputFileType();
    }

    public void setOutputFileType(String inFileType) {
        currProject.setOutputFileType(inFileType);
    }

    public StringProperty getCodec() {
        StringProperty currCodec;
        if (currProject instanceof AudioProject) {
            currCodec = ((AudioProject) currProject).getCodec();
        } else {
            currCodec = ((VideoProject) currProject).getCodec();
        }
        return currCodec;
    }

    public void setCodec(String inCodec) {
        if (currProject instanceof AudioProject) {
            ((AudioProject) currProject).setCodec(inCodec);
        } else {
            ((VideoProject) currProject).setCodec(inCodec);
        }
    }

    /* Return the current projects type (Audio or video) as an enumerated ProjectType */
    public ProjectType getProjectType() {
        ProjectType type = null;
        if (currProject instanceof AudioProject) {
            type = ProjectType.AUDIO_PROJECT;
        } else if (currProject instanceof VideoProject) {
            type = ProjectType.VIDEO_PROJECT;;
        }
        return type;
    }

    public StringProperty getFrameRate() {
        return ((VideoProject) currProject).getFrameRate();
    }

    public StringProperty getFrameSize() {
        return ((VideoProject) currProject).getFrameSize();
    }

    public StringProperty getAudioForVideo() {
        return ((VideoProject) currProject).getAudioForVideo();
    }

    public void setFrameRate(String frameRate) {
        ((VideoProject) currProject).setFrameRate(frameRate);
    }

    public void setFrameSize(String frameSize) {
        ((VideoProject) currProject).setFrameSize(frameSize);
    }

    public void setAudioForVideo(String audioForVideo) {
        ((VideoProject) currProject).setAudioForVideo(audioForVideo);
    }

}
