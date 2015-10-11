
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;

import javafx.event.EventHandler;


public class Intro implements SceneController
{
	private MainApp ma;

	@FXML
	private HBox vidOption;

	@Override
	public void initialize(URL fxmlLocation, ResourceBundle res)
	{
		vidOption.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e)
			{
				ma.gotoVideo();
			}
		});
	}

	@Override
	public void setApp(MainApp ma)
	{
		this.ma = ma;
	}
}
