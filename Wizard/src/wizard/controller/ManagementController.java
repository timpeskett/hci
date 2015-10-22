/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import wizard.model.WizardConfig;

/* Name: ManagementController
 * Description: Controller class for Management.fxml
 *              Management is the settings window that is accessed via clicking
 *              the manage cog on the right hand navigation menu
 * @author Chris
 */
public class ManagementController {

    private RightPanelController rightPanel;
    private WizardConfig config;

    /* FXML components for injection */
    @FXML
    private Button defaultOutputLocBrowse;
    @FXML
    private AnchorPane managementAnchor;

    @FXML
    private ComboBox colourSchemeBox;
    @FXML
    private ComboBox languageBox;
    @FXML
    private ComboBox backgroundBox;
    @FXML
    private TextField defaultOutputLoc;
    @FXML
    private CheckBox displaySplashCheckbox;

    public void init(RightPanelController inRightPanelController, WizardConfig inConfig) {
        config = inConfig;
        rightPanel = inRightPanelController;
        setPreferences();
        setValues();
    }

    public void colourSchemeChanged() {
        config.setColourScheme(colourSchemeBox.getValue().toString());
        rightPanel.setTheme(colourSchemeBox.getValue().toString());
    }

    public void languageChanged() {
        config.setLanguage(languageBox.getValue().toString());
        // todo: implement a way to change language
    }

    public void backgroundChanged() {
        config.setBackground(backgroundBox.getValue().toString());
        rightPanel.setBg(backgroundBox.getValue().toString());
    }

    public void defaultOutputLocPressed() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) managementAnchor.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            defaultOutputLoc.setText(selectedDirectory.getAbsolutePath());
            config.setDefaultOutputLoc(selectedDirectory.getAbsolutePath());
        }
    }

    public void displaySplashScreenChanged() {
        //System.out.println("todo: user changed splash screen option to " + displaySplashCheckbox.isSelected());
        String str = "";
        if (displaySplashCheckbox.isSelected()) {
            str = "yes";
        } else {
            str = "no";
        }
        config.setDisplaySplash(str);
    }

    private void setPreferences() {
        if ("yes".equals(config.getDisplaySplash())) {
            displaySplashCheckbox.setSelected(true);
        } else {
            displaySplashCheckbox.setSelected(false);
        }
        defaultOutputLoc.setText(config.getDefaultOutputLoc());
        backgroundBox.setValue(config.getBackground());
        languageBox.setValue(config.getLanguage());
        colourSchemeBox.setValue(config.getColourScheme());
    }

    private void setValues() {
        colourSchemeBox.getItems().addAll("Orange", "Black");
        languageBox.getItems().addAll("English");
        backgroundBox.getItems().addAll("bg1", "bg2", "bg3", "bg4");
        
    }

}
