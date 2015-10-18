package aes.test;

import aes.boundary.Converter;
import aes.boundary.ConvertParamsException;
import aes.boundary.ConversionInProcessException;

import java.io.IOException;



public class TestConverter
{
	public static void main(String [] args)
	{
		Converter converter;

		try
		{
			converter = new Converter();
			converter.addInputFile("t0.mp4");
			converter.setOutputFile("out_file.avi");

			converter.setPosition("t0.mp4", 0, 0, 20);
			converter.setDuration(300);

			converter.setFrameRate(24);
			converter.setFrameSize(640, 480);
			converter.setVideoEncoder("h264");
			converter.setBitRate("64k");
			converter.setFormat("avi");

			converter.convert();
		}
		catch(ConvertParamsException cpe)
		{
			System.out.println("Bad conversion params");
		}
		catch(IOException ioe)
		{
			System.out.println("No ffmpeg");
		}
		catch(ConversionInProcessException cip)
		{
			System.out.println("Conversion already happening");
		}
	}
}
