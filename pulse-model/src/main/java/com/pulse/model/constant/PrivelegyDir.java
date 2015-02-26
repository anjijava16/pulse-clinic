/*
 * Copyright 2015 Vladimir Shin
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.pulse.model.constant;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
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
