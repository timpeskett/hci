import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser;
import javafx.fxml.FXMLLoader;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.IOException;
import java.io.File;
import java.net.URL;


public class MainApp extends Application
{
	private Stage mainStage;
	private Logger logger;

	@Override
	public void start(Stage stage)
	{
		/* Create a logger to log info/warn/error messages */
		logger = Logger.getLogger(MainApp.class.getName());

		/* Save reference to main stage */
		mainStage = stage;
		mainStage.initStyle(StageStyle.UNDECORATED);
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


	public File openFile(String title)
	{
		FileChooser fc = new FileChooser();
		fc.setTitle(title);
		return fc.showOpenDialog(mainStage);
	}

	public File saveFile(String title)
	{
		FileChooser fc = new FileChooser();
		fc.setTitle(title);
		return fc.showSaveDialog(mainStage);
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
		if(cont != null)
		{
			cont.setApp(this);
		}
	}


	private SceneController replaceScene(String fxmlFile)
	{
		SceneController controller = null;

		try
		{
			/* Load the fxml from the file */
			FXMLLoader loader = new FXMLLoader();
			URL fileLocation = new File(fxmlFile).toURI().toURL();

			loader.setLocation(fileLocation);
			Parent root = loader.load();

			/* Change the current display to the loaded fxml */
			Scene newScene = new Scene(root);

			mainStage.setScene(newScene);
			mainStage.show();

			controller = loader.getController();
		}
		catch(IOException e)
		{
			logger.log(Level.SEVERE, "Could not load FXML file: " + fxmlFile + "\n" + e.getMessage());
			e.printStackTrace();
			mainStage.close();
		}

		return controller;
	}

}
