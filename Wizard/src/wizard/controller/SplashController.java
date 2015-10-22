
package wizard.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import wizard.Wizard;

    /* Name: SplashController
     * Description: Controller class for Splash.fxml
     *              Displays the splash screen to the user upon loading program
     * @author Chris
     */
public class SplashController {

    SimpleIntegerProperty state;
    /* FXML components for injection */
    @FXML
    private AnchorPane splashPane;
    @FXML
    private Button startBtn;
    @FXML
    private ImageView splashLogo;

    void init(SimpleIntegerProperty inState) {
        state = inState;
        Image img = new Image(Wizard.class.getResourceAsStream("res/splashLogo.png"));
        splashLogo.setImage(img);
    }

    public void startBtnPressed() {
        state.set(state.add(1).get());
    }
}
