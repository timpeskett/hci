package wizard.controller;

import java.awt.Dimension;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import wizard.Wizard;

/* Name: RightPanelController
 * Description: Controller class for Right Panel.fxml
 *              Controller class for the right hand panel that controls the 
 *              manage and help buttons and icons.
 * @author Chris
 */
public class RightPanelController {

    private MainController main;

    /* FXML components for injection */
    @FXML
    private VBox manageBox;
    @FXML
    private ImageView manageImage;
    @FXML
    private ImageView helpImage;
    @FXML
    private Label manageText;

    private ManagementController managementController;

    void init(MainController inMain) {
        main = inMain;
        manageImage.setId("manage");
        helpImage.setId("help");
    }

    public void manageBoxClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(Wizard.class.getResource("view/Management.fxml"));
        AnchorPane rootLayout = (AnchorPane) loader.load();

        // Give the controller access to the main app
        managementController = loader.getController();
        managementController.init(this, main.getConfig());

        Scene scene = new Scene(rootLayout);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        final Dimension d = new Dimension(900, 250);
        stage.resizableProperty().set(false);
        stage.setWidth(d.width);
        stage.setHeight(d.height);
        stage.setTitle("FFMpeg Settings");
        stage.setScene(scene);
        stage.show();
        System.out.println("");
    }

    public void helpBoxClicked() {
        JOptionPane optionPane = new JOptionPane();
        optionPane.setMessage("Would load documentation and help website if we had one (https://ffmpeg.org/ffmpeg.html)");
        JDialog dialog = optionPane.createDialog("Help");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    public void setTheme(String inTheme) {
        main.setTheme(inTheme);
    }

    public void setBg(String toString) {
        main.setBG(toString);
    }

}
