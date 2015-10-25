/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Chris
 */
public class AudioProject extends AbstractProject {

    // String currProfile, String currFormat, String currCodec, String currChannels, String currBitrate, String currSampleRate) {
    private StringProperty codec;
    private StringProperty bitrate;
    private StringProperty channels;
    private StringProperty sampleRate;

    public AudioProject() {
        super();
        codec = new SimpleStringProperty();
        bitrate = new SimpleStringProperty();
        channels = new SimpleStringProperty();
        sampleRate = new SimpleStringProperty();
    }

    // generic setters and getters below
    public StringProperty getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec.set(codec);
    }

    public StringProperty getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels.set(channels);
    }

    public StringProperty getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate.set(bitrate);
    }

    public void setSampleRate(String inSampleRate) {
        this.sampleRate.set(inSampleRate);
    }

    public StringProperty getSampleRate() {
        return sampleRate;
    }

}
