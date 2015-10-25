package wizard.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import wizard.controller.MainController.ProjectType;

/* Name: FinaliseController
 * Description: Controller class for Finalise.fxml
 *              Finalise is a sub-window of the center panel and displays
 *              all current user input to the user so they can make their
 *              decision on if they wish to convert.
 * @author Chris
 */
public class FinaliseController {

    private CenterPanelController centerPanel;
    private ProjectType type;

    private WorkSimulation work;
    private boolean inProgress = false;
    private SimpleIntegerProperty state;

    /* FXML components for injection */
    @FXML
    private ProgressBar finaliseProgressBar;
    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private AnchorPane finalisePane;
    @FXML
    private Button backBtn;
    @FXML
    private Button createBtn;

    @FXML
    private Label inputFilesTxt;
    @FXML
    private Label outputFilesTxt;

    @FXML
    private Label formatResult;
    @FXML
    private Label codecResult;
    @FXML
    private Label setting1Result;
    @FXML
    private Label setting2Result;
    @FXML
    private Label setting3Result;
    @FXML
    private Label setting4Result;
    @FXML
    private Label setting1Txt;
    @FXML
    private Label setting2Txt;
    @FXML
    private Label setting3Txt;
    @FXML
    private Label setting4Txt;
    @FXML
    private Label codecTxt;

    /* Project settings */
    private StringProperty outputType;
    private StringProperty codec;
    private List<File> inputFiles;
    private StringProperty outputFilename;
    private StringProperty profile;
    private StringProperty bitrate;

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
        createBtn.setTooltip(new Tooltip("Start conversion and create new file"));
        backBtn.setTooltip(new Tooltip("Return to settings"));
        finaliseProgressBar.setTooltip(new Tooltip("Current progress of conversion"));
    }

    public void backBtnPressed() {
        state.set(state.subtract(1).get());
    }

    public void createBtnPressed() {

        try {
            if (centerPanel.getProjectType() == ProjectType.VIDEO_PROJECT) {
                Converter converter = new Converter();
                System.out.println("----------------------------------------------------------------");
                System.out.println("input file:" + inputFiles.get(0).getPath());
                converter.addInputFile(inputFiles.get(0).getPath());
                System.out.println("output loc/file:" + centerPanel.getOutputFileLoc().getPath() + "\\" + centerPanel.getOutputFilename().get() + "." + formatResult.getText().toLowerCase());
                converter.setOutputFile(centerPanel.getOutputFileLoc().getPath() + "\\" + centerPanel.getOutputFilename().get() + "." + formatResult.getText().toLowerCase());

                //converter.setPosition(inputFiles.get(0).getPath(), 0, 0, 20);
                //converter.setPosition(inputFiles.get(0).getPath(), 0, 0, 0);
                //converter.setDuration(300);
                int fps = Integer.parseInt(centerPanel.getFrameRate().get().substring(0, 2));
                System.out.println("fps:" + fps + ", with no modification: " + centerPanel.getFrameRate().get());
                converter.setFrameRate(fps);
                System.out.println("framesize:" + centerPanel.getFrameSize().get());
                int w = Integer.parseInt(centerPanel.getFrameSize().get().split("x")[0]);
                int h = Integer.parseInt(centerPanel.getFrameSize().get().split("x")[1]);
                System.out.println("new w/h:" + w + "/" + h);
                converter.setFrameSize(w, h);
                System.out.println("codec:" + centerPanel.getCodec().get());
                String codec = "";
                switch (centerPanel.getCodec().get()) {
                    case "H.264/AVC":
                        codec = "h264";
                        break;
                    case "XviD":
                        codec = "libxvid";
                        break;
                    case "MPEG-4":
                        codec = "mpeg4";
                        break;
                    case "MPEG-2":
                        codec = "mpeg2video";
                        break;
                }
                System.out.println("finale encoder:" + codec);
                converter.setVideoEncoder(codec);
                System.out.println("bitrate:" + centerPanel.getBitrate().get());
                String bitrate = centerPanel.getBitrate().get().split(" ")[0] + "k";
                System.out.println("final bitrate:" + bitrate);
                converter.setVideoBitRate(bitrate);
                String format = formatResult.getText().toLowerCase().replace(".", "");
                System.out.println("final format:" + format);
                //converter.setFormat(format);
                converter.convert();
            } else {
                Converter converter = new Converter();
                converter.addInputFile(inputFiles.get(0).getPath());
                converter.setOutputFile(centerPanel.getOutputFileLoc().getPath() + "\\" + centerPanel.getOutputFilename().get() + "." + formatResult.getText().toLowerCase());
                String bitrate = centerPanel.getBitrate().get().split(" ")[0] + "k";
                converter.setAudioBitRate(bitrate);
                String codec = "";
                // codec not required for WMA
                switch (centerPanel.getCodec().get()) {
                    case "MP3":
                        codec = "libmp3lame";
                        converter.setVideoEncoder(codec);
                        break;
                    case "PCM":
                        codec = "pcm_s16le";
                        converter.setVideoEncoder(codec);
                        break;
                    case "WMA":
                        codec = "wmav2";
                        converter.setVideoEncoder(codec);
                        break;
                    case "Ogg":
                        codec = "libvorbis";
                        converter.setVideoEncoder(codec);
                }
                if ("Mono".equals(centerPanel.getChannel().get())) {
                    converter.setAudioChannels("1");
                } else {
                    converter.setAudioChannels("2");
                }
                String samplerate = centerPanel.getSamplerate().get().split(" ")[0];
                converter.setAudioSamplerate(samplerate);
                converter.convert();
            }
        } catch (ConvertParamsException cpe) {
            System.out.println("Bad conversion params");
        } catch (IOException ioe) {
            System.out.println("No ffmpeg");
        } catch (ConversionInProcessException cip) {
            System.out.println("Conversion already happening");
        }

        if (!inProgress) {
            work = new WorkSimulation();
            createBtn.setText("Cancel");
            work.execute();
            inProgress = true;
            backBtn.setDisable(true);
        } else {
            work.cancel(true);
        }
    }

    public void setProjectType(MainController.ProjectType inType) {
        type = inType;
        if (type == MainController.ProjectType.AUDIO_PROJECT) {
            setupAudioSummary();
        } else if (type == MainController.ProjectType.VIDEO_PROJECT) {
            setupVideoSummary();
        }
    }

    private void setupAudioSummary() {
        outputType = centerPanel.getOutputFileType();
        codec = centerPanel.getCodec();
        profile = centerPanel.getProfile();
        bitrate = centerPanel.getBitrate();
        channels = centerPanel.getChannel();
        sampleRate = centerPanel.getSamplerate();
        inputFiles = centerPanel.getInputFiles();
        outputFilename = centerPanel.getOutputFilename();

        inputFilesTxt.setFont(Font.font(null, FontWeight.BOLD, 16));
        outputFilesTxt.setFont(Font.font(null, FontWeight.BOLD, 16));

        formatResult.setText(outputType.get());
        codecTxt.setText("Codec          ");
        codecResult.setText(codec.get());
        setting1Txt.setText("Bitrate:           ");
        setting1Result.setText(bitrate.get()); //bitrate channels samplerate
        setting2Txt.setText("Channels:     ");
        setting2Result.setText(channels.get());
        setting3Txt.setText("Sample Rate:  ");
        setting3Result.setText(sampleRate.get());
        setting4Txt.setVisible(false);
        setting4Result.setVisible(false);
        String inputFilesString = "";

        /* Load file names for summary */
        if (inputFiles != null) {
            for (File f : inputFiles) {
                inputFilesString += f.getName();
                if (f != inputFiles.get(inputFiles.size() - 1)) {
                    inputFilesString += ", ";
                }
            }
            inputFilesTxt.setText(inputFilesString);
        } else {
            inputFilesTxt.setText("None specified, please go back and select input files.");
        }

        outputFilesTxt.setText(outputFilename.get() + "." + formatResult.getText().toLowerCase());

    }

    private void setupVideoSummary() {
        outputType = centerPanel.getOutputFileType();
        codec = centerPanel.getCodec();
        profile = centerPanel.getProfile();
        bitrate = centerPanel.getBitrate();
        audioForVideo = centerPanel.getAudioForVideo();
        frameRate = centerPanel.getFrameRate();
        frameSize = centerPanel.getFrameSize();
        inputFiles = centerPanel.getInputFiles();

        outputFilename = centerPanel.getOutputFilename();

        inputFilesTxt.setFont(Font.font(null, FontWeight.BOLD, 16));
        outputFilesTxt.setFont(Font.font(null, FontWeight.BOLD, 16));

        formatResult.setText(outputType.get());
        codecTxt.setText("Codec               ");
        codecResult.setText(codec.get());
        setting1Txt.setText("Framesize:      ");
        setting1Result.setText(frameSize.get()); //bitrate channels samplerate
        setting2Txt.setText("FrameRate:       ");
        setting2Result.setText(frameRate.get());
        setting3Txt.setText("Bitrate:            ");
        setting3Result.setText(bitrate.get());
        setting4Txt.setText("Audio settings: ");
        setting4Result.setText(audioForVideo.get());
        String inputFilesString = "";

        /* Load file names for summary */
        if (inputFiles != null) {
            for (File f : inputFiles) {
                inputFilesString += f.getName();
                if (f != inputFiles.get(inputFiles.size() - 1)) {
                    inputFilesString += ", ";
                }
            }
            inputFilesTxt.setText(inputFilesString);
        } else {
            inputFilesTxt.setText("None specified, please go back and select input files.");
        }

        outputFilesTxt.setText(outputFilename.get() + "." + formatResult.getText().toLowerCase());
    }

    class WorkSimulation extends SwingWorker<Integer, Integer> {

        private int iterationsRequired = 1000;
        private int iterationCount = 0;

        @Override
        protected Integer doInBackground() throws Exception {
            for (int i = 0; i < iterationsRequired; i++) {
                //text.append("This is iteration number " + iterationCount++ + '\n');
                Thread.sleep(10);
                publish(i);
            }
            return 100;
        }

        @Override
        protected void process(java.util.List<Integer> list) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    finaliseProgressBar.setProgress(((list.get(0) / (double) iterationsRequired)));
                    progressIndicator.setProgress(((list.get(0) / (double) iterationsRequired)));
                }
            });
            //System.out.println("Complete: " + ((list.get(0) / (double) iterationsRequired)) + "%");
        }

        @Override
        protected void done() {
            try {
                //inProgress = false;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            /* When finished and result = successful */
                            finaliseProgressBar.setProgress(get());
                            progressIndicator.setProgress(get());
                            /*Alert alert = new Alert(AlertType.INFORMATION);
                             alert.setTitle("Finished converting");
                             alert.setHeaderText("FFMpeg Alert");
                             alert.setContentText("Conversion was successful!");
                             alert.show();*/
                            JOptionPane optionPane = new JOptionPane();
                            optionPane.setMessage("Finished converting");
                            JDialog dialog = optionPane.createDialog("Success");
                            dialog.setAlwaysOnTop(true);
                            dialog.setVisible(true);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FinaliseController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ExecutionException ex) {
                            Logger.getLogger(FinaliseController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (CancellationException e) {

                        } finally {
                            /* When finished regardless of result*/
                            createBtn.setText("Create");
                            backBtn.setDisable(false);
                            inProgress = false;

                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
