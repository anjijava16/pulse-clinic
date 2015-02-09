package com.pulse.desktop.view.frame.childframes.template;

import com.pulse.model.constant.PrivelegyDir;


/**
 *
 * @author befast
 */
public class LaboratoryTemplateService extends TemplateService {
            
    public LaboratoryTemplateService() {
        this.root = PrivelegyDir.LABORATORY_PATH.getAnalysPath();
    }
}
