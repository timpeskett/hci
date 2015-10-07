/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard_ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Wizard_UI extends Application {

    private Stage window;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        Button button = new Button();
        button.setText("Click me");
        BorderPane layout = new BorderPane();
        layout.setId("background"); // make sure id matches css #

        Parent navigationPanel = FXMLLoader.load(getClass().getResource("Navigation Panel.fxml"));
        layout.setTop(navigationPanel);

        Parent rightPanel = FXMLLoader.load(getClass().getResource("Right Panel.fxml"));
        layout.setRight(rightPanel);

        Parent center = FXMLLoader.load(getClass().getResource("Center Panel.fxml"));
        VBox centerPane = new VBox();
        centerPane.setPadding(new Insets(0, 0, 40, 0)); // top left bot right
        centerPane.getChildren().add(center);
        layout.setCenter(centerPane);

        Pane blank = new Pane();
        layout.setBottom(blank);

        scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("Orange.css").toExternalForm());

        final Dimension d = new Dimension(1100, 600);
        window.setScene(scene);
        window.resizableProperty().set(false);
        window.setWidth(d.width);
        window.setHeight(d.height);
        window.setTitle("FFmpeg Wizard Interface");

        window.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
