package com.pulse.desktop.view.util;

/**
 *
 * @author befast
 */
public interface Settings {
    public static final String APPLICATION_VERSION = "Prometheus v.1.0.0a - ";
    
    public static final int TEXT_FIELD_MAX_CHARS = 10;
    
    public static final String M_OFFICE_PATH = ConfigParser.getWordPath();
    public static final String E_OFFICE_PATH = ConfigParser.getExcelPath();
    
    public static final String[] BONUS_STATUS = {"Нет", "Есть"};
    
//    public static final byte NOT_PAYED_STATUS = 0;
//    public static final byte PAYED_STATUS = 1;
//    public static final byte DEBT_STATUS = 2;
//    public static final byte PAYED_BACK_STATUS = 3;
        
    public static final String[] VISIT_STATUSES = { "Не осмотрен", 
                                                    "Осмотрен" };
    public static final byte NOT_VISITED = 0;
    public static final byte VISITED = 1;
    
    public static final String[] VISIT_TYPES = { "По приходу", 
                                                 "Записанный" };
    public static final byte VISIT_TYPE_CAMED = 0;
    public static final byte VISIT_TYPE_REGISTRED = 1;
    
    public static final byte STATUS_TYPE_PAY  = 0;
    public static final byte STATUS_TYPE_VIEW = 1;
        
    public static final String OLD_DOC_TRAILOR = ".doc";
    public static final String OLD_XSL_TRAILOR = ".xls";
    public static final String DOC_TRAILOR = ".docx";
    public static final String XSL_TRAILOR = ".xlsx";
    
}