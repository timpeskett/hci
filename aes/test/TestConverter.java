package aes.test;

import aes.boundary.Converter;
import aes.boundary.ConvertParamsException;

import java.io.IOException;



public class TestConverter
{
	public static void main(String [] args)
	{
		Converter converter;

		try
		{
			converter = new Converter();
			converter.addInputFile("test_file.mp4");
			converter.setOutputFile("test_file_new.avi");

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
	}
}
