package wizard.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

    /* Name: SettingsController
     * Description: Controller class for Settings.fxml
     *              Allows user to modify the state of combo boxes to select
     *              ffmpeg conversion params.
     * @author Chris
     */
public class SettingsController {

    private SimpleIntegerProperty state;
    private CenterPanelController centerPanel;
    private MainController.ProjectType type;

    /* FXML components for injection */
    @FXML
    private Button backBtn;
    @FXML
    private Button nextBtn;

    @FXML
    private ComboBox formatCombo;
    @FXML
    private ComboBox codecCombo;
    @FXML
    private ComboBox setting1Combo;
    @FXML
    private ComboBox setting2Combo;
    @FXML
    private ComboBox setting3Combo;
    @FXML
    private ComboBox setting4Combo;
    @FXML
    private ComboBox profileCombo;

    @FXML
    private Label setting1Txt;
    @FXML
    private Label setting2Txt;
    @FXML
    private Label setting3Txt;
    @FXML
    private Label setting4Txt;
    @FXML
    private Label profileTxt;

    /* Project settings */
    private StringProperty outputType;
    private StringProperty codec;
    private StringProperty bitrate;
    private StringProperty profile;

    /* Audio specific settings */
    private StringProperty channels;
    private StringProperty sampleRate;

    /* Video specific settings */
    private StringProperty audioForVideo;
    private StringProperty frameRate;
    private StringProperty frameSize;

    public void init(SimpleIntegerProperty inState, CenterPanelController inCenterPanel) {
        state = inState;
        centerPanel = inCenterPanel;

        formatCombo.setTooltip(new Tooltip("Select a format to convert to"));
        codecCombo.setTooltip(new Tooltip("Select a codec"));
        profileCombo.setTooltip(new Tooltip("Select a profile with suitable params"));
        backBtn.setTooltip(new Tooltip("Return to file settings"));
        nextBtn.setTooltip(new Tooltip("Continue to finalise options"));
    }

    public void backBtnPressed() {
        state.set(state.subtract(1).get());
    }

    public void nextBtnPressed() {
        state.set(state.add(1).get());
    }

    public void setProjectType(MainController.ProjectType inType) {
        type = inType;
        if (type == MainController.ProjectType.AUDIO_PROJECT) {
            setupAudioSettings();
        } else if (type == MainController.ProjectType.VIDEO_PROJECT) {
            setupVideoSettings();
        }
    }

    public void formatComboChange() {
        /* Add to back of EDT queue to avoid threading issues */
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (type == MainController.ProjectType.AUDIO_PROJECT) {
                    outputType.set(formatCombo.getValue().toString());
                    String currFormat = outputType.get();

                    codecCombo.getItems().clear();
                    switch (currFormat) {
                        case "Ogg":
                            codecCombo.setValue("Ogg Vorbis");
                            codecCombo.setDisable(true);
                            break;
                        case "MP3":
                            codecCombo.setValue("MP3");
                            codecCombo.setDisable(true);
                            break;
                        case "FLAC":
                            codecCombo.setValue("FLAC");
                            codecCombo.setDisable(true);
                            break;
                        case "WAV":
                            codecCombo.getItems().clear();
                            codecCombo.getItems().addAll("PCM", "MP3");
                            if (codec.get().contains("PCM")) {
                                codecCombo.setValue("PCM");
                            } else {
                                codecCombo.setValue("MP3");
                            }
                            codecCombo.setDisable(false);

                            break;
                        case "WMA":
                            codecCombo.getItems().clear();
                            codecCombo.getItems().addAll("PCM", "WMA");
                            if (codec.get().contains("PCM")) {
                                codecCombo.setValue("PCM");
                            } else {
                                codecCombo.setValue("WMA");
                            }
                            codecCombo.setDisable(false);
                    }
                } else {
                    // video here
                    outputType.set(formatCombo.getValue().toString());
                }

            }
        });
    }

    public void codecComboChange() {
        /* Add to back of EDT queue to avoid threading issues */
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                codec.set(codecCombo.getValue().toString());
            }
        });
    }

    public void setting1Change() {
        /* Add to back of EDT queue to avoid threading issues */
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (type == MainController.ProjectType.AUDIO_PROJECT) {
                    bitrate.set(setting1Combo.getValue().toString());
                } else {
                    // video here
                    frameSize.set(setting1Combo.getValue().toString());
                }
            }
        });
    }

    public void setting2Change() {
        /* Add to back of EDT queue to avoid threading issues */
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (type == MainController.ProjectType.AUDIO_PROJECT) {
                    channels.set(setting2Combo.getValue().toString());
                } else {
                    // video here
                    frameRate.set(setting2Combo.getValue().toString());
                }
            }
        });
    }

    public void setting3Change() {
        /* Add to back of EDT queue to avoid threading issues */
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (type == MainController.ProjectType.AUDIO_PROJECT) {
                    sampleRate.set(setting3Combo.getValue().toString());
                } else {
                    // video here
                    bitrate.set(setting3Combo.getValue().toString());
                }
            }
        });
    }

    public void setting4Change() {
        /* Add to back of EDT queue to avoid threading issues */
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (type == MainController.ProjectType.AUDIO_PROJECT) {
                    // audio here
                } else {
                    // video here
                    audioForVideo.set(setting4Combo.getValue().toString());
                }
            }
        });
    }

    public void profileComboChange() {
        /* Add to back of EDT queue to avoid threading issues */
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                profile.set(profileCombo.getValue().toString());
                if (type == MainController.ProjectType.AUDIO_PROJECT) {
                    if (profile.get().equals("Low Quality")) {
                        setting1Combo.setValue("64 kb/s");
                        setting2Combo.setValue("Mono");
                        setting3Combo.setValue("22050 Hz");
                    } else if (profile.get().equals("Medium Quality")) {
                        setting1Combo.setValue("128 kb/s");
                        setting2Combo.setValue("Stereo");
                        setting3Combo.setValue("44100 Hz");
                    } else if (profile.get().equals("High Quality")) {
                        setting1Combo.setValue("320 kb/s");
                        setting2Combo.setValue("Stereo");
                        setting3Combo.setValue("44100 Hz");
                    }
                } else {
                    /* 
                     format
                     codec
                     setting 1: frame size: 426x240, 640x360, 854x480, 1280x720, 1920x1080, 2560x1440, 3840x2160
                     setting 2: frame rate: 24 25 30 48 50 60
                     setting 3: bitrate: 560 768 800 1200 1500 4200 4500 5120 8192 16384    
                     setting 4: Audio: (codec/bitrate)
                    
                     formatCombo.getItems().addAll("AVI", "Ogg", "ASF", "MOV", "M4V", "WMV", "FLV", "F4V", "MPEG", "MKV", "MP4");
                     codecCombo.getItems().addAll("H.264/AVC", "MPEG-4", "MPEG-2", "XviD/DivX");
                     profileCombo.getItems().addAll("HD Video 1080p", "HD Video 720p", "BlackBerry 95xx", "BlackBerry 81xx", "Apple iPhone 5/5C/5S/6", "Apple iPhone 4/4S/5", "Samsung Galaxy S4/S5", "Samsung Galaxy S3/S4");
                     setting1Combo.getItems().addAll("426x240", "640x360", "854x480", "1280x720", "1920x1080", "2560x1440", "3840x2160");
                     setting2Combo.getItems().addAll("24 fps", "25 fps", "30 fps", "48 fps", "50 fps", "60 fps");
                     setting3Combo.getItems().addAll("560 kb/s", "768 kb/s", "800 kb/s", "1200 kb/s", "1500 kb/s", "3200 kb/s", "2100", "4200 kb/s", "4500 kb/s", "5120 kb/s", "8192 kb/s", "16384 kb/s");
                     setting4Combo.getItems().addAll("MP3, 92 kb/s", "MP3, 192 kb/s", "MP3, 256 kb/s", "MP3, 320 kb/s");
                     */
                    if (profile.get().equals("HD Video 1080p")) {
                        formatCombo.setValue("AVI");
                        codecCombo.setValue("XviD/DivX");
                        setting1Combo.setValue("1920x1080");
                        setting2Combo.setValue("30 fps");
                        setting3Combo.setValue("4200 kb/s");
                        setting4Combo.setValue("MP3, 256 kb/s");
                    } else if (profile.get().equals("HD Video 720p")) {
                        formatCombo.setValue("AVI");
                        codecCombo.setValue("XviD/DivX");
                        setting1Combo.setValue("1280x720");
                        setting2Combo.setValue("30 fps");
                        setting3Combo.setValue("4500 kb/s");
                        setting4Combo.setValue("MP3, 192 kb/s");
                    } else if (profile.get().equals("BlackBerry 95xx")) {
                        formatCombo.setValue("AVI");
                        codecCombo.setValue("XviD/DivX");
                        setting1Combo.setValue("480x360");
                        setting2Combo.setValue("30 fps");
                        setting3Combo.setValue("800 kb/s");
                        setting4Combo.setValue("MP3, 92 kb/s");
                    } else if (profile.get().equals("BlackBerry 81xx")) {
                        formatCombo.setValue("AVI");
                        codecCombo.setValue("MPEG-4");
                        setting1Combo.setValue("320x240");
                        setting2Combo.setValue("30 fps");
                        setting3Combo.setValue("560 kb/s");
                        setting4Combo.setValue("MP3, 92 kb/s");
                    } else if (profile.get().equals("Apple iPhone 5/5C/5S/6")) {
                        formatCombo.setValue("M4V");
                        codecCombo.setValue("H.264/AVC");
                        setting1Combo.setValue("1920x1080");
                        setting2Combo.setValue("30 fps");
                        setting3Combo.setValue("2100 kb/s");
                        setting4Combo.setValue("MP3, 256 kb/s");
                    } else if (profile.get().equals("Apple iPhone 4/4S/5")) {
                        formatCombo.setValue("M4V");
                        codecCombo.setValue("H.264/AVC");
                        setting1Combo.setValue("960x640");
                        setting2Combo.setValue("30 fps");
                        setting3Combo.setValue("1500 kb/s");
                        setting4Combo.setValue("MP3, 192 kb/s");
                    } else if (profile.get().equals("Samsung Galaxy S4/S5")) {
                        formatCombo.setValue("MP4");
                        codecCombo.setValue("H.264/AVC");
                        setting1Combo.setValue("1920x1080");
                        setting2Combo.setValue("30 fps");
                        setting3Combo.setValue("4500 kb/s");
                        setting4Combo.setValue("MP3, 256 kb/s");
                    } else if (profile.get().equals("Samsung Galaxy S3/S4")) {
                        formatCombo.setValue("MP4");
                        codecCombo.setValue("H.264/AVC");
                        setting1Combo.setValue("1280x720");
                        setting2Combo.setValue("30 fps");
                        setting3Combo.setValue("3200 kb/s");
                        setting4Combo.setValue("MP3, 192 kb/s");
                    }
                }
            }
        });
    }

    private void setupAudioSettings() {
        outputType = centerPanel.getOutputFileType();
        profile = centerPanel.getProfile();
        codec = centerPanel.getCodec();
        bitrate = centerPanel.getBitrate();
        channels = centerPanel.getChannel();
        sampleRate = centerPanel.getSamplerate();

        formatCombo.getItems().clear();
        codecCombo.getItems().clear();
        profileCombo.getItems().clear();
        setting1Combo.getItems().clear();
        setting2Combo.getItems().clear();
        setting3Combo.getItems().clear();
        setting4Combo.getItems().clear();

        formatCombo.getItems().addAll("MP3", "Ogg", "WAV", "FLAC", "WMA");

        /* Set default values if nothing is set already */
        if (outputType.get() == null) {
            formatCombo.setValue("MP3");
            outputType.set("MP3");
        } else {
            formatCombo.setValue(outputType.get());
        }
        if (codec.get() == null) {
            codecCombo.setValue("MP3");
            codec.set("MP3");
            codecCombo.setDisable(true);
        } else {
            codecCombo.setValue(codec.get());
        }

        if (profile.get() == null) {
            profileCombo.setValue("High Quality");
            profile.set("High Quality");
        } else {
            profileCombo.setValue(profile.get());
        }

        if (bitrate.get() == null) {
            setting1Combo.setValue("320 kb/s");
            bitrate.set("320 kb/s");
        } else {
            setting1Combo.setValue(bitrate.get());
        }

        if (channels.get() == null) {
            setting2Combo.setValue("Stereo");
            channels.set("Stereo");
        } else {
            setting2Combo.setValue(channels.get());
        }

        if (sampleRate.get() == null) {
            setting3Combo.setValue("44100 Hz");
            sampleRate.set("44100 Hz");
        } else {
            setting3Combo.setValue(sampleRate.get());
        }

        /* Setup discriptions */
        setting1Txt.setText("Bitrate            ");
        setting2Txt.setText("Channels     ");
        setting3Txt.setText("Sample Rate  ");
        setting4Txt.setVisible(false);

        /* Setup combo boxes */
        System.out.println("populating combo boxes");
        profileCombo.getItems().addAll("High Quality", "Medium Quality", "Low Quality");
        setting1Combo.getItems().addAll("64 kb/s", "80 kb/s", "96 kb/s", "128 kb/s", "160 kb/s", "192 kb/s", "320 kb/s");
        setting2Combo.getItems().addAll("Mono", "Stereo");
        setting3Combo.getItems().addAll("22050 Hz", "32000 Hz", "32000 Hz", "44100 Hz", "48000 Hz");
        setting4Combo.setVisible(false);

    }

    private void setupVideoSettings() {
        outputType = centerPanel.getOutputFileType();
        profile = centerPanel.getProfile();
        codec = centerPanel.getCodec();
        bitrate = centerPanel.getBitrate();
        frameRate = centerPanel.getFrameRate();
        frameSize = centerPanel.getFrameSize();
        audioForVideo = centerPanel.getAudioForVideo();

        formatCombo.getItems().clear();
        codecCombo.getItems().clear();
        profileCombo.getItems().clear();
        setting1Combo.getItems().clear();
        setting2Combo.getItems().clear();
        setting3Combo.getItems().clear();
        setting4Combo.getItems().clear();

        /* 
         format
         codec
         setting 1: frame size: 426x240, 640x360, 854x480, 1280x720, 1920x1080, 2560x1440, 3840x2160
         setting 2: frame rate: 24 25 30 48 50 60
         setting 3: bitrate: 560 768 800 1200 1500 4200 4500 5120 8192 16384    
         setting 4: Audio: (codec/bitrate)
         */
        /* Setup discriptions */
        setting1Txt.setText("Framesize      ");
        setting2Txt.setText("Framerate   ");
        setting3Txt.setText("Bitrate           ");
        setting4Txt.setText("Audio          ");

        setting4Combo.setVisible(true);
        formatCombo.getItems().addAll("AVI", "Ogg", "ASF", "MOV", "M4V", "WMV", "FLV", "F4V", "MPEG", "MKV", "MP4");
        codecCombo.getItems().addAll("H.264/AVC", "MPEG-4", "MPEG-2", "XviD/DivX");
        profileCombo.getItems().addAll("HD Video 1080p", "HD Video 720p", "BlackBerry 95xx", "BlackBerry 81xx", "Apple iPhone 5/5C/5S/6", "Apple iPhone 4/4S/5", "Samsung Galaxy S4/S5", "Samsung Galaxy S3/S4");
        setting1Combo.getItems().addAll("426x240", "640x360", "854x480", "1280x720", "1920x1080", "2560x1440", "3840x2160");
        setting2Combo.getItems().addAll("24 fps", "25 fps", "30 fps", "48 fps", "50 fps", "60 fps");
        setting3Combo.getItems().addAll("560 kb/s", "768 kb/s", "800 kb/s", "1200 kb/s", "1500 kb/s", "2100 kb/s", "3200 kb/s", "4200 kb/s", "4500 kb/s", "5120 kb/s", "8192 kb/s", "16384 kb/s");
        setting4Combo.getItems().addAll("MP3, 92 kb/s", "MP3, 192 kb/s", "MP3, 256 kb/s", "MP3, 320 kb/s");

        /* Set default values if nothing is set already */
        if (outputType.get() == null) {
            formatCombo.setValue("MP4");
            outputType.set("MP4");
        } else {
            formatCombo.setValue(outputType.get());

        }

        if (codec.get() == null) {
            codecCombo.setValue("XviD");
            codec.set("XviD");
        } else {
            codecCombo.setValue(codec.get());
        }

        if (profile.get() == null) {
            profileCombo.setValue("HD Video 720p");
            profile.set("HD Video 720p");
        } else {
            profileCombo.setValue(profile.get());
        }

        if (frameSize.get() == null) {
            setting1Combo.setValue("1280x720");
            frameSize.set("1280x720");
        } else {
            setting1Combo.setValue(frameSize.get());
        }

        if (frameRate.get() == null) {
            setting2Combo.setValue("30 fps");
            frameRate.set("30 fps");
        } else {
            setting2Combo.setValue(frameRate.get());
        }

        if (bitrate.get() == null) {
            setting3Combo.setValue("5120 kb/s");
            bitrate.set("5120 kb/s");
        } else {
            setting3Combo.setValue(bitrate.get());
        }

        if (audioForVideo.get() == null) {
            setting4Combo.setValue("MP3, 192 kb/s");
            audioForVideo.set("MP3, 192 kb/s");
        } else {
            setting4Combo.setValue(audioForVideo.get());
        }

    }

}
