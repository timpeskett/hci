package wizard.controller;

import java.io.File;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import wizard.controller.MainController.ProjectType;

/* Name: FilesController
 * Description: Controller class for Files.fxml
 *              Files is a sub-window of the center panel and controls all
 *              user input relative to input and output files
 * @author Chris
 */
public class FilesController {

    private SimpleIntegerProperty state; // interger representing current state of program
    private CenterPanelController centerPanel;
    private ProjectType projType;
    private List<File> inputFiles = null;
    private File outputDir = null;
    private String newFileName;

    /* FXML components for injection */
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

    void init(SimpleIntegerProperty inState, CenterPanelController inCenterPanel) {
        state = inState;
        centerPanel = inCenterPanel;

        /* Set styles for browse buttons */
        inputFilesBrowse.getStyleClass().clear();
        outputDirectoryBrowse.getStyleClass().clear();
        inputFilesBrowse.setId("browseButtons");
        outputDirectoryBrowse.setId("browseButtons");

        /* Create tooltips for components */
        inputFilesBrowse.setTooltip(new Tooltip("Select input file(s)"));
        outputDirectoryBrowse.setTooltip(new Tooltip("Select output file name"));
        nextBtn.setTooltip(new Tooltip("Continue to project settings"));
        backBtn.setTooltip(new Tooltip("Go back to project type selection"));
        inputFilesTextArea.setTooltip(new Tooltip("Click on browse to add files for conversion"));
        outputDirectoryTextArea.setTooltip(new Tooltip("Click on browse to add output file"));
        outputFileNameTextArea.setTooltip(new Tooltip("Enter a name for the output file"));

        /* If we have loaded a default output location, use it */
        if (!"".equals(centerPanel.getDefaultOutputLocation())) {
            outputDirectoryTextArea.setText(centerPanel.getDefaultOutputLocation());
        }

        nextBtn.setDisable(true);
    }

    public void backBtnPressed() {
        state.set(state.subtract(1).get());
    }

    public void nextBtnPressed() {
        // conditions on pressing next:
        // 1. new filename length not > than 255
        // 2. new filename can not exist at output location
        newFileName = outputFileNameTextArea.getText();
        if (!doesFileExist(outputDirectoryTextArea.getText() + "\\" + newFileName.split("\\.")[0])) {
            if (newFileName.length() <= 255) { // filename can't be greater than 255 characters
                centerPanel.setOutputFilename(outputFileNameTextArea.getText());
                state.set(state.add(1).get());
            } else {
                /*  Alert alert = new Alert(AlertType.ERROR);
                 alert.setTitle("Error");
                 alert.setHeaderText("New file name is too long.");
                 //alert.setContentText("Please choose a new file name or output directory.");

                 alert.showAndWait();*/
                JOptionPane optionPane = new JOptionPane();
                optionPane.setMessage("Please choose a new file name or output directory.");
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
            }
        } else {
            System.out.println("ERROR: File already exists at " + outputDirectoryTextArea.getText() + "\\" + newFileName);
            /*Alert alert = new Alert(AlertType.ERROR);
             alert.setTitle("Error");
             alert.setHeaderText("Error: File already exists named " + newFileName + " at this location.");
             alert.setContentText("Please choose a new file name or output directory.");

             alert.showAndWait();*/
            JOptionPane optionPane = new JOptionPane();
            optionPane.setMessage("Error: File already exists named " + newFileName + " at this location.");
            JDialog dialog = optionPane.createDialog("Error");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
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
                    new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac", "*.flac", "*.ogg", "*.wma"));
        } else if (projType == ProjectType.VIDEO_PROJECT) {
            fileChooser.setTitle("Select Video File(s)");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Video Files", "*.avi", "*.mpeg", "*.mov", "*.wmv", "*.m4v", "*.vob", "*.ogg", "*.flv", "*.f4v", "*.mkv", "*.mp4", "*.asf"));
        }
        inputFiles = fileChooser.showOpenMultipleDialog(stage);
        System.out.println(inputFiles.toArray()[0].toString());

        if (inputFiles.size() > 10) {
            JOptionPane optionPane = new JOptionPane();
            optionPane.setMessage("Unable to use more than 10 video files.");
            JDialog dialog = optionPane.createDialog("Error");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
        } else {
            centerPanel.setInputFiles(inputFiles);

            /* Put all file names in input file text area */
            if (inputFiles != null) {
                newFileName = inputFiles.get(0).getName().split("\\.")[0] + 1;// + "." + inputFiles.get(0).getName().split("\\.")[1];
                outputFileNameTextArea.setText(newFileName);
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

            /* Only enable next button if we have input files as well as an output file location */
            if (centerPanel.getInputFiles() != null && centerPanel.getOutputFileLoc() != null) {
                nextBtn.setDisable(false);
            }
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
            //System.out.println("Output file destination: " + outputDir);
            if (centerPanel.getInputFiles() != null && centerPanel.getOutputFileLoc() != null) {
                nextBtn.setDisable(false);
            }
        }
    }

}
