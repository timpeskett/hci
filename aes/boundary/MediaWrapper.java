package aes.boundary;

import java.io.File;
import java.lang.Integer;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaMarkerEvent;

import javafx.event.EventHandler;

import javafx.collections.ObservableMap;
import javafx.util.Duration;


public class MediaWrapper
{
	private File file;
	private MediaPlayer player;

	public MediaWrapper(File file)
	{
		this.file = file;
		Media mediaFile = new Media(file.toURI().toString());
		player = new MediaPlayer(mediaFile);

		player.setOnReady(new Runnable(){
			@Override
			public void run()
			{
				onReady();
			}
		});
	}


	public void setTimeUpdate(EventHandler<MediaMarkerEvent> handler)
	{
		player.setOnMarker(handler);
	}


	public void setMediaView(MediaView mv)
	{
		mv.setMediaPlayer(player);
	}


	public void play()
	{
		player.play();
	}


	public void pause()
	{
		player.pause();
	}


	public void stop()
	{
		player.stop();
	}

	private void onReady()
	{
		Media mediaFile = player.getMedia();

		/* Load markers into the file for each second */
		ObservableMap<String, Duration> markers = mediaFile.getMarkers();
		System.out.println("Length: " + mediaFile.getDuration().toSeconds());
		for(int i = 0; i < mediaFile.getDuration().toSeconds(); i++)
		{
			markers.put(new Integer(i).toString(), new Duration(i * 1000));
		}
	}


	private void playMedia()
	{
		player.play();
	}

	private void pauseMedia()
	{
		player.pause();
	}
}


