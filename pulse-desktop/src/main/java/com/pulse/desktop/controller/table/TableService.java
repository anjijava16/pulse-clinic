package com.pulse.desktop.controller.table;

import com.pulse.desktop.controller.service.PatientService;
import com.pulse.model.Record;
import java.awt.Color;
import java.awt.Component;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.pulse.model.Patient;


/**
 *
 * @author Vladimir Shin <vladimir.shin@gmail.com>
 */
public enum TableService {

    INSTANCE;

    private TableService() {
    }

    /**
     *
     */
    public void clearTable(TableHolder holder) {
        try {
            TABLE_EDIT_LOCK.lock();

            holder.getModel().getDataVector().removeAllElements();
            holder.getModel().fireTableDataChanged();
        } finally {
            TABLE_EDIT_LOCK.unlock();
        }
    }

    /**
     * This function will return new instance of JTable and associated JPanel
     *
     * @return JScrollPane instance with JTable instance inside
     */
    public TableHolder buildTable(int mode) {
        // Initialize table and table header
        TableHolder holder = new TableHolder();
        holder.buildTable(mode);

        return holder;
    }

    public int selectedRow(TableHolder holder) {
        try {
            TABLE_EDIT_LOCK.lock();

            return holder.getTable().getSelectedRow();
        } finally {
            TABLE_EDIT_LOCK.unlock();
        }
    }

    public static Object getValueAt(TableHolder holder, int row, int column) {
        try {
            TABLE_EDIT_LOCK.lock();

            return holder.getModel().getValueAt(row, column);
        } finally {
            TABLE_EDIT_LOCK.unlock();
        }
    }

    public Patient getSelectedPatient(TableHolder holder) {
        Patient patient = null;

        final int row = holder.getTable().getSelectedRow();
        if (row != -1) {
            final String patientNfp = (String) getValueAt(holder, row, REGISTRY_PATIENT_NFP_FIELD);

            if (patientNfp == null || patientNfp.isEmpty()) {
                return null;
            }

            patient = PatientService.INSTANCE.getByName(patientNfp);
        }

        return patient;
    }

    public Patient getSelectedPatient(TableHolder holder, int position) {
        Patient patient = null;

        final int row = holder.getTable().getSelectedRow();
        if (row != -1) {
            final String patientNfp = (String) getValueAt(holder, row, position);

            if (patientNfp == null || patientNfp.isEmpty()) {
                return null;
            }

            patient = PatientService.INSTANCE.getByName(patientNfp);
        }

        return patient;
    }

    public String getSelectedDoctor(TableHolder holder) {
        final int row = holder.getTable().getSelectedRow();
        if (row != -1) {
            final String doctorNfp = (String) getValueAt(holder, row, REGISTRY_DOCTOR_NAME_FIELD);

            return doctorNfp;
        }

        return null;
    }

    public void addRecordData(TableHolder holder, Record record, Patient patient) {
        final String[] data = new String[PATIENT_RECORD_TABLE_HEADER.length];

        int ptr = 0;
        data[ptr++] = String.valueOf(record.getPatientId());
        data[ptr++] = String.valueOf(record.getId());
        data[ptr++] = String.valueOf(patient.getNfp());
        data[ptr++] = String.valueOf(record.getName());
        data[ptr++] = String.valueOf(record.getName());

        holder.getModel().addRow(data);
    }

    public class TableHolder {

        private final String[][] TABLE_DATA = new String[][]{};

        private JScrollPane scrollPane;
        private JTable table;
        private DefaultTableModel model;

        public TableHolder() {
        }

        public void clear() {
            this.model.getDataVector().removeAllElements();
            this.model.fireTableDataChanged();
        }

        public void buildTable(int mode) {
            switch (mode) {
                case SIMPLE_TABLE:
                    this.model = new DefaultTableModel(TABLE_DATA, REGISTRY_TABLE_HEADER);
                    break;

                case STATIONARY_TABLE:
                    this.model = new DefaultTableModel(TABLE_DATA, STATIONARY_TABLE_HEADER);
                    break;

                case PATIENT_RECORD_TABLE:
                    this.model = new DefaultTableModel(TABLE_DATA, PATIENT_RECORD_TABLE_HEADER);
                    break;

                case ORGANISATION_TABLE:
                    this.model = new DefaultTableModel(TABLE_DATA, ORGANISATIONS_FRAME_HEADER);
                    break;
                    
                case PATIENT_ROOM_TABLE:
                    this.model = new DefaultTableModel(TABLE_DATA, PATIENT_ROOM_FRAME_HEADER);
                    break;

                case STATISTIC_TABLE:
                    this.model = new DefaultTableModel(TABLE_DATA, STATISTIC_TABLE_HEADER);
                    break;

                case GENERAL_TABLE:
                    this.model = new DefaultTableModel(TABLE_DATA, GENERAL_TABLE_HEADER);
                    break;

                case JOURNALS_TABLE:
                    this.model = new DefaultTableModel(TABLE_DATA, JOURNALS_TABLE_HEADER);
                    break;

                case NEXT_VISIT_TABLE:
                    this.model = new DefaultTableModel(TABLE_DATA, NEXT_VISIT_TABLE_HEADER);
                    break;

                case BOOK_KEEPING_TABLE:
                    this.model = new DefaultTableModel(TABLE_DATA, BOOKKEEPING_TABLE_HEADER);
                    break;
                    
                case USERS_TABLE:
                    this.model = new DefaultTableModel(TABLE_DATA, USERS_TABLE_HEADER);
                    break;

                default:
                    break;
            }

            this.table = new JTable(this.model) {
                private static final long serialVersionUID = 5888611849826987986L;

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }

                @Override
                public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                    JComponent c = (JComponent) super.prepareRenderer(renderer, row, col);
                    c.setBackground(Color.LIGHT_GRAY);
                    c.setOpaque(true);

                    return c;
                }
            };

            this.table.getTableHeader().setReorderingAllowed(false);
            this.table.setCellEditor(null);

            this.scrollPane = new JScrollPane(this.table);
        }

        public JScrollPane getScrollPane() {
            return scrollPane;
        }

        public JTable getTable() {
            return table;
        }

        public DefaultTableModel getModel() {
            return model;
        }

    }

    public final static int REGISTRY_COMMING_DATE_FIELD = 0;
    public final static int REGISTRY_VISIT_ID_FIELD = 1;
    public final static int REGISTRY_PATIENT_ID_FIELD = 2;
    public final static int REGISTRY_PATIENT_NFP_FIELD = 3;
    public final static int REGISTRY_BIRTHDAY_FIELD = 4;
    public final static int REGISTRY_VISIT_COURSE_FIELD = 5;
    public final static int REGISTRY_GROUP_FIELD = 6;
    public final static int REGISTRY_ANALYS_FIELD = 7;
    public final static int REGISTRY_DOCTOR_NAME_FIELD = 8;
    public final static int REGISTRY_PAYING_STATUS_FIELD = 9;
    public final static int REGISTRY_PATIENT_TYPE = 10;
    public final static int REGISTRY_VISIT_TYPE_FIELD = 11;
    public final static int REGISTRY_STATUS_FIELD = 12;
    public final static int REGISTRY_FROM_ORGANISATION_FIELD = 13;
    public final static int REGISTRY_FROM_DOCTOR_FIELD = 14;
    public final static int REGISTRY_BONUS_FIELD = 15;
    public final static int REGISTRY_TILL_DATE_FIELD = 16;
    public final static int REGISTRY_CREATED_BY_FIELD = 17;

    public final static int PATIENT_RECORD_PATIENT_NFP = 2;

    public final static int USER_ID_FIELD = 0;
    
    public final static int COMMING_DATE_FIELD = 0;
    public final static int VISIT_ID_FIELD = 1;
    public final static int PATIENT_ID_FIELD = 2;
    public final static int PATIENT_NFP_FIELD = 3;
    public final static int BIRTHDAY_FIELD = 4;
    public final static int VISIT_COURSE_FIELD = 5;
    public final static int GROUP_FIELD = 6;
    public final static int ANALYS_FIELD = 7;
    public final static int DOCTOR_NAME_FIELD = 8;
    public final static int PAYING_STATUS_FIELD = 9;
    public final static int PATIENT_TYPE = 10;
    public final static int BONUS_VALUE = 11;
    public final static int VISIT_TYPE_FIELD = 12;
    public final static int STATUS_FIELD = 12;
    public final static int BONUS_FIELD = 13;

    public final static int PATIENT_ROOM_NAME_FIELD = 0;
    public final static int ORGANISATION_NAME_FIELD = 0;

    public final static int RECORD_ID_FIELD = 1;

    public final static int SIMPLE_TABLE = 1;
    public final static int EXPANDED_TABLE = 3;
    public final static int STATIONARY_TABLE = 2;
    public final static int PATIENT_RECORD_TABLE = 4;
    public final static int ORGANISATION_TABLE = 5;
    public final static int STATISTIC_TABLE = 6;
    public final static int GENERAL_TABLE = 7;
    public final static int JOURNALS_TABLE = 8;
    public final static int NEXT_VISIT_TABLE = 9;
    public final static int BOOK_KEEPING_TABLE = 10;
    public final static int USERS_TABLE = 11;
    public final static int PATIENT_ROOM_TABLE = 12;

    public final String[] USERS_TABLE_HEADER = new String[] {
        "Ид", "Фио", "Привилегии", "Дата рождения", "Логин"
    };
    
    public final String[] STATISTIC_TABLE_HEADER = new String[]{
        "Номер",
        "Приход id",
        "Пациент",
        "Отдел",
        "Группа°",
        "Анализ",
        "Доктор",
        "От организации",
        "От врача",
        "Создал"
    };

    public final String[] GENERAL_TABLE_HEADER = new String[]{
        "Дата прихода",
        "Приход id",
        "Пациент id",
        "Пациент",
        "Дата рождения",
        "Отдел",
        "Группа",
        "Анализ",
        "Врач",
        "Оплата",
        "Тип прихода",
        "Скидка",
        "Статус",
        "От организации",
        "От врача"
    };

    public final String[] REGISTRY_TABLE_HEADER = new String[]{
        "Дата прихода",
        "Приход id",
        "Пациент id",
        "Пациент",
        "Дата рождения",
        "Отдел",
        "Группа",
        "Анализ",
        "Врач",
        "Оплата",
        "Тип прихода",
        "Скидка",
        "Статус",
        "От организации",
        "От врача",
        "Создал"
    };

    public final String[] STATIONARY_TABLE_HEADER = new String[]{
        "Дата поступления", "Приход id", "Направил", "Пациент", "Палата", "Оплата", "Скидка"
    };

    public final String[] PATIENT_RECORD_TABLE_HEADER = new String[]{
        "Пациент id",
        "Анкета id",
        "Пациент",
        "Создана",
        "ИД"};

    public final String[] BOOKKEEPING_TABLE_HEADER = new String[]{
        "Номер",
        "Дата прихода",
        "Приход id",
        "Пациент id",
        "Пациент",
        "Дата рождения",
        "Отдел",
        "Группа",
        "Анализ",
        "Врач",
        "Оплата",
        "Тип прихода",
        "Скидка",
        "Статус",
        "От организации",
        "От врача",
        "Создал"
    };

    public final String[] NEXT_VISIT_TABLE_HEADER = new String[]{
        "Дата прихода", "ИД", "Пациент", "Врач", "Телефон"
    };

    public final String[] JOURNALS_TABLE_HEADER = new String[]{
        "Дата создания", "ИД", "Название"
    };

    public final String[] ORGANISATIONS_FRAME_HEADER = new String[] {
        "Название"
    };
    
    public final String[] PATIENT_ROOM_FRAME_HEADER = new String[] {
        "Название", "Вместимость", "Занято"
    };

    private final static Lock TABLE_EDIT_LOCK = new ReentrantLock();
}