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
package com.pulse.desktop.view.frame.childframes.assignment;


import com.pulse.desktop.view.frame.childframes.template.GinecologyTemplateService;
import com.pulse.desktop.view.frame.childframes.template.LaboratoryTemplateService;
import com.pulse.desktop.view.frame.childframes.template.MriTemplateService;
import com.pulse.desktop.view.frame.childframes.template.PhysiotherapyTemplateService;
import com.pulse.desktop.view.frame.childframes.template.TemplateService;
import com.pulse.desktop.view.frame.childframes.template.UltrasoundTemplateService;
import com.pulse.model.constant.Privilege;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public enum AssignmentService {
    
    INSTANCE;
    
    private TemplateService laboratoryTemplateService = new LaboratoryTemplateService();
    private TemplateService ultrasoundTemplateService = new UltrasoundTemplateService();
    private TemplateService ginecologyTemplateService = new GinecologyTemplateService();
    private TemplateService physiotherapyTemplateService = new PhysiotherapyTemplateService();
    private TemplateService mriTemplateService = new MriTemplateService();
    
    private AssignmentFrame laboratoryFrame = new AssignmentFrame(this.laboratoryTemplateService, Privilege.Laboratory.getName());
    private AssignmentFrame ultrasoundFrame = new AssignmentFrame(this.ultrasoundTemplateService, Privilege.Ultrasound.getName());
    private AssignmentFrame ginecologyFrame = new AssignmentFrame(this.ginecologyTemplateService, Privilege.Ginecology.getName());
    private AssignmentFrame physiotherapyFrame = new AssignmentFrame(this.physiotherapyTemplateService, Privilege.Fizio.getName());
    private AssignmentFrame mriFrame = new AssignmentFrame(this.mriTemplateService, Privilege.MagneticResonanceImaging.getName());
    
    private AssignmentService() {}
    
    public void hideAllFrames() {
        this.laboratoryFrame.getInternalFrame().setVisible(false);
        this.ultrasoundFrame.getInternalFrame().setVisible(false);
        this.ginecologyFrame.getInternalFrame().setVisible(false);
        this.physiotherapyFrame.getInternalFrame().setVisible(false);
        this.mriFrame.getInternalFrame().setVisible(false);
    }
    
    public AssignmentFrame getLaboratoryFrame() {
        return laboratoryFrame;
    }

    public AssignmentFrame getUltrasoundFrame() {
        return ultrasoundFrame;
    }

    public AssignmentFrame getGinecologyFrame() {
        return ginecologyFrame;
    }

    public AssignmentFrame getPhysiotherapyFrame() {
        return physiotherapyFrame;
    }

    public AssignmentFrame getMriFrame() {
        return mriFrame;
    }
}
