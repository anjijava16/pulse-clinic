package com.pulse.desktop.controller;

import com.pulse.desktop.controller.builder.MessageBuilder;
import com.pulse.desktop.controller.service.ResultToolbarService;
import com.pulse.desktop.controller.service.ThreadPoolService;
import com.pulse.desktop.controller.table.TableService;
import com.pulse.model.constant.Privelegy;
import com.pulse.rest.client.UserClient;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author befast
 */
public class DeleteUserListener extends AbstractTableListener {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final UserClient userService = new UserClient();
            
    public DeleteUserListener(Privelegy privelegy, TableService.TableHolder holder) {
        super(privelegy, holder);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ThreadPoolService.INSTANCE.execute(() -> {
            this.LOGGER.debug("actionPerformed()");
            
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
            
            final int answer = MessageBuilder.getAnswerCode(
                    null, "Вы действительно хотите удалить запись?", "Удаление"
            );

            if (answer == JOptionPane.YES_OPTION) {            
                try {
                    userService.delete(userId);
                    getTableHolder().getModel().removeRow(row);
                    ResultToolbarService.INSTANCE.showSuccessStatus();
                } catch (IOException ioe) {
                    ResultToolbarService.INSTANCE.showFailedStatus("Ошибка сети");
                }
            }                        
        });
    }
}