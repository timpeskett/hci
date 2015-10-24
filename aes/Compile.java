package aes;

import aes.boundary.ConvertListener;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.LinkedList;
import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.collections.ObservableList;

public class Compile implements SceneController, ConvertListener
{
	private MainApp ma;
	private LinkedList<File> inputFiles;
	private ObservableList<Text> inputFileTexts;
	private File currOutputDirectory, currOutputBasename;

	/* Navigation controls */
	@FXML private Button backButton;

	/* Input related controls */
	@FXML private ListView<Text> inputListView;
	@FXML private Button inputBrowseButton;
	@FXML private Button inputRemoveButton;
	@FXML private ChoiceBox inputFramerateChoiceBox;

	/* Output related controls */
	@FXML private TextField outputDirectory, outputBasename;
	@FXML private Button outputBrowseButton;
	@FXML private ChoiceBox outputFramerateChoiceBox, filetypeChoiceBox;

	/* Conversion related controls */
	@FXML private Text convertPercentage;
	@FXML private ProgressBar progressBar;
	@FXML private Button compileButton;


	@Override
	public void initialize(URL fxmlLocation, ResourceBundle res)
	{
		/* Initialise listview */
		inputFiles = new LinkedList<File>();

		/* Navigate back to intro when back pressed */
		backButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e)
			{
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
					Text fileText = new Text(inFile.getAbsolutePath());
					inputListView.getItems().add(fileText);
					inputFiles.add(inFile);
				}
			}
		});

		/* Remove items from listview */
		inputRemoveButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e)
			{
				for(Integer index : inputListView.getSelectionModel().getSelectedIndices())
				{
					inputListView.getItems().remove(index.intValue());
					inputFiles.remove(index.intValue());
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

		/* Compile button to perform conversion */
		compileButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				compile();
			}
		});
		

	}


	private void compile()
	{
		File outFile = new File(currOutputDirectory, outputBasename.getText());
		String fileType = (String)filetypeChoiceBox.getValue();
		String outFramerate = (String)outputFramerateChoiceBox.getValue();
		boolean inputsValid = true;

		for(File inFile : inputFiles)
		{
			if(!inFile.exists())
			{
				inputsValid = false;
				ma.alertUser("File: " + inFile.getAbsolutePath() + " does not exist.");
			}
		}
		
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
		else if(inputFiles.size() == 0)
		{
			ma.alertUser("No input files specified!");
		}
		else if(!inputsValid)
		{
			/* Error message already given */
		}
		else
		{
			ConvertOptions co;

			co = new ConvertOptions(outFile, inputFiles.toArray(new File[0]));
			co.setFileType(fileType);
			if(outFramerate != null && !outFramerate.equals("Default"))
			{
				co.setOutFramerate(Integer.valueOf(outFramerate));
			}

			compileButton.setText("Cancel");
			backButton.setDisable(true);
			try
			{
				ma.concatFiles(co, this);
			}
			catch(IOException e)
			{
				ma.alertUser("A recent version of FFmpeg was not found on your system");
				reset();
			}
		}
	}


	@Override
	public void onFinish(int exitValue)
	{
		if(exitValue == 0)
		{
			ma.tellUser("Compilation complete!");
		}
		else
		{
			ma.alertUser("Compilation could not be completed!");
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
				backButton.setDisable(false);
				compileButton.setText("Compile");
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
