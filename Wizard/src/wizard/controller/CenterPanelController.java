/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard.controller;

import java.io.File;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Chris
 */
public class CenterPanelController {

    private MainController main;
    SimpleIntegerProperty state = new SimpleIntegerProperty();

    @FXML
    private AnchorPane centerPanel;

    @FXML
    private SplashController splashController;
    @FXML
    private AnchorPane splash;
    @FXML
    private FinaliseController finaliseController;
    @FXML
    private AnchorPane finalise;
    @FXML
    private TypeController typeController;
    @FXML
    private AnchorPane type;
    @FXML
    private FilesController filesController;
    @FXML
    private AnchorPane files;
    @FXML
    private SettingsController settingsController;
    @FXML
    private AnchorPane settings;

    void init(MainController inCont) {
        main = inCont;
        state.set(0);
        splashController.init(state);
        typeController.init(state, this);
        filesController.init(state, this);
        settingsController.init(state, this);
        finaliseController.init(state, this);
        centerPanel.getChildren().clear();
        centerPanel.getChildren().add(splash);

        /* Create listener to change state of wizard */
        state.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal) {
                main.setProgressBarState(state.get());
                centerPanel.getChildren().clear();
                if (state.get() == 1) {
                    centerPanel.getChildren().add(type);
                } else if (state.get() == 2) {
                    centerPanel.getChildren().add(files);
                } else if (state.get() == 3) {
                    settingsController.setProjectType(main.getProjectType());
                    System.out.println("Showing " + main.getProjectType() + " to user.");
                    centerPanel.getChildren().add(settings);
                } else if (state.get() == 4) {
                 //   System.out.println("saved bitrate: '" + main.getBitrate().get() + "', channels: '"
                   //         + main.getChannels().get() + ", codec: '" + main.getCodec().get() + "', sample rate: '" + main.getSampleRate().get()
                     //       + ", profile: '" + main.getProfile().get() + "', output format: '" + main.getOutputFileType().get() + "'");
                    finaliseController.setProjectType(main.getProjectType());
                    centerPanel.getChildren().add(finalise);
                }
            }
        });
    }

    public void createAudioProject() {
        main.createAudioProject();
    }

    public void createVideoProject() {
        main.createVideoProject();
    }

    public MainController.ProjectType getProjectType() {
        return main.getProjectType();
    }

    public void setInputFiles(List<File> inputFiles) {
        main.setInputFiles(inputFiles);
    }

    public void setOutputDirectory(File outputFile) {
        main.setOutputDirectory(outputFile);
    }

    public void setOutputFilename(String text) {
        main.setOutputFilename(text);
    }

    public StringProperty getOutputFileType() {
        return main.getOutputFileType();
    }

    public StringProperty getCodec() {
        return main.getCodec();
    }

    public StringProperty getBitrate() {
        return main.getBitrate();
    }

    public StringProperty getChannel() {
        return main.getChannels();
    }

    public StringProperty getSamplerate() {
        return main.getSampleRate();
    }

    public StringProperty getProfile() {
        return main.getProfile();
    }

    public StringProperty getOutputFilename() {
        return main.getOutputFilename();
    }

    public List<File> getInputFiles() {
        return main.getInputFiles();
    }

    public StringProperty getFrameRate() {
        return main.getFrameRate();
    }

    public StringProperty getFrameSize() {
        return main.getFrameSize();
    }

    public StringProperty getAudioForVideo() {
        return main.getAudioForVideo();
    }

}
