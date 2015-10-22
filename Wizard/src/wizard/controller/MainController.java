package wizard.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import wizard.Wizard;
import wizard.model.AbstractProject;
import wizard.model.AudioProject;
import wizard.model.VideoProject;
import wizard.model.WizardConfig;

/* Name: MainController
 * Description: Controller class for Main.fxml
 *              Main controller is the top-level controller that holds all
 *              window components together and stores the current project 
 *              data.
 * @author Chris
 */
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

    /* FXML components for injection */
    @FXML
    AnchorPane mainAnchor;

    /* Stores current user profile information in memory */
    private WizardConfig config;
    Properties props = new Properties();

    /* The current project being manipulated by the user */
    private AbstractProject currProject;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        config = new WizardConfig();
        loadProperties();
        navPanelController.init(this);
        centerPanelController.init(this);
        rightPanelController.init(this);
        setBG(config.getBackground());

        /* Save user properties before closing program */
        Platform.runLater(() -> {
            ((Stage) (mainAnchor.getScene().getWindow())).setOnHiding((WindowEvent event) -> {
                saveProperties();
            });
        });

        /* Once components are setup, load colour scheme */
        Platform.runLater(() -> {
            setTheme(config.getColourScheme());
        });
    }

    /* Load another stylesheet */
    public void setTheme(String inTheme) {
        Platform.runLater(() -> {
            Scene scene = mainAnchor.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(Wizard.class.getResource("res/" + inTheme + ".css").toExternalForm());
        });
    }

    /* Change the current background of the program*/
    public void setBG(String inBG) {
        mainAnchor.setId(inBG);
    }

    public String getDefaultOutputLocation() {
        return config.getDefaultOutputLoc();
    }

    public String getShowSplashscreen() {
        return config.getDisplaySplash();
    }

    public void setProgressBarState(int inState) {
        navPanelController.setPanelState(inState);
    }

    public void createAudioProject() {
        currProject = new AudioProject();
        if (!"".equals(config.getDefaultOutputLoc())) {
            setOutputDirectory(new File(config.getDefaultOutputLoc()));
        }
    }

    public void createVideoProject() {
        currProject = new VideoProject();
        if (!"".equals(config.getDefaultOutputLoc())) {
            setOutputDirectory(new File(config.getDefaultOutputLoc()));
        }
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

    public void saveProperties() {
        try {
            String COLOUR_SCHEME = config.getColourScheme();
            String LANGUAGE = config.getLanguage();
            String BACKGROUND = config.getBackground();
            String DEFAULT_OUTPUT_LOC = config.getDefaultOutputLoc();
            String DISPLAY_SPLASH = config.getDisplaySplash();

            //create a properties file
            props.setProperty("Colour scheme", COLOUR_SCHEME);
            props.setProperty("Language", LANGUAGE);
            props.setProperty("Background", BACKGROUND);
            props.setProperty("Default output location", DEFAULT_OUTPUT_LOC);
            props.setProperty("Display splashscreen?", DISPLAY_SPLASH);
            OutputStream out = new FileOutputStream("UserConfig.data");
            props.store(out, "User properties for FFMpeg Wizard Interface");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadProperties() {
        try {
            InputStream in = new FileInputStream("UserConfig.data");
            props.load(in);
            String COLOUR_SCHEME = props.getProperty("Colour scheme");
            String LANGUAGE = props.getProperty("Language");
            String BACKGROUND = props.getProperty("Background");
            String DEFAULT_OUTPUT_LOC = props.getProperty("Default output location");
            String DISPLAY_SPLASH = props.getProperty("Display splashscreen?");

            config.setColourScheme(COLOUR_SCHEME);
            config.setLanguage(LANGUAGE);
            config.setBackground(BACKGROUND);
            config.setDefaultOutputLoc(DEFAULT_OUTPUT_LOC);
            config.setDisplaySplash(DISPLAY_SPLASH);

            //System.out.println(COLOUR_SCHEME + " " + LANGUAGE + " " + BACKGROUND + " " + DEFAULT_OUTPUT_LOC + " " + DISPLAY_SPLASH);
        } catch (FileNotFoundException e) {
            //System.out.println("No user data exists, creating default profile.");
        } catch (IOException e2) {
            //System.out.println("Error reading userConfig.data");
        }
    }

    public WizardConfig getConfig() {
        return config;
    }

}
