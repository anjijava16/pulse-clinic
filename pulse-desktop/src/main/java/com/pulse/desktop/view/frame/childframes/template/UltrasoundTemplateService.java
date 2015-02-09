package com.pulse.desktop.view.frame.childframes.template;

import com.pulse.model.constant.PrivelegyDir;

/**
 *
 * @author befast
 */
public class UltrasoundTemplateService extends TemplateService {
            
    public UltrasoundTemplateService() {
        this.root = PrivelegyDir.ULTRASOUND_PATH.getAnalysPath();
    }
}
