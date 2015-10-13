package aes;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class Audio implements SceneController
{
	private MainApp ma;

	@FXML
	private Button backButton;

	@Override
	public void initialize(URL fxmlLocation, ResourceBundle res)
	{
		backButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e)
			{
				ma.gotoIntro();
			}
		});
	}

	@Override
	public void setApp(MainApp ma)
	{
		this.ma = ma;
	}
}
