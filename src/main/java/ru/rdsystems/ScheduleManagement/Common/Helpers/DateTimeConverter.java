package ru.rdsystems.ScheduleManagement.Common.Helpers;

import java.time.*;

public class DateTimeConverter {
    /**
     * Функция конвертация даты и времени для отображения в JSON ответах
     * @param dt - дата и время в формате БД (TIMESTAMPTZ)
     * @return Дата и время в локальном часовом поясе
     */
    public static LocalDateTime toLocalDateTime(OffsetDateTime dt) {
        return dt.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Функция конвертации времени из LocalTime в OffsetTime
     * @param t - время в формате LocalTime
     * @return Время в формате OffsetTime в зоне UTC
     */
    public static OffsetTime toOffsetTime(LocalTime t) {
        return  OffsetTime.of(t, ZoneOffset.UTC);
    }
}
