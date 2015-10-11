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

    // output file fields
    private String codec;
    private int channels;
    private double frequency;
    private int bitrate;

    public AudioProject(List<File>inInputFiles, File inOutputLocation, String inOutputFileName, String inOutputFileType) {
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

    public int getChannels() {
        return channels;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

}
