/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard.controller;

import java.io.File;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javax.swing.SwingWorker;

/**
 *
 * @author Chris
 */
public class FinaliseController {

    private CenterPanelController centerPanel;
    private MainController.ProjectType type;
    private boolean inProgress = false;
    WorkSimulation work;

    @FXML
    private ProgressBar finaliseProgressBar;
    @FXML
    private ProgressIndicator progressIndicator;

    private SimpleIntegerProperty state;
    @FXML
    private AnchorPane finalisePane;
    @FXML
    private Button backBtn;
    @FXML
    private Button createBtn;

    @FXML
    private Text inputFilesTxt;
    @FXML
    private Text outputFilesTxt;

    @FXML
    private Text formatResult;
    @FXML
    private Text codecResult;
    @FXML
    private Text setting1Result;
    @FXML
    private Text setting2Result;
    @FXML
    private Text setting3Result;
    @FXML
    private Text setting4Result;
    @FXML
    private Text setting1Txt;
    @FXML
    private Text setting2Txt;
    @FXML
    private Text setting3Txt;
    @FXML
    private Text setting4Txt;
    @FXML
    private Text codecTxt;

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
    }

    public void backBtnPressed() {
        state.set(state.subtract(1).get());
    }

    public void createBtnPressed() {
        
        if (!inProgress) {
             work = new WorkSimulation();
            createBtn.setText("Cancel");
            work.execute();
            inProgress = true;
            backBtn.setDisable(true);
        } else {
           // System.out.println("Trying to cancel.");
            work.cancel(true);
           // inProgress = false;
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
                            

                        } catch (InterruptedException ex) {
                            System.out.println("A");
                            //Logger.getLogger(tute_example.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ExecutionException ex) {
                            System.out.println("B");
                            //Logger.getLogger(tute_example.class.getName()).log(Level.SEVERE, null, ex);
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
