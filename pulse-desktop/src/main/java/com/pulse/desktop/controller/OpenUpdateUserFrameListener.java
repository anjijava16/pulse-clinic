package com.pulse.desktop.controller;

import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.view.manager.WindowManager;
import com.pulse.model.User;
import com.pulse.model.constant.Privelegy;
import com.pulse.rest.client.UserClient;
import java.awt.event.ActionEvent;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class OpenUpdateUserFrameListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private UserClient userService = new UserClient();

    public OpenUpdateUserFrameListener(Privelegy privelegy, TableService.TableHolder tableHolder) {
        super(privelegy, tableHolder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            if (WindowManager.getInstance().getAccountChangingFrame().isVisible()) {
                WindowManager.getInstance().getAccountChangingFrame().setVisible(false);
            } else {
                int row = getTableHolder().getTable().getSelectedRow();

                this.LOGGER.debug("row: {}", row);

                if (row < 0) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Пользователь не выбран");
                    return;
                }

                final long userId = Long.valueOf(
                        TableService.getValueAt(getTableHolder(), row, TableService.USER_ID_FIELD).toString()
                );

                this.LOGGER.debug("userId: {}", userId);

                try {
                    final User user = userService.get(userId);

                    if (user != null) {
                        WindowManager.getInstance().getAccountChangingFrame().getUserInfoPanel().showAccountsData(user);

                        WindowManager.getInstance().getAccountChangingFrame().setVisible(true);
                    } else {
                        ResultToolbarService.INSTANCE.showFailedStatus("Пользователь не найден");
                        return;
                    }
                } catch (IOException ioe) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                }
            }
        });
    }
}
