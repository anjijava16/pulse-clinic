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
    
    LABORATORY_PATH(Privilege.Laboratory, "data/laboratory/temporary/", "data/laboratory/templates/", "data/laboratory/analys/"),
    GINECOLOGY_PATH(Privilege.Ginecology, "data/ginecology/temporary/", "data/ginecology/templates/", "data/ginecology/analys/"),
    ULTRASOUND_PATH(Privilege.Ultrasound, "data/ultrasound/temporary/", "data/ultrasound/templates/", "data/ultrasound/analys/"),
    FIZIO_PATH(Privilege.Fizio, "data/fizio/temporary/", "data/fizio/templates/", "data/fizio/analys/"),
    MRI_PATH(Privilege.MagneticResonanceImaging, "data/mri/temporary/", "data/mri/templates/", "data/mri/analys/"),
    RECORDS_PATH(Privilege.PatientRecord, "data/records/temporary/", "data/records/templates/", ""),
    JOURNALS_PATH(Privilege.Journal, "data/journal/temporary/", "data/journal/templates/", "data/journal/analys/"),
    APPOINTMENTS_PATH(Privilege.PatientAppointment, "data/appointment/temporary/", "data/appointment/templates/", "data/appointment/analys/");
    
    private PrivelegyDir(Privilege privilege, String temporaryPath, String templatePath, String analysPath) {
        this.privilege = privilege;
        this.temporaryPath = temporaryPath;
        this.templatePath = templatePath;
        this.analysPath = analysPath;
    }
    
    private Privilege privilege;
    private String temporaryPath;
    private String templatePath;
    private String analysPath;
    
    public Privilege getPrivilege() {
        return privilege;
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
    
    public static PrivelegyDir getPathBy(Privilege privilege) {
        for (PrivelegyDir sp : values()) {
            if (privilege.equals(sp.privilege)) {
                return sp;
            }
        }
        
        return null;
    }
}
