package aes;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.LinkedList;
import java.io.File;

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

public class Compile implements SceneController
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

		/* Convert button to perform conversion */
		compileButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e)
			{
				/* Do things */
			}
		});
	}


	@Override
	public void setApp(MainApp ma)
	{
		this.ma = ma;
	}
}
