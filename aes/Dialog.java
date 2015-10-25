package aes;

/* A dialog box to show information and errors to the user */

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Modality;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;


public class Dialog extends Stage
{
	String message;
	Stage owner, dialog;
	Button okButton;
	Text messageText;

	public Dialog(String message, Stage owner)
	{
		super();

		this.message = message;
		this.owner = owner;

		okButton = new Button("Ok");
		messageText = new Text(message);

		VBox container = new VBox(10);
		container.getChildren().addAll(messageText, okButton);

		Scene scene = new Scene(container);

		initStyle(StageStyle.UTILITY);
		initModality(Modality.WINDOW_MODAL);
		initOwner(owner);
		setScene(scene);

		okButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e)
			{
				Dialog.this.close();
			}
		});
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

