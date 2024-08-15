package ru.rdsystems.ScheduleManagement.Common.Helpers;

import java.util.UUID;

public class IDGenerator {
    /**
     * Генерирует UUID без разделителей
     * @return UUID без разделителей в нижнем регистре
     */
    public static String generate() {
        return UUID.randomUUID().toString().replace("-","").toLowerCase();
    }
}
