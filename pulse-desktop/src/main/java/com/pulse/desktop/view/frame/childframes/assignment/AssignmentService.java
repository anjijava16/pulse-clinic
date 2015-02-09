package com.pulse.desktop.view.frame.childframes.assignment;

import com.pulse.desktop.view.frame.childframes.template.GinecologyTemplateService;
import com.pulse.desktop.view.frame.childframes.template.LaboratoryTemplateService;
import com.pulse.desktop.view.frame.childframes.template.MriTemplateService;
import com.pulse.desktop.view.frame.childframes.template.PhysiotherapyTemplateService;
import com.pulse.desktop.view.frame.childframes.template.TemplateService;
import com.pulse.desktop.view.frame.childframes.template.UltrasoundTemplateService;
import com.pulse.model.constant.Privelegy;


/**
 *
 * @author Vladimir Shin
 */
public enum AssignmentService {
    
    INSTANCE;
    
    private TemplateService laboratoryTemlateService = new LaboratoryTemplateService();
    private TemplateService ultrasoundTemlateService = new UltrasoundTemplateService();
    private TemplateService ginecologyTemlateService = new GinecologyTemplateService();
    private TemplateService physiotherapyTemlateService = new PhysiotherapyTemplateService();
    private TemplateService mriTemlateService = new MriTemplateService();
    
    private AssignmentFrame laboratoryFrame = new AssignmentFrame(this.laboratoryTemlateService, Privelegy.Laboratory.getName());
    private AssignmentFrame ultrasoundFrame = new AssignmentFrame(this.ultrasoundTemlateService, Privelegy.Ultrasound.getName());
    private AssignmentFrame ginecologyFrame = new AssignmentFrame(this.ginecologyTemlateService, Privelegy.Ginecology.getName());
    private AssignmentFrame physiotherapyFrame = new AssignmentFrame(this.physiotherapyTemlateService, Privelegy.Fizio.getName());
    private AssignmentFrame mriFrame = new AssignmentFrame(this.mriTemlateService, Privelegy.MagneticResonanceImaging.getName());
    
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
