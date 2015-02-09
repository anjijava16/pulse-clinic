package com.pulse.desktop.controller;

import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.service.UserFacade;
import com.pulse.desktop.controller.table.StationaryTableService;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.desktop.controller.table.UsersTableService;
import com.pulse.model.User;
import com.pulse.model.Visit;
import com.pulse.model.constant.Privelegy;
import com.pulse.rest.client.UserClient;
import com.pulse.rest.client.VisitClient;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class RefreshUsersListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    private final UserClient visitClient = new UserClient();
    private UsersTableService tableService;
        
    public RefreshUsersListener(Privelegy privelegy, TableHolder tableHolder) {
        super(privelegy, tableHolder);
        this.tableService = new UsersTableService(tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            try {                
                getTableHolder().clear();

                List<User> list = this.visitClient.list();
                
                ResultToolbarService.INSTANCE.showSuccessStatus();
                
                this.tableService.add(list);
                
                UserFacade.INSTANCE.updateAll(list);
            } catch (IOException ioe) {
                this.LOGGER.error(ioe.getMessage());
                ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");                
            }
        });
    }
}
