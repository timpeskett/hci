package aes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;

import javafx.event.EventHandler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


public class Intro implements SceneController
{
	private MainApp ma;

	@FXML private HBox videoBannerHbox;
	@FXML private HBox audioBannerHbox;
	@FXML private HBox compileBannerHbox;
	@FXML private ChoiceBox<String> themeChoiceBox;

	@Override
	public void initialize(URL fxmlLocation, ResourceBundle res)
	{

		videoBannerHbox.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e)
			{
				ma.gotoVideo();
			}
		});

		audioBannerHbox.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e)
			{
				ma.gotoAudio();
			}
		});
		compileBannerHbox.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e)
			{
				ma.gotoCompile();
			}
		});

		/* Display available themes */
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				themeChoiceBox.getItems().addAll(ma.getThemes());
				themeChoiceBox.setValue(ma.getCurrentTheme());
			}
		});
				
		themeChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
			{
				ma.setTheme(newValue);
			}
		});
	}

	@Override
	public void setApp(MainApp ma)
	{
		this.ma = ma;
	}
}
