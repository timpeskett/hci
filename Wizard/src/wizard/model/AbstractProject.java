/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard.model;

import java.io.File;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Chris
 */
public abstract class AbstractProject {

    private List<File> inputFiles = null;

    private File outputDirectory;
    private StringProperty outputFileName;
    private StringProperty outputFileType;
    private StringProperty profile;

    public AbstractProject() {
        profile = new SimpleStringProperty();
        outputFileType = new SimpleStringProperty();
        outputFileName = new SimpleStringProperty();
    }

    // generic setters and getters below
    public List<File> getInputFiles() {
        return inputFiles;
    }

    public void setInputFiles(List<File> inputFiles) {
        this.inputFiles = inputFiles;
    }

    public File getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputLocation(File outputLocation) {
        this.outputDirectory = outputLocation;
    }

    public StringProperty getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName.set(outputFileName);
    }

    public StringProperty getOutputFileType() {
        return outputFileType;
    }

    public void setOutputFileType(String outputFileType) {
        this.outputFileType.set(outputFileType);
    }

    public void setProfile(String inProfile) {
        this.profile.set(inProfile);
    }

    public StringProperty getProfile() {
        return profile;
    }
}
