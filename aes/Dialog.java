package aes;

/* A dialog box to show information and errors to the user */

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Modality;

import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;

import java.io.IOException;


public class Dialog extends Stage
{
	String message;
	Stage owner, dialog;

	public Dialog(String message, Stage owner) throws IOException
	{
		super();

		this.message = message;
		this.owner = owner;

		Parent root = FXMLLoader.load(MainApp.class.getResource("/aes/res/fxml/dialog.fxml"));
		Button okButton = (Button)root.lookup("#ok-button");
		Text messageText = (Text)root.lookup("#dialog-text");

		messageText.setText(message);

		okButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e)
			{
				Dialog.this.close();
			}
		});

		Scene scene = new Scene(root);

		initStyle(StageStyle.UTILITY);
		initModality(Modality.WINDOW_MODAL);
		initOwner(owner);
		setScene(scene);

	}


	public void alert()
	{
		setTitle("Warning!");
		show();
	}


	public void inform()
	{
		setTitle("Information");
		show();
	}
}

