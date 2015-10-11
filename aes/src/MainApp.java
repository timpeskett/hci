import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.IOException;
import java.io.File;
import java.net.URL;


public class MainApp extends Application
{
	private Stage mainStage;
	private FXMLLoader fxmlLoader;
	private Logger logger;

	@Override
	public void start(Stage stage)
	{
		/* Create FXMLLoader object instead of using static methods */
		fxmlLoader = new FXMLLoader();

		/* Create a logger to log info/warn/error messages */
		logger = Logger.getLogger(MainApp.class.getName());

		/* Save reference to main stage */
		mainStage = stage;
		mainStage.setTitle("FFmpeg Aesthetic Interface");

		gotoIntro();
	}

	public static void main(String [] args)
	{
		launch(args);
	}


	public void convertVideo(/*ConvertOptions co*/)
	{
	}


	public void convertAudio(/*ConvertOptions co*/)
	{
	}

	
	public void gotoIntro()
	{
		gotoPage("../res/fxml/intro.fxml");
	}


	public void gotoVideo()
	{
		gotoPage("../res/fxml/video.fxml");
	}


	public void gotoAudio()
	{
		gotoPage("../res/fxml/audio.fxml");
	}


	private void gotoPage(String page)
	{
		SceneController cont = replaceScene(page);
		cont.setApp(this);
	}


	private SceneController replaceScene(String fxmlFile)
	{
		SceneController controller = null;
		FXMLLoader loader = new FXMLLoader();

		try
		{
			/* Load the fxml from the file */
			URL fileLocation = new File(fxmlFile).toURI().toURL();
			loader.setLocation(fileLocation);
			Parent root = loader.load();

			/* Change the current display to the loaded fxml */
			Scene newScene = new Scene(root);

			mainStage.setScene(newScene);
			mainStage.sizeToScene();
			mainStage.show();

			controller = loader.getController();
		}
		catch(IOException e)
		{
			logger.log(Level.SEVERE, "Could not load FXML file: " + fxmlFile + "\n" + e.getMessage());
			mainStage.close();
		}

		return controller;
	}

}
