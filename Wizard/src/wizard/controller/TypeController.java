/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TypeController {

    private CenterPanelController centerPanel;
    private SimpleIntegerProperty state;
    @FXML
    private Button Audio;
    @FXML
    private Button Video;

    public void init(SimpleIntegerProperty inState, CenterPanelController inCenterPanel) {
        state = inState;
        centerPanel = inCenterPanel;
    }

    public void audioBtnPressed() {
        centerPanel.createAudioProject();
        state.set(state.add(1).get());
    }

    public void videoBtnPressed() {
        centerPanel.createVideoProject();
        state.set(state.add(1).get());
    }


}
