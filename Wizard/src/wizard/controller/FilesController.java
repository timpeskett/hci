/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard.controller;

import java.io.File;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import wizard.controller.MainController.ProjectType;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class FilesController {

    private SimpleIntegerProperty state;
    private CenterPanelController centerPanel;
    ProjectType projType;
    List<File> inputFiles = null;
    File outputDir = null;

    @FXML
    private AnchorPane filesAnchor;
    @FXML
    private Button backBtn;
    @FXML
    private Button nextBtn;

    @FXML
    private TextField inputFilesTextArea;
    @FXML
    private TextField outputDirectoryTextArea;
    @FXML
    private TextField outputFileNameTextArea;
    @FXML
    private Button inputFilesBrowse;
    @FXML
    private Button outputDirectoryBrowse;

    private String newFileName;

    void init(SimpleIntegerProperty inState, CenterPanelController inCenterPanel) {
        state = inState;
        centerPanel = inCenterPanel;
        inputFilesBrowse.getStyleClass().clear();
        outputDirectoryBrowse.getStyleClass().clear();
        inputFilesBrowse.setId("browseButtons");
        outputDirectoryBrowse.setId("browseButtons");
        if (!"".equals(centerPanel.getDefaultOutputLocation())) {
            System.out.println("asd");
            outputDirectoryTextArea.setText(centerPanel.getDefaultOutputLocation());
        }
        nextBtn.setDisable(true);
    }

    public void backBtnPressed() {
        state.set(state.subtract(1).get());
    }

    public void nextBtnPressed() {
        newFileName = outputFileNameTextArea.getText();
        if (!doesFileExist(outputDirectoryTextArea.getText() + "\\" + newFileName.split("\\.")[0])) {
            centerPanel.setOutputFilename(outputFileNameTextArea.getText());
            state.set(state.add(1).get());
        } else {
            System.out.println("ERROR: File already exists at " + outputDirectoryTextArea.getText() + "\\" + newFileName);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error: File already exists named " + newFileName + " at this location.");
            alert.setContentText("Please choose a new file name or output directory.");

            alert.showAndWait();
        }
    }

    public void inputFilesBrowsePressed() {
        projType = centerPanel.getProjectType();
        inputFilesTextArea.setText("");
        Stage stage = (Stage) filesAnchor.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        /* Set which types of files a user can input */
        if (projType == ProjectType.AUDIO_PROJECT) {
            fileChooser.setTitle("Select Audio File(s)");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"));
        } else if (projType == ProjectType.VIDEO_PROJECT) {
            fileChooser.setTitle("Select Video File(s)");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Video Files", "*.avi", "*.mov", "*.vob", ".flv", ".f4v", ".mkv"));
        }
        inputFiles = fileChooser.showOpenMultipleDialog(stage);

        centerPanel.setInputFiles(inputFiles);

        /* Put all file names in input file text area */
        if (inputFiles != null) {
            for (File currFile : inputFiles) {
                inputFilesTextArea.appendText(currFile.getName());
                if (!currFile.equals(inputFiles.get(inputFiles.size() - 1))) {
                    inputFilesTextArea.appendText(", ");
                }
            }
        }

        // for testing, output files selected
        if (inputFiles != null) {
            System.out.println("Input files selected by user:");
            for (int i = 0; i < inputFiles.size(); i++) {
                System.out.println(inputFiles.get(i).getName());
            }
        }
        // ---------------------------------
        if (inputFiles != null) {
            newFileName = inputFiles.get(0).getName().split("\\.")[0] + 1;// + "." + inputFiles.get(0).getName().split("\\.")[1];
            outputFileNameTextArea.setText(newFileName);
        }

        if (centerPanel.getInputFiles() != null && centerPanel.getOutputFileLoc() != null) {
            nextBtn.setDisable(false);
        }
    }

    /* Not working yet, meant to check if there is a file already existing at the path with the same name as newFilename */
    private boolean doesFileExist(String path) {
        /* System.out.println("original path: " + path);
         String newPath = path.substring(0, path.length()-1);*/

        if (new File(path).exists()) {
            //System.out.println("FILE EXISTS: " + newPath);
            return true;
        } else {
            //  System.out.println("FILE DOES NOT EXIST: " + newPath);
            return false;
        }
    }

    public void outputDirectoryBrowse() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) filesAnchor.getScene().getWindow();
        outputDir = directoryChooser.showDialog(stage);
        if (outputDir != null) {
            outputDirectoryTextArea.setText(outputDir.getAbsolutePath());
            centerPanel.setOutputDirectory(outputDir);
            System.out.println("Output file destination: " + outputDir);
            if (centerPanel.getInputFiles() != null && centerPanel.getOutputFileLoc() != null) {
                nextBtn.setDisable(false);
            }
        }
    }

}
