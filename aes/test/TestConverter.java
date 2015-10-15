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
			converter.addInputFile("t0.mp4");
			converter.setOutputFile("out_file.avi");

			converter.addInputFile("t1.mp4");
			converter.addInputFile("t2.mp4");

			converter.setPosition("t1.mp4", 0, 0, 20);
			converter.setDuration("t2.mp4", 300);

			converter.setFrameRate(24);
			converter.setFrameSize(640, 480);
			converter.setEncoder("h264");
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
	}
}
