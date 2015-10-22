package aes;

import aes.boundary.MediaWrapper;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.File;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaMarkerEvent;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.util.Duration;


public class Video implements SceneController
{
	private MainApp ma;
	private File currInputFile, currOutputDirectory, currOutputBasename;
	private MediaWrapper mediaWrapper;

	/* Navigation controls */
	@FXML private Button backButton;

	/* Input related controls */
	@FXML private TextField inputFilename;
	@FXML private Button inputBrowseButton;
	@FXML private ChoiceBox inputFramerateChoiceBox;

	/* Output related controls */
	@FXML private TextField outputDirectory, outputBasename;
	@FXML private Button outputBrowseButton;
	@FXML private ChoiceBox outputFramerateChoiceBox, filetypeChoiceBox;

	/* Conversion related controls */
	@FXML private Text convertPercentage;
	@FXML private ProgressBar progressBar;
	@FXML private Button convertButton;

	/* Preview related controls */
	@FXML private MediaView mediaView;
	@FXML private Button playButton, pauseButton;


	@Override
	public void initialize(URL fxmlLocation, ResourceBundle res)
	{
		/* Navigate back to intro when back pressed */
		backButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e)
			{
				pauseMedia();
				ma.gotoIntro();
			}
		});

		/* Load input file when browse button selected */
		inputBrowseButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e)
			{
				File inFile = ma.openFile("Open Input File...");

				if(inFile != null)
				{
					currInputFile = inFile;
					inputFilename.setText(currInputFile.getAbsolutePath());
					loadMedia(currInputFile);
				}
			}
		});

		/* Load output directory when output browse button selected */
		outputBrowseButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e)
			{
				File inFile = ma.openDirectory("Open Output Directory");

				if(inFile != null)
				{
					currOutputDirectory = inFile;
					outputDirectory.setText(currOutputDirectory.getAbsolutePath());
				}
			}
		});

		/* Multimedia button to play media view */
		playButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e)
			{
				playMedia();
			}
		});
		/* Multimedia button to pause media view */
		pauseButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e)
			{
				pauseMedia();
			}
		});
		/* Convert button to perform conversion */
		convertButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e)
			{
				/* Do things */
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
