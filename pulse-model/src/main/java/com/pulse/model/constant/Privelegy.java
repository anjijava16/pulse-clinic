package com.pulse.model.constant;

public enum Privelegy {

    None("", Byte.MAX_VALUE, false, false),
    Organisation("Организации", Byte.MAX_VALUE, false, false),
    Statistic("Статистика", Byte.MAX_VALUE, false, true),
    Admin("Администратор", (byte) 0, false, true),
    Registratur("Регистратура", (byte) 1, false, true),
    Urology("Урология", (byte) 2, true, true),
    Ginecology("Гинекология", (byte) 3, true, true),
    Ultrasound("УЗИ", (byte) 4, true, true),
    Laboratory("Лаборатория", (byte) 5, true, true),
    Stationary("Стационар", (byte) 6, true, true),
    Director("Директор", (byte) 7, false, true),
    MainDoctor("Глав.врач", (byte) 8, false, true),
    HeadNurse("Старшая мед.сестра", (byte) 9, false, true),
    HeadMaleNurse("Старший мед.брат", (byte) 10, false, true),
    BookKeeping("Бухгалтерия", (byte) 11, false, true),
    Hirurgiya("Хирургия", (byte) 12, true, true),
    Okulist("Окулист", (byte) 13, true, true),
    Fizio("Физиотерапия", (byte) 14, true, true),
    Terapevt("Терапевт", (byte) 15, true, true),
    Endokrinolog("Эндокринолог", (byte) 16, true, true),
    Nevropatolog("Невропатолог", (byte) 17, true, true),
    Vertebrolog("Вертебролог", (byte) 18, true, true),
    TicketWindow("Касса", (byte) 19, false, true),
    PatientRecord("Анкеты", (byte) 20, false, false),
    MagneticResonanceImaging("МРТ", (byte) 21, true, true),
    PatientAppointment("Назначения", (byte) 22, false, false),
    Journal("Журналы", (byte) 23, false, false),
    NextVisit("Приди проверься", (byte) 24, false, false),
    PatientRoom("Палаты", (byte) 25, false, false);

    private Privelegy(String name, byte id, boolean isDepartment, boolean showAsPrivelegy) {
        this.name = name;
        this.id = id;
        this.isDepartment = isDepartment;
        this.showAsPrivelegy = showAsPrivelegy;
    }

    private final String name;
    private final byte id;
    private final boolean isDepartment;
    private final boolean showAsPrivelegy;

    public boolean isShowAsPrivelegy() {
        return showAsPrivelegy;
    }

    public String getName() {
        return this.name;
    }

    public boolean isDepartment() {
        return this.isDepartment;
    }

    public byte getId() {
        return this.id;
    }

    public static Privelegy findById(int id) {
        for (Privelegy p : values()) {
            if (p.getId() == id) {
                return p;
            }
        }

        return null;
    }

    public static Privelegy findByName(String name) {
        for (Privelegy p : values()) {
            if (p.getName().equals(name)) {
                return p;
            }
        }

        return null;
    }
}
