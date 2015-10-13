package aes;

import aes.boundary.MediaWrapper;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.File;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaMarkerEvent;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;


public class Video implements SceneController
{
	private MainApp ma;
	private File currInputFile;
	private MediaWrapper mediaWrapper;

	@FXML
	private Button backButton;

	@FXML
	private TextField inputFilename;
	@FXML
	private Button inputBrowseButton;
	@FXML
	private MediaView mediaView;

	@FXML
	private Button playButton, pauseButton;

	@Override
	public void initialize(URL fxmlLocation, ResourceBundle res)
	{
		backButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e)
			{
				ma.gotoIntro();
			}
		});
		inputBrowseButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e)
			{
				currInputFile = ma.openFile("Open Input File...");
				inputFilename.setText(currInputFile.getAbsolutePath());
				loadMedia(currInputFile);
			}
		});
		playButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e)
			{
				playMedia();
			}
		});
		pauseButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e)
			{
				pauseMedia();
			}
		});


	}


	private void loadMedia(File file)
	{
		mediaWrapper = new MediaWrapper(file);
		mediaWrapper.setTimeUpdate(new EventHandler<MediaMarkerEvent>(){
			@Override
			public void handle(MediaMarkerEvent mme) 
			{
				System.out.println(mme.getMarker().getValue().toSeconds() + " seconds passed");
			}
		});
		mediaWrapper.setMediaView(mediaView);
	}


	private void playMedia()
	{
		if(mediaWrapper != null)
		{
			mediaWrapper.play();
		}
	}

	private void pauseMedia()
	{
		if(mediaWrapper != null)
		{
			mediaWrapper.pause();
		}
	}

	@Override
	public void setApp(MainApp ma)
	{
		this.ma = ma;
	}
}
