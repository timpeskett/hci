/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Converter {

    private Map<String, FileParams> inputs;
    private FileParams output;

    private Process ffmpeg;
    private ProcessBuilder builder;
    //private ConvertListener listener;

    /* ffmpeg version string */
    private String version;

    /* An abstract class seems like a natural choice here, but it's
     * a little heavy for a private inner class that would only ever
     * have two subclasses.
     */
    private class FileParams {

        public boolean isInput;
        public String fileName;
        public Map<String, String> options;

        public FileParams(String fileName, boolean isInput) {
            this.fileName = fileName;
            this.isInput = isInput;

            options = new HashMap<String, String>();
        }

        public void setPosition(int hours, int minutes, int seconds) {
            options.put("-ss", String.format("%02d:%02d:%02d", hours, minutes, seconds));
        }

        public void setDuration(int seconds) {
            options.put("-t", String.valueOf(seconds));
        }

        public void setVideoEncoder(String encoder) {
            options.put("-c:v", encoder);
        }

        public void setAudioEncoder(String encoder) {
            options.put("-c:a", encoder);
        }

        public void setAudioChannels(String channels) {
            options.put("-ac", channels);
        }

        public void setFrameSize(int width, int height) {
            options.put("-s", String.format("%dx%d", width, height));
        }

        public void setFrameRate(int frameRate) {
            options.put("-r", String.valueOf(frameRate));
        }

        public void setVideoBitRate(String bitRate) {
            options.put("-b:v", bitRate);
        }

        public void setFormat(String format) {
            options.put("-f", format);
        }

        public void setAudioSamplerate(String samplerate) {
            options.put("-ar", samplerate);
        }

        public void setAudioBitRate(String bitrate) {
            options.put("-b:a", bitrate);
        }

        public List<String> synthesize() {
            List<String> args = new LinkedList<String>();
            String outString = "";

            for (Map.Entry<String, String> option : options.entrySet()) {
                args.add(option.getKey());
                args.add(option.getValue());
            }

            if (isInput) {
                args.add("-i");
            }

            args.add(fileName);
            return args;
        }

    }


    /* Throws IOException if no ffmpeg found on system */
    public Converter() throws IOException {
        inputs = new HashMap<String, FileParams>();

        version = getFFmpegVersion();
    }

    public synchronized void convert() throws IOException, ConversionInProcessException {
        List<String> command = makeCommand();
        String c = "";

        /* Check not already converting */
        if (ffmpeg != null) {
            throw new ConversionInProcessException("One converter cannot perform two simultaneous conversions.");
        }

        /* Build process and execute thread */
        builder = new ProcessBuilder(command);
        ffmpeg = builder.start();

        /* Start thread to inform when exited 
         new Thread(new Runnable(){
         @Override
         public void run(){
         try
         {
         ffmpeg.waitFor();
         if(listener != null)
         {
         listener.onFinish(ffmpeg.exitValue());
         }
         }
         catch(Exception e)
         {
         /* Do nothing 
         }
         }
         }).start();*/
        for (String part : command) {
            c += part + " ";
        }

        System.out.println("Command line: " + c);
    }


    /*
     public void cancel()
     {

     }


     public void onProgressUpdate()
     {

     }
     */

    /*public void setConvertListener(ConvertListener listener)
     {
     this.listener = listener;
     }*/
    public void addInputFile(String inputFile) {
        inputs.put(inputFile, new FileParams(inputFile, true));
    }

    public void removeInputFile(String inputFile) {
        inputs.remove(inputFile);
    }

    public void setOutputFile(String outputPath) {
        output = new FileParams(outputPath, false);
    }

    public String getVersion() {
        return version;
    }

    private String getFFmpegVersion() throws IOException {
        final String versionRegex = "ffmpeg version (\\d+\\.\\d+\\.\\d+).*";
        Process proc;
        String outLine;

        try {
            proc = new ProcessBuilder("ffmpeg", "-version").start();
            /* Usually we wouldn't wait but this command should be almost instantaneous */
            proc.waitFor();

            /* Get process output */
            outLine = new BufferedReader(
                    new InputStreamReader(
                            proc.getInputStream())).readLine();
        } catch (InterruptedException ie) {
            outLine = "Unknown";
        }

        return outLine;
    }

    private List<String> makeCommand() {
        List<String> command = new LinkedList<String>();

        command.add("ffmpeg");

        for (FileParams fileParams : inputs.values()) {
            command.addAll(fileParams.synthesize());
        }

        if (output != null) {
            command.addAll(output.synthesize());
        }

        return command;
    }


    /* Pass throughs to FileParams */
    public void setPosition(String file, int hours, int minutes, int seconds) throws ConvertParamsException {
        FileParams fp = inputs.get(file);
        if (fp == null) {
            throw new ConvertParamsException("Position argument not applicable to any found inputs");
        }

        fp.setPosition(hours, minutes, seconds);
    }

    public void setDuration(int seconds) throws ConvertParamsException {
        if (output == null) {
            throw new ConvertParamsException("Duration argument not applicable to any found outputs");
        }

        output.setDuration(seconds);
    }

    public void setVideoEncoder(String encoder) throws ConvertParamsException {
        if (output == null) {
            throw new ConvertParamsException("No output to set video encoder for");
        }

        output.setVideoEncoder(encoder);
    }

    public void setAudioEncoder(String encoder) throws ConvertParamsException {
        if (output == null) {
            throw new ConvertParamsException("No output to set audio encoder for");
        }

        output.setAudioEncoder(encoder);
    }

    public void setAudioChannels(String channels) throws ConvertParamsException {
        if (output == null) {
            throw new ConvertParamsException("bad");
        }

        output.setAudioChannels(channels);

    }

    public void setAudioSamplerate(String samplerate) throws ConvertParamsException {
        if (output == null) {
            throw new ConvertParamsException("bad");
        }

        output.setAudioSamplerate(samplerate);
    }

    public void setFrameSize(int width, int height) throws ConvertParamsException {
        if (output == null) {
            throw new ConvertParamsException("No output to set frame size for");
        }

        output.setFrameSize(width, height);
    }

    public void setFrameRate(int frameRate) throws ConvertParamsException {
        if (output == null) {
            throw new ConvertParamsException("No output to set frame rate for");
        }

        output.setFrameRate(frameRate);
    }

    public void setVideoBitRate(String bitRate) throws ConvertParamsException {
        if (output == null) {
            throw new ConvertParamsException("No output to set bit rate for");
        }

        output.setVideoBitRate(bitRate);
    }
    
        public void setAudioBitRate(String bitRate) throws ConvertParamsException {
        if (output == null) {
            throw new ConvertParamsException("No output to set bit rate for");
        }

        output.setAudioBitRate(bitRate);
    }

    public void setFormat(String format) throws ConvertParamsException {
        if (output == null) {
            throw new ConvertParamsException("No output to set format for");
        }

        output.setFormat(format);
    }
}
