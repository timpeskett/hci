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
public class AudioProject extends AbstractProject {

    // String currProfile, String currFormat, String currCodec, String currChannels, String currBitrate, String currSampleRate) {
    private String codec;
    private String frequency;
    private String bitrate;
    private String channels;
    private String sampleRate;

    public AudioProject(List<File> inInputFiles, File inOutputLocation, String inOutputFileName, String inOutputFileType) {
        super(inInputFiles, inOutputLocation, inOutputFileName, inOutputFileType);
    }

    public AudioProject() {
        super();
    }

    // generic setters and getters below
    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public void setSampleRate(String inSampleRate) {        
        sampleRate = inSampleRate;
        System.out.println("official samplerate has been changed to: " + sampleRate);
    }

    public String getSampleRate() {
        return sampleRate;
    }

}
