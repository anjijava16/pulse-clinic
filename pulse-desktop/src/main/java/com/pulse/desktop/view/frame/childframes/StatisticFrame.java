package com.pulse.desktop.view.frame.childframes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

import com.pulse.desktop.controller.table.TableService;
import com.pulse.desktop.controller.StatisticSearchListener;
import com.pulse.model.constant.Privelegy;

/**
 *
 * @author befast
 */
public class StatisticFrame extends AbstractTabledChildFrame {

    private final TableService.TableHolder TABLE_HOLDER = TableService.INSTANCE.buildTable(TableService.STATISTIC_TABLE);
    private final List<JComponent> TOOLBAR_BTN_LIST = new ArrayList<>(10);

    private final JComboBox<String> ORGANISATIONS_LIST = new JComboBox<>();
    private final JButton SEARCH_PATTERN_BUTTON = new JButton("", new ImageIcon("./pic/update.png"));

    private final Privelegy privelegy = Privelegy.Statistic;

    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
    private final JDatePickerImpl SEARCH_FROM_DATE_PICKER = new JDatePickerImpl(new JDatePanelImpl(null));
    private final JDatePickerImpl SEARCH_UNTIL_DATE_PICKER = new JDatePickerImpl(new JDatePanelImpl(null));

    private void buildActionListeners() {
        this.SEARCH_FROM_DATE_PICKER.getJFormattedTextField().setText(this.FORMATTER.format(new Date()));
        this.SEARCH_UNTIL_DATE_PICKER.getJFormattedTextField().setText(this.FORMATTER.format(new Date()));

        StatisticSearchListener ssl = new StatisticSearchListener(
                this.privelegy, this.TABLE_HOLDER, this.ORGANISATIONS_LIST, this.SEARCH_FROM_DATE_PICKER, this.SEARCH_UNTIL_DATE_PICKER
        );

        this.SEARCH_PATTERN_BUTTON.addActionListener(ssl);
    }

    public StatisticFrame() {
        super.setPrivelegy(privelegy);
        super.setTableHolder(this.TABLE_HOLDER);

        buildActionListeners();

        this.SEARCH_PATTERN_BUTTON.setToolTipText("Поиск");

        this.TOOLBAR_BTN_LIST.add(this.ORGANISATIONS_LIST);
        this.TOOLBAR_BTN_LIST.add(this.SEARCH_FROM_DATE_PICKER);
        this.TOOLBAR_BTN_LIST.add(this.SEARCH_UNTIL_DATE_PICKER);
        this.TOOLBAR_BTN_LIST.add(this.SEARCH_PATTERN_BUTTON);

        super.setToolbarBtnList(TOOLBAR_BTN_LIST);
        super.initializeFrame();
    }
}