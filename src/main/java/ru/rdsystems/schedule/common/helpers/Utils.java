package ru.rdsystems.schedule.common.helpers;

import java.util.UUID;

public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Проверяет что строка не null и не пустая
     * @param s - строка для проверки
     * @return true - если строка не null и не пустая
     */

    public static boolean isValidStr(String s) {
        return (s != null && !s.trim().isEmpty());
    }

    /**
     * Генерирует ID записи в виде UUID без разделителей
     * @return UUID без разделителей в нижнем регистре
     */
    public static String generateID() {
        return UUID.randomUUID().toString().replace("-","").toLowerCase();
    }

    /**
     * Вывод информации в лог (для целей тестирования)
     * @param x - объект для вывода в лог
     */
    public static void log(Object x) {
        System.out.println(x);
    }
}
