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
package com.pulse.desktop.view.manager;


import com.pulse.desktop.controller.service.UserFacade;
import com.pulse.model.constant.Privelegies;
import com.pulse.model.constant.Privelegy;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public enum PrivelegyService {
    
    INSTANCE;
    
    private PrivelegyService() {
    }
    
    public void enablePrivelegies(int privelegy) {
        switch (privelegy) {
            case Privelegies.ADMINISTRATOR:
            case Privelegies.DIRECTOR:
                break;
                
            default:
                WindowManager.getInstance().getMainFrame().enableAdministrationFrame(false);
                WindowManager.getInstance().getMainFrame().enableOrganisationFrame(false);

                if (UserFacade.INSTANCE.getApplicationUser().getPrivelegy() != Privelegy.BookKeeping.getId())
                    WindowManager.getInstance().getMainFrame().enableMoneyFrame(false);

                if (UserFacade.INSTANCE.getApplicationUser().getPrivelegy() != Privelegy.TicketWindow.getId())
                    WindowManager.getInstance().getMainFrame().enableTicketFrame(false);

                if (UserFacade.INSTANCE.getApplicationUser().getPrivelegy() != Privelegy.Statistic.getId())
                    WindowManager.getInstance().getMainFrame().enableStatisticFrame(false);

//                if (UserFacade.INSTANCE.getApplicationUser().getPrivelegy() != Privelegy.Journal.getId())
//                    WindowManager.getInstance().getMainFrame().enableJournalFrame(false);

                WindowManager.getInstance().getMainFrame().enablePatientRoomFrame(false);
                                
                WindowManager.getInstance().getRegistryFrame().disableAccess();

                if (UserFacade.INSTANCE.getApplicationUser().getPrivelegy() != Privelegy.Urology.getId())
                    WindowManager.getInstance().getUrologyFrame().disableAccess();
                
                if (UserFacade.INSTANCE.getApplicationUser().getPrivelegy() != Privelegy.Ginecology.getId())
                    WindowManager.getInstance().getGinecologyFrame().disableAccess();
                
                if (UserFacade.INSTANCE.getApplicationUser().getPrivelegy() != Privelegy.Ultrasound.getId())
                    WindowManager.getInstance().getUltrasoundFrame().disableAccess();
                
                if (UserFacade.INSTANCE.getApplicationUser().getPrivelegy() != Privelegy.Okulist.getId())
                    WindowManager.getInstance().getOculistFrame().disableAccess();
                
                if (UserFacade.INSTANCE.getApplicationUser().getPrivelegy() != Privelegy.MagneticResonanceImaging.getId())
                    WindowManager.getInstance().getMriFrame().disableAccess();
                
                if (UserFacade.INSTANCE.getApplicationUser().getPrivelegy() != Privelegy.Vertebrolog.getId())
                    WindowManager.getInstance().getVertebrologistFrame().disableAccess();
                
                if (UserFacade.INSTANCE.getApplicationUser().getPrivelegy() != Privelegy.Hirurgiya.getId())
                    WindowManager.getInstance().getSurgeryFrame().disableAccess();
                
                if (UserFacade.INSTANCE.getApplicationUser().getPrivelegy() != Privelegy.Nevropatolog.getId())
                    WindowManager.getInstance().getNeurologistFrame().disableAccess();
                
                if (UserFacade.INSTANCE.getApplicationUser().getPrivelegy() != Privelegy.Endokrinolog.getId())
                    WindowManager.getInstance().getEndoFrame().disableAccess();
                
                if (UserFacade.INSTANCE.getApplicationUser().getPrivelegy() != Privelegy.Terapevt.getId())
                    WindowManager.getInstance().getTherapeuticFrame().disableAccess();
                
                if (UserFacade.INSTANCE.getApplicationUser().getPrivelegy() != Privelegy.Fizio.getId())
                    WindowManager.getInstance().getPhysiotherapyFrame().disableAccess();
                
                break;
        }
    }
}
