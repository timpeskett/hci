
package wizard.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class NavigationPanelController {

    private MainController main;

    /* FXML components for injection */
    @FXML
    private ImageView ffmpegLogo;
    @FXML
    private HBox type;
    @FXML
    private HBox files;
    @FXML
    private HBox settings;
    @FXML
    private HBox finalise;
    @FXML
    private ImageView arrow1;
    @FXML
    private ImageView arrow2;
    @FXML
    private ImageView arrow3;
    @FXML
    private Label progressText;

    /* Name: NavigationPanelController
     * Description: Controller class for Navigation Panel.fxml
     *              Navigation Panel is the upper panel that displays the current 
     *              progress of the wizard to the user.
     * @author Chris
     */
    public void init(MainController inMainController) {
        main = inMainController;
        setPanelState(0);
        ffmpegLogo.setId("logo");
        arrow1.setId("arrow");
        arrow2.setId("arrow");
        arrow3.setId("arrow");
    }

    public void setPanelState(int inState) {
        if (inState == 0) {
            progressText.setOpacity(0);
            type.setOpacity(0);
            arrow1.setOpacity(0);
            files.setOpacity(0);
            settings.setOpacity(0);
            finalise.setOpacity(0);
            arrow2.setOpacity(0);
            arrow3.setOpacity(0);
        } else if (inState == 1) {
            progressText.setOpacity(1);
            type.setOpacity(1.0);
            arrow1.setOpacity(0.65);
            files.setOpacity(0.2);
            settings.setOpacity(0.2);
            finalise.setOpacity(0.2);
            arrow2.setOpacity(0.2);
            arrow3.setOpacity(0.2);
        } else if (inState == 2) {
            progressText.setOpacity(1);
            type.setOpacity(1.0);
            arrow1.setOpacity(0.65);
            files.setOpacity(1.0);
            settings.setOpacity(0.2);
            finalise.setOpacity(0.2);
            arrow2.setOpacity(0.65);
            arrow3.setOpacity(0.2);
        } else if (inState == 3) {
            progressText.setOpacity(1);
            type.setOpacity(1.0);
            arrow1.setOpacity(0.65);
            files.setOpacity(1.0);
            settings.setOpacity(1.0);
            finalise.setOpacity(0.2);
            arrow2.setOpacity(0.65);
            arrow3.setOpacity(0.65);
        } else if (inState == 4) {
            progressText.setOpacity(1);
            type.setOpacity(1.0);
            arrow1.setOpacity(0.65);
            files.setOpacity(1.0);
            settings.setOpacity(1.0);
            finalise.setOpacity(1.0);
            arrow2.setOpacity(0.65);
            arrow3.setOpacity(0.65);
        }
    }
}
