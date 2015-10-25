package aes;

import aes.boundary.Converter;
import aes.boundary.ConvertListener;
import aes.boundary.ConvertParamsException;
import aes.boundary.ConversionInProcessException;

import javafx.application.Platform;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.Parent;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;

import javafx.fxml.FXMLLoader;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.HashMap;
import java.util.Set;
import java.io.IOException;
import java.io.File;
import java.net.URL;


public class MainApp extends Application
{
	private Stage mainStage;
	private Logger logger;
	private Converter converter;
	private String themeName;
	private HashMap<String, String> themes;

	@Override
	public void start(Stage stage)
	{
		/* Create a logger to log info/warn/error messages */
		logger = Logger.getLogger(MainApp.class.getName());

		/* Create an association between theme names and
		 * themes */
		themes = new HashMap<String, String>();
		themes.put("Dark", "/aes/res/css/theme.css");

		themeName = "Dark";

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


	public void convertVideo(ConvertOptions co, ConvertListener cl) throws IOException
	{
		try
		{
			converter = new Converter();

			converter.setConvertListener(cl);

			File inFile = co.getInFile(0);
			File outFile = co.getOutFile();
			String inFilePath = inFile.getAbsolutePath();

			converter.addInputFile(inFilePath);
			converter.setOutputFile(outFile.getAbsolutePath());

			if(co.hasOutFramerate())
			{
				converter.setFrameRate(co.getOutFramerate().intValue());
			}

			if(co.hasFileType())
			{
				converter.setFormat(co.getFileType());
			}

			converter.convert();
		}
		/* We can safely catch these exceptions because
		 * the data is validated
		 */
		catch(ConvertParamsException cpe)
		{
		}
		catch(ConversionInProcessException cipe)
		{
		}
	}


	public void convertAudio(ConvertOptions co, ConvertListener cl)throws IOException
	{
		try
		{
			converter = new Converter();

			converter.setConvertListener(cl);

			File inFile = co.getInFile(0);
			File outFile = co.getOutFile();
			String inFilePath = inFile.getAbsolutePath();

			converter.addInputFile(inFilePath);
			converter.setOutputFile(outFile.getAbsolutePath());

			if(co.hasFileType())
			{
				converter.setFormat(co.getFileType());
			}

			converter.convert();
		}
		catch(ConvertParamsException cpe)
		{
		}
		catch(ConversionInProcessException cipe)
		{
		}
	}


	public void concatFiles(ConvertOptions co, ConvertListener cl) throws IOException
	{
		try
		{
			converter = new Converter();

			converter.setConvertListener(cl);

			for(File inFile : co.getInFiles())
			{
				String inFilePath = inFile.getAbsolutePath();
				converter.addInputFile(inFilePath);
			}

			File outFile = co.getOutFile();
			converter.setOutputFile(outFile.getAbsolutePath());

			if(co.hasOutFramerate())
			{
				converter.setFrameRate(co.getOutFramerate().intValue());
			}

			if(co.hasFileType())
			{
				converter.setFormat(co.getFileType());
			}

			converter.compile();
		}
		/* We can safely catch these exceptions because
		 * the data is validated
		 */
		catch(ConvertParamsException cpe)
		{
		}
		catch(ConversionInProcessException cipe)
		{
		}
	}


	public void cancelConversion()
	{
		if(converter != null)
		{
			converter.cancel();
			converter = null;
		}
	}

	public File openFile(String title)
	{
		FileChooser fc = new FileChooser();
		fc.setTitle(title);
		return fc.showOpenDialog(mainStage);
	}

	public File openDirectory(String title)
	{
		DirectoryChooser dc = new DirectoryChooser();
		dc.setTitle(title);
		return dc.showDialog(mainStage);
	}

	public File saveFile(String title)
	{
		FileChooser fc = new FileChooser();
		fc.setTitle(title);
		return fc.showSaveDialog(mainStage);
	}

	public String getCurrentTheme()
	{
		return themeName;
	}

	public void setTheme(String themeName)
	{
		if(themes.containsKey(themeName) && themeName != this.themeName)
		{
			Scene root = mainStage.getScene();
			root.getStylesheets().clear();
			root.getStylesheets().add(themes.get(themeName));
			this.themeName = themeName;
		}
	}

	public Set<String> getThemes()
	{
		return themes.keySet();
	}

	public void tellUser(String message)
	{
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				try
				{
					Dialog alertDialog = new Dialog(message, mainStage);
					alertDialog.inform();
				}
				catch(IOException e)
				{
					logger.log(Level.WARNING, "Could not create dialog box");
				}
			}
		});
	}

	public void alertUser(String message)
	{
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				try
				{
					Dialog alertDialog = new Dialog(message, mainStage);
					alertDialog.inform();
				}
				catch(IOException e)
				{
					logger.log(Level.WARNING, "Could not create dialog box");
				}
			}
		});

	}
	
	public void gotoIntro()
	{
		gotoPage("/aes/res/fxml/intro.fxml");
	}


	public void gotoVideo()
	{
		gotoPage("/aes/res/fxml/video.fxml");
	}


	public void gotoAudio()
	{
		gotoPage("/aes/res/fxml/audio.fxml");
	}

	
	public void gotoCompile()
	{
		gotoPage("/aes/res/fxml/compile.fxml");
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
			loader.setLocation(MainApp.class.getResource(fxmlFile));
			Parent root = loader.load();

			/* Set the current theme */
			Scene newScene = new Scene(root);
			newScene.getStylesheets().add(themes.get(themeName));

			/* Change the current display to the loaded fxml */
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
