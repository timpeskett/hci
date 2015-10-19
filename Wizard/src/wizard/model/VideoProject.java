/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard.model;

import java.io.File;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Chris
 */
public class VideoProject extends AbstractProject {

    //private DoubleProperty[] inputFilesframerate;
    // output file fields
    //private DoubleProperty outputFileFramerate;
    private StringProperty codec;
    //private StringProperty quality;
    //private StringProperty aspectRatio;
    private StringProperty frameSize;
    private StringProperty frameRate;
    private StringProperty bitrate;
    private StringProperty audioForVideo;

    public VideoProject() {
        super();
        codec = new SimpleStringProperty();
        frameSize = new SimpleStringProperty();
        frameRate = new SimpleStringProperty();
        bitrate = new SimpleStringProperty();
        audioForVideo = new SimpleStringProperty();
    }

    // generic setters and getters below
    /*public DoubleProperty[] getInputFilesframerate() {
     return inputFilesframerate;
     }

     public void setInputFilesframerate(double[] inputFilesframerate) {
     for (int i = 0; i < inputFilesframerate.length; i++) {
     this.inputFilesframerate[i].set(inputFilesframerate[i]);
     }
     }

     public DoubleProperty getOutputFileFramerate() {
     return outputFileFramerate;
     }

     public void setOutputFileFramerate(double outputFileFramerate) {
     this.outputFileFramerate.set(outputFileFramerate);
     }*/
    public StringProperty getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec.set(codec);
    }

    /* public StringProperty getQuality() {
     return quality;
     }

     public void setQuality(String quality) {
     this.quality.set(quality);
     }

     public StringProperty getAspectRatio() {
     return aspectRatio;
     }

     public void setAspectRatio(String aspectRatio) {
     this.aspectRatio.set(aspectRatio);
     }*/
    public StringProperty getFrameSize() {
        return frameSize;
    }

    public void setFrameSize(String frameSize) {
        this.frameSize.set(frameSize);
    }

    public StringProperty getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(String frameRate) {
        this.frameRate.set(frameRate);
    }

    public void setBitrate(String bitrate) {
        this.frameRate.set(bitrate);
    }

    public StringProperty getBitrate() {
        return bitrate;
    }

    public void setAudioForVideo(String audioForVideo) {
        this.audioForVideo.set(audioForVideo);
    }

    public StringProperty getAudioForVideo() {
        return audioForVideo;
    }

}
