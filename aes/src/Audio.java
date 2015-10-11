
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;

import javafx.event.EventHandler;

public class Audio implements SceneController
{
	private MainApp ma;

	@Override
	public void initialize(URL fxmlLocation, ResourceBundle res)
	{
	}

	@Override
	public void setApp(MainApp ma)
	{
		this.ma = ma;
	}
}
