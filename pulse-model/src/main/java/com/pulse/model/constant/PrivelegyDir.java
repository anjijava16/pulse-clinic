package com.pulse.model.constant;

/**
 *
 * @author befast
 */
public enum PrivelegyDir {
    
    LABORATORY_PATH(Privelegy.Laboratory, "data/laboratory/temporary/", "data/laboratory/templates/", "data/laboratory/analys/"),
    GINECOLOGY_PATH(Privelegy.Ginecology, "data/ginecology/temporary/", "data/ginecology/templates/", "data/ginecology/analys/"),
    ULTRASOUND_PATH(Privelegy.Ultrasound, "data/ultrasound/temporary/", "data/ultrasound/templates/", "data/ultrasound/analys/"),
    FIZIO_PATH(Privelegy.Fizio, "data/fizio/temporary/", "data/fizio/templates/", "data/fizio/analys/"),
    MRI_PATH(Privelegy.MagneticResonanceImaging, "data/mri/temporary/", "data/mri/templates/", "data/mri/analys/"),
    RECORDS_PATH(Privelegy.PatientRecord, "data/records/temporary/", "data/records/templates/", ""),
    JOURNALS_PATH(Privelegy.Journal, "data/journal/temporary/", "data/journal/templates/", "data/journal/analys/"),
    APPOINTMENTS_PATH(Privelegy.PatientAppointment, "data/appointment/temporary/", "data/appointment/templates/", "data/appointment/analys/");
    
    private PrivelegyDir(Privelegy privelegy, String temporaryPath, String templatePath, String analysPath) {
        this.privelegy = privelegy;
        this.temporaryPath = temporaryPath;
        this.templatePath = templatePath;
        this.analysPath = analysPath;
    }
    
    private Privelegy privelegy;
    private String temporaryPath;
    private String templatePath;
    private String analysPath;
    
    public Privelegy getPrivelegy() {
        return privelegy;
    }

    public String getTemporaryPath() {
        return temporaryPath;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public String getAnalysPath() {
        return analysPath;
    }        
    
    public static PrivelegyDir getPathBy(Privelegy privelegy) {
        for (PrivelegyDir sp : values()) {
            if (privelegy.equals(sp.privelegy)) {
                return sp;
            }
        }
        
        return null;
    }
}
