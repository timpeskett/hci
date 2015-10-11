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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        content.getChildren().clear();
        content.getChildren().add(btnBounds);
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
