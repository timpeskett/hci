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
public abstract class Project {

    private List<File> inputFiles = null;

    private File outputDirectory = null;
    private String outputFileName = null;
    private String outputFileType = null;

    
    public Project(){
        //
    }
    
    public Project(List<File> inInputFiles, File inOutputLocation, String inOutputFileName, String inOutputFileType) {
        inputFiles = inInputFiles;
        outputDirectory = inOutputLocation;
        outputFileName = inOutputFileName;
        outputFileType = inOutputFileType;
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

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public String getOutputFileType() {
        return outputFileType;
    }

    public void setOutputFileType(String outputFileType) {
        this.outputFileType = outputFileType;
    }

}
