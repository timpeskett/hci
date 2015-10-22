package wizard.model;

public class WizardConfig {

    /* Default config settings */
    private String colourScheme, language, background, defaultOutputLoc, displaySplash;

    public WizardConfig() {
        colourScheme = "Orange";
        language = "English";
        background = "bg1";
        defaultOutputLoc = "";
        displaySplash = "yes";
    }

    public String getColourScheme() {
        return colourScheme;
    }

    public String getLanguage() {
        return language;
    }

    public String getBackground() {
        return background;
    }

    public String getDisplaySplash() {
        return displaySplash;
    }

    public String getDefaultOutputLoc() {
        return defaultOutputLoc;
    }

    public void setColourScheme(String inColourScheme) {
        colourScheme = inColourScheme;
    }

    public void setLanguage(String inLanguage) {
        language = inLanguage;
    }

    public void setBackground(String inBackground) {
        background = inBackground;
    }

    public void setDisplaySplash(String inDisplaySplash) {
        displaySplash = inDisplaySplash;
    }

    public void setDefaultOutputLoc(String inLoc) {
        defaultOutputLoc = inLoc;
    }

}
