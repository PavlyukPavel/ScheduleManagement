package ru.rdsystems.ScheduleManagement.Common.Helpers;

public class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     *
     * @param s - строка для проверки
     * @return Проверяет что строка не null и не пустая
     */

    public static boolean isValid(String s) {
        return (s != null && !s.trim().isEmpty());
    }
}
