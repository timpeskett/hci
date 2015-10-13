/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard_interface.controller;

import wizard_interface.controller.MainController;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import wizard_interface.controller.MainController.ProjectType;
import wizard_interface.model.AudioProject;

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
    VBox content;
    @FXML
    AnchorPane centerPanel;

    /* Splash screen */
    @FXML
    ImageView splashLogo;

    public void init(MainController inMainController) {
        main = inMainController;
        currState = 0;
        centerPanel.setId("centerPanel");
        displaySplash();

    }

    public void displaySplash() {
        try {
            throw new Exception();
        } catch (Exception ex) {
            //Logger.getLogger(CenterPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        splashText.textProperty().set(
                "FFmpeg is the leading multimedia framework, able to decode, encode, transcode, mux, demux,"
                + "stream, filter and play pretty much anything that humans and machines have created.\n\n"
                + "It supports the most obscure ancient formats up to the cutting edge"
                + "No matter if they were designed by some standards committee, the"
                + "community or a corporation.");
        splashText.setFont(Font.font("Verdana", 16));

        btnBounds.getChildren().remove(0, 2); // remove start and back for now, then add back when start is pressed
        content.setAlignment(Pos.CENTER);
        content.setSpacing(30);
        btnBounds.setAlignment(Pos.CENTER);
        btnBounds.setSpacing(200);

        startBtn.setVisible(true);
        nextBtn.setVisible(false);
        backBtn.setVisible(false);
        main.setProgressBarState(0);
    }

    public void displayType() {

        // set next and back invisible for this screen
        nextBtn.setVisible(false);
        backBtn.setVisible(false);
        content.getChildren().clear();
        content.getChildren().add(btnBounds);

        HBox typeHbox = new HBox();
        typeHbox.setSpacing(200);
        Button audioType = new Button("Audio");
        Button videoType = new Button("Video");
        audioType.setScaleX(3);
        audioType.setScaleY(3);
        videoType.setScaleX(3);
        videoType.setScaleY(3);
        typeHbox.getChildren().addAll(audioType, videoType);
        typeHbox.setAlignment(Pos.BOTTOM_CENTER);

        audioType.setOnAction(e -> {
            System.out.println("User wishes to create an audio project");
            main.createAudioProject();
            content.getChildren().remove(typeHbox);
            nextBtnPressed();
        });

        videoType.setOnAction(e -> {
            System.out.println("User wishes to create a video project");
            main.createVideoProject();
            content.getChildren().remove(typeHbox);
            nextBtnPressed();
        });

        content.getChildren().add(0, typeHbox);

    }

    public void displayFiles() {

        content.getChildren().clear();
        content.getChildren().add(btnBounds);
        // create components required to get file info from user and add it to content pane
        List<File> inputFiles = new ArrayList<>();

        // setup input file(s)
        HBox inputFilesHbox = new HBox(40);
        Text inputFilesText = new Text("Input File(s):");
        inputFilesText.setFont(new Font(20));
        inputFilesHbox.setAlignment(Pos.CENTER);
        TextField inputFilesTextArea = new TextField();
        inputFilesTextArea.setPrefColumnCount(40);
        inputFilesTextArea.setEditable(false);
        Button browseInputFiles = new Button("Browse...");
        inputFilesHbox.getChildren().addAll(inputFilesText, inputFilesTextArea, browseInputFiles);
        browseInputFiles.setScaleX(2);
        browseInputFiles.setScaleY(2);
        browseInputFiles.setOnAction(e -> {
            inputFiles.clear();
            inputFilesTextArea.setText("");
            Stage stage = (Stage) centerPanel.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Video File(s)");
            fileChooser.getExtensionFilters().addAll(
                    // new ExtensionFilter("Text Files", "*.txt"),
                    //  new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                    new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"));
            //   new ExtensionFilter("All Files", "*.*"));
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);

            // put all files into an array of files outside lambda, only when there are less than 10 selected for now
            if (selectedFiles.size() < 10) {
                for (int i = 0; i < selectedFiles.size(); i++) {
                    inputFiles.add(selectedFiles.get(i));
                }
            }
            main.setInputFiles(inputFiles);

            // put all file names in input file text area
            for (File currFile : selectedFiles) {
                inputFilesTextArea.appendText(currFile.getName());
                if (!currFile.equals(selectedFiles.get(selectedFiles.size() - 1))) {
                    inputFilesTextArea.appendText(", ");
                }
                // for testing, output files selected
                System.out.println("Input files selected by user:");
                for (int i = 0; i < inputFiles.size(); i++) {
                    System.out.println(inputFiles.get(i).getName());
                }
            }
        });

        // setup output file location
        HBox outputFilesHbox = new HBox(40);
        Text outputFilesText = new Text("Output Location:");
        outputFilesText.setFont(new Font(20));
        outputFilesHbox.setAlignment(Pos.CENTER);
        TextField outputFilesTextArea = new TextField();
        outputFilesTextArea.setPrefColumnCount(40);
        outputFilesTextArea.setEditable(false);
        Button browseOutputFiles = new Button("Browse...");
        outputFilesHbox.getChildren().addAll(outputFilesText, outputFilesTextArea, browseOutputFiles);
        browseOutputFiles.setScaleX(2);
        browseOutputFiles.setScaleY(2);

        browseOutputFiles.setOnAction((ActionEvent e) -> {
            File currOutputDir = outputDirectoryBrowse();
            outputFilesTextArea.setText(currOutputDir.getAbsolutePath());
            main.setOutputDirectory(currOutputDir);
            System.out.println("Output file destination: " + currOutputDir);
        });

        // Fetch the data already entered if possible
        File outF = main.getOutputDirectory();
        if (outF != null) {
            outputFilesTextArea.setText(outF.getAbsolutePath());
        }

        List<File> inF = main.getInputFiles();
        if (inF != null) {
            List<File> inputFilesAlreadySelected = main.getInputFiles();
            for (File currFile : inputFilesAlreadySelected) {
                inputFilesTextArea.appendText(currFile.getName());
                if (!currFile.equals(inputFilesAlreadySelected.get(inputFilesAlreadySelected.size() - 1))) {
                    inputFilesTextArea.appendText(", ");
                }
            }
        }

        content.getChildren().add(0, inputFilesHbox);
        content.getChildren().add(1, outputFilesHbox);
        backBtn.setVisible(true);
        nextBtn.setVisible(true);
    }

    public void displaySettings() {

        final int HORIZONTAL_GAP = 10;
        HBox line1 = new HBox(HORIZONTAL_GAP);
        HBox line2 = new HBox(HORIZONTAL_GAP);
        line1.setAlignment(Pos.CENTER_LEFT);
        line2.setAlignment(Pos.CENTER_LEFT);
        line1.setPadding(new Insets(0, 0, 0, 100)); // top left bot right
        line2.setPadding(new Insets(0, 0, 0, 100)); // top left bot right
        content.setPadding(new Insets(20, 0, 20, 0));

        content.getChildren().clear();
        ProjectType currProjectType = main.getProjectType();
        /* Display proper project settings */
        if (currProjectType == ProjectType.AUDIO_PROJECT) {
            Text audioSettingsText = new Text("Audio Settings");
            audioSettingsText.setFont(new Font(25));

            /* Profiles for quick settings */
            HBox profileContainer = new HBox(HORIZONTAL_GAP);
            profileContainer.setPadding(new Insets(0, 0, 20, 0)); // top left bot right
            profileContainer.setAlignment(Pos.CENTER);
            Text profileText = new Text("Profile");
            profileText.setFont(new Font(20));
            ComboBox profileBox = new ComboBox();
            profileBox.getItems().addAll("Low Quality", "Medium Quality", "Best Quality");
            profileContainer.getChildren().addAll(profileText, profileBox);

            /* Audio format */
            HBox formatContainer = new HBox(HORIZONTAL_GAP);
            // formatContainer.setAlignment(Pos.CENTER);
            Text formatText = new Text("Format ");
            formatText.setFont(new Font(20));
            ComboBox formatBox = new ComboBox();
            formatBox.getItems().addAll("Ogg", "MP3", "WAV", "FLAC", "WMA");
            formatContainer.getChildren().addAll(formatText, formatBox);

            /* Audio codec */
            HBox codecContainer = new HBox(HORIZONTAL_GAP);
            //codecContainer.setAlignment(Pos.CENTER);
            Text codecText = new Text("Codec     ");
            codecText.setFont(new Font(20));
            ComboBox codecBox = new ComboBox();
            //codecBox.getItems().addAll("MP3", "PCM", "MPEG-4 Part 2", "H.264", "WMV", "FLAC");
            codecContainer.getChildren().addAll(codecText, codecBox);

            /* Audio channels */
            HBox channelsContainer = new HBox(HORIZONTAL_GAP);
            //  channelsContainer.setAlignment(Pos.CENTER);
            Text channelsText = new Text("Channels");
            channelsText.setFont(new Font(20));
            ComboBox channelsBox = new ComboBox();
            channelsBox.getItems().addAll("Mono", "Stereo");//, "2.1", "3.0", "4.0", "5.0", "5.1", "6.0", "6.1", "7.0", "7.1");
            channelsContainer.getChildren().addAll(channelsText, channelsBox);

            /* Bit-rate */
            HBox bitrateContainer = new HBox(HORIZONTAL_GAP);
            //  bitrateContainer.setAlignment(Pos.CENTER);
            Text bitrateText = new Text("Bit-rate");
            bitrateText.setFont(new Font(20));
            ComboBox bitrateBox = new ComboBox();
            bitrateBox.getItems().addAll("64 kb/s", "80 kb/s", "96 kb/s", "128 kb/s", "160 kb/s", "192 kb/s", "320 kb/s");
            bitrateContainer.getChildren().addAll(bitrateText, bitrateBox);

            /* Sample rate */
            HBox sampleRateContainer = new HBox(HORIZONTAL_GAP);
            sampleRateContainer.setAlignment(Pos.CENTER);
            Text sampleRateText = new Text("Sample Rate");
            sampleRateText.setFont(new Font(20));
            ComboBox sampleRateBox = new ComboBox();
            sampleRateBox.getItems().addAll("22050 Hz", "32000 Hz", "32000 Hz", "44100 Hz", "48000 Hz");
            sampleRateContainer.getChildren().addAll(sampleRateText, sampleRateBox);

            /* Load values user has already set or use defaults */
            if (main.getSampleRate() == null) {
                sampleRateBox.setValue("44100 Hz");
            } else {
                sampleRateBox.setValue(main.getSampleRate());
            }

            if (main.getProfile() == null) {
                profileBox.setValue("Best Quality");
            } else {
                profileBox.setValue(main.getProfile());
            }

            if (main.getChannels() == null) {
                channelsBox.setValue("Stereo");
            } else {
                channelsBox.setValue(main.getChannels());
            }

            if (main.getCodec() == null) {
                codecBox.setValue("MP3");
                codecBox.disableProperty().set(true);
            } else {
                codecBox.setValue(main.getCodec());
            }

            if (main.getBitrate() == null) {
                bitrateBox.setValue("320 kb/s");
            } else {
                bitrateBox.setValue(main.getBitrate());
            }

            if (main.getOutputFileType() == null) {
                formatBox.setValue("MP3");
            } else {
                String currFormat = main.getOutputFileType();
                formatBox.setValue(currFormat);

                /* if codec can not be changed, disable the box otherwise populate codec box with possible selections */
                if (currFormat.equals("WAV")) {
                    codecBox.getItems().addAll("MP3", "PCM");
                } else if (currFormat.equals("WMA")) {
                    codecBox.getItems().addAll("WMA", "PCM");
                } else {
                    codecBox.setDisable(true);
                }
            }


            /* Add lines of components to content pane */
            line1.getChildren().addAll(formatContainer, codecContainer);
            line2.getChildren().addAll(channelsContainer, bitrateContainer, sampleRateContainer);
            content.getChildren().addAll(audioSettingsText, profileContainer, line1, line2, btnBounds);

            /* Update project with current values */
            String currProfile = (String) profileBox.getSelectionModel().getSelectedItem();
            String currFormat = (String) formatBox.getSelectionModel().getSelectedItem();
            String currCodec = (String) codecBox.getSelectionModel().getSelectedItem();
            String currChannels = (String) channelsBox.getSelectionModel().getSelectedItem();
            String currBitrate = (String) bitrateBox.getSelectionModel().getSelectedItem();
            String currSampleRate = (String) sampleRateBox.getSelectionModel().getSelectedItem();
            main.setAudioProjectSettings(currProfile, currFormat, currCodec, currChannels, currBitrate, currSampleRate);

            /* Create events for when any dialog box is changed which updates model*/
            profileBox.setOnAction(e -> {
                String newProfile = (String) profileBox.getSelectionModel().getSelectedItem();
                main.setProfile(newProfile);
                switch (newProfile) {
                    case "Low Quality":
                        channelsBox.setValue("Mono");
                        bitrateBox.setValue("64 kb/s");
                        formatBox.setValue("MP3");
                        sampleRateBox.setValue("44100 Hz");
                        codecBox.setValue("MP3");
                        codecBox.disableProperty().set(true);
                        break;
                    case "Medium Quality":
                        channelsBox.setValue("Stereo");
                        bitrateBox.setValue("128 kb/s");
                        formatBox.setValue("MP3");
                        sampleRateBox.setValue("44100 Hz");
                        codecBox.setValue("MP3");
                        codecBox.disableProperty().set(true);
                        break;
                    case "Best Quality":
                        channelsBox.setValue("Stereo");
                        bitrateBox.setValue("320 kb/s");
                        formatBox.setValue("MP3");
                        sampleRateBox.setValue("44100 Hz");
                        codecBox.setValue("MP3");
                        codecBox.disableProperty().set(true);
                        break;
                }
            });

            formatBox.setOnAction(e -> {
                /*  If format is MP3, codec must be MP3
                 *  If format is FLAC, codec must be FLAC
                 *  If format is OGG, codec must be Ogg Vorbis
                 *  If format is WMA, codec can be WMA or PCM
                 *  If format is WAV, codec can be PCM or MP3
                 */
                String newFormat = (String) formatBox.getSelectionModel().getSelectedItem();
                main.setOutputFileType(newFormat);
                switch (newFormat) {
                    case "MP3":
                        main.setCodec("MP3");
                        codecBox.setValue("MP3");
                        codecBox.disableProperty().set(true);
                        break;
                    case "FLAC":
                        main.setCodec("FLAC");
                        codecBox.setValue("FLAC");
                        codecBox.disableProperty().set(true);
                        break;
                    case "Ogg":
                        main.setCodec("Ogg Vorbis");
                        codecBox.setValue("Ogg Vorbis");
                        codecBox.disableProperty().set(true);
                        break;
                    case "WAV":
                        codecBox.disableProperty().set(false);
                        codecBox.getItems().clear();
                        codecBox.getItems().addAll("PCM", "MP3");
                        codecBox.setValue("MP3");
                        break;
                    case "WMA":
                        codecBox.disableProperty().set(false);
                        codecBox.getItems().clear();
                        codecBox.getItems().addAll("PCM", "WMA");
                        codecBox.setValue("WMA");
                        break;
                }
            });

            codecBox.setOnAction(e -> {
                main.setCodec((String) codecBox.getSelectionModel().getSelectedItem());
            });

            channelsBox.setOnAction(e -> {
                main.setChannels((String) channelsBox.getSelectionModel().getSelectedItem());
            });

            bitrateBox.setOnAction(e -> {
                main.setBitrate((String) bitrateBox.getSelectionModel().getSelectedItem());
            });

            sampleRateBox.setOnAction(e -> {
                main.setSampleRate((String) sampleRateBox.getSelectionModel().getSelectedItem());
            });

        } else {
            Text videoSettingsText = new Text("Video Settings");
            videoSettingsText.setFont(new Font(25));

            // required components: profile, codec, bitrate, framesize?(wxh), FPS, aspect ratio
            
            
            
            /* Profiles for quick settings */
            HBox profileContainer = new HBox(HORIZONTAL_GAP);
            profileContainer.setPadding(new Insets(0, 0, 20, 0)); // top left bot right
            profileContainer.setAlignment(Pos.CENTER);
            Text profileText = new Text("Profile");
            profileText.setFont(new Font(20));
            ComboBox profileBox = new ComboBox();
            profileBox.getItems().addAll("Low Quality", "Medium Quality", "Best Quality");
            profileContainer.getChildren().addAll(profileText, profileBox);

            // supported codecs and formats:
            // AVI: almost anything
            // MPG
            // WMV
            // MOV
            // MKV: almost anything
            // FLV: MP3, AAC
            // MP4: MPEG-2 Part 2, MPEG-4 ASP, H.264/MPEG-4 AVC, H.263
            
            /*
            FORMATS:
            AVI
            ASF
            MOV
            FLV or F4V
            
            
            CODECS:
            h.264 (or MPEG 4 part 10 & AVC)
            MPEG 2
            VP6
            VC 1
            
            
            */
            
            
            /* Video format */
            HBox formatContainer = new HBox(HORIZONTAL_GAP);
            // formatContainer.setAlignment(Pos.CENTER);
            Text formatText = new Text("Format ");
            formatText.setFont(new Font(20));
            ComboBox formatBox = new ComboBox();
            formatBox.getItems().addAll("AVI", "MKV", "MP4", "FLV", "WMA");
            formatContainer.getChildren().addAll(formatText, formatBox);

            /* Audio codec */
            HBox codecContainer = new HBox(HORIZONTAL_GAP);
            //codecContainer.setAlignment(Pos.CENTER);
            Text codecText = new Text("Codec     ");
            codecText.setFont(new Font(20));
            ComboBox codecBox = new ComboBox();
            //codecBox.getItems().addAll("MP3", "PCM", "MPEG-4 Part 2", "H.264", "WMV", "FLAC");
            codecContainer.getChildren().addAll(codecText, codecBox);
        }

    }

    public void displayFinalise() {
        content.getChildren().clear();
        content.getChildren().add(btnBounds);
    }

    // action events below
    public void startBtnPressed() {
        System.out.println("Start button pressed");
        btnBounds.getChildren().addAll(backBtn, nextBtn);
        btnBounds.getChildren().remove(startBtn);
        btnBounds.setAlignment(Pos.BOTTOM_CENTER);
        //btnBounds.setAlignment(Pos.BOTTOM_LEFT);
        content.getChildren().removeAll(splashLogo, splashText);
        // nextBtn.setVisible(true);
        //backBtn.setVisible(true);
        main.setProgressBarState(1);
        displayType();
        currState++;
    }

    public void nextBtnPressed() {
        if (currState < 4) {
            currState++;
            if (currState == 1) {
                displayType();
            } else if (currState == 2) {
                displayFiles();
            } else if (currState == 3) {
                displaySettings();
            } else if (currState == 4) {
                displayFinalise();
            }
        }
        main.setProgressBarState(currState);
    }

    public void backBtnPressed() {
        if (currState > 1) {
            currState--;
            if (currState == 1) {
                displayType();
            } else if (currState == 2) {
                displayFiles();
            } else if (currState == 3) {
                displaySettings();
            } else if (currState == 4) {
                displayFinalise();
            }
        }
        main.setProgressBarState(currState);
    }

    public File outputDirectoryBrowse() {
        File outputFile = null;
        Stage stage = (Stage) centerPanel.getScene().getWindow();
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        final File selectedDirectory = directoryChooser.showDialog(stage);
        outputFile = selectedDirectory;
        return outputFile;
    }

}
