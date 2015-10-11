/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard_interface.model;

import java.io.File;
import java.util.List;

/**
 *
 * @author Chris
 */
public class VideoProject extends AbstractProject {

    private double[] inputFilesframerate;

    // output file fields
    private double outputFileFramerate;
    private String codec;
    private String quality;
    private String aspectRatio;
    private String frameSize;


    
    public VideoProject(List<File> inInputFiles, File inOutputLocation, String inOutputFileName, String inOutputFileType) {
        super(inInputFiles, inOutputLocation, inOutputFileName, inOutputFileType);
    }

    public VideoProject() {
        super();
    }

    // generic setters and getters below
    public double[] getInputFilesframerate() {
        return inputFilesframerate;
    }

    public void setInputFilesframerate(double[] inputFilesframerate) {
        this.inputFilesframerate = inputFilesframerate;
    }

    public double getOutputFileFramerate() {
        return outputFileFramerate;
    }

    public void setOutputFileFramerate(double outputFileFramerate) {
        this.outputFileFramerate = outputFileFramerate;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getFrameSize() {
        return frameSize;
    }

    public void setFrameSize(String frameSize) {
        this.frameSize = frameSize;
    }

}
