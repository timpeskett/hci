
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
	private HBox videoBannerHbox;
	@FXML
	private HBox audioBannerHbox;

	@Override
	public void initialize(URL fxmlLocation, ResourceBundle res)
	{
		videoBannerHbox.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e)
			{
				ma.gotoVideo();
			}
		});

		audioBannerHbox.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e)
			{
				ma.gotoAudio();
			}
		});
	}

	@Override
	public void setApp(MainApp ma)
	{
		this.ma = ma;
	}
}
