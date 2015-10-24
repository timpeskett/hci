package aes;

import aes.boundary.MediaWrapper;
import aes.boundary.ConvertListener;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaMarkerEvent;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.util.Duration;


public class Video implements SceneController, ConvertListener
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
	@FXML private Slider playSlider;


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
				if(convertButton.getText().equals("Cancel"))
				{
					ma.cancelConversion();
					
				}
				else
				{
					convert();
				}
			}
		});

		reset();
	}


	private void loadMedia(File file)
	{
		mediaWrapper = new MediaWrapper(file);
		mediaWrapper.setTimeUpdate(new EventHandler<MediaMarkerEvent>(){
			@Override
			public void handle(MediaMarkerEvent mme) 
			{
				Duration length = mediaWrapper.getDuration();	
				Duration pos = mme.getMarker().getValue();
				double ratio = pos.toSeconds() / (double)length.toSeconds();
				Platform.runLater(new Runnable(){
					@Override
					public void run(){
						playSlider.setValue(ratio * (playSlider.getMax() - playSlider.getMin()) + playSlider.getMin());
					}
				});
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


	private void convert()
	{
		File outFile = new File(currOutputDirectory, outputBasename.getText());
		String fileType = (String)filetypeChoiceBox.getValue();
		String outFramerate = (String)outputFramerateChoiceBox.getValue();
		
		if(outFile.isDirectory())
		{
			ma.alertUser("Output file cannot be a directory.");
		}
		else if(outFile.exists())
		{
			ma.alertUser("Output file already exists.");
		}
		else if(fileType == null)
		{
			ma.alertUser("Must specify filetype.");
		}
		else if(!currInputFile.exists())
		{
			ma.alertUser("Specified input file does not exist.");
		}
		else
		{
			ConvertOptions co;

			co = new ConvertOptions(outFile, currInputFile);
			co.setFileType(fileType);
			if(outFramerate != null && !outFramerate.equals("Default"))
			{
				co.setOutFramerate(Integer.valueOf(outFramerate));
			}

			convertButton.setText("Cancel");
			try
			{
				ma.convertVideo(co, this);
			}
			catch(IOException e)
			{
				ma.alertUser("A recent version of FFmpeg was not found on your system");
				onCancel();
			}
		}
	}


	@Override
	public void onFinish(int exitValue)
	{
		if(exitValue == 0)
		{
			ma.tellUser("Conversion complete!");
		}
		else if(exitValue == 255)
		{
			/* Cancel */
			System.out.println("Skip diddly bop");
		}
		else
		{
			ma.alertUser("Conversion could not be completed!");
		}
		reset();
	}

	@Override
	public void onCancel()
	{
		reset();
	}

	@Override
	public void onProgress(double progress)
	{
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				convertPercentage.setText(String.format("%d%% Complete", (int)(progress*100)));
				progressBar.setProgress(progress);
			}
		});
	}

	private void reset()
	{
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				convertButton.setText("Convert");
				convertPercentage.setText("0% Complete");
				progressBar.setProgress(0.0);
			}
		});
	}

	@Override
	public void setApp(MainApp ma)
	{
		this.ma = ma;
	}
}
