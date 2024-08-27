package ru.rdsystems.schedule.common.enums;

import jakarta.persistence.AttributeConverter;

public enum SlotTypeEnum {
    LOCAL,
    FROM_HOME,
    UNDEFINED;


    public static class Converter implements AttributeConverter<SlotTypeEnum, String> {

        @Override
        public String convertToDatabaseColumn(SlotTypeEnum slotTypeEnum) {
            return slotTypeEnum.name().replace("_"," ");
        }

        @Override
        public SlotTypeEnum convertToEntityAttribute(String s) {
            return SlotTypeEnum.valueOf(s.replace(" ","_"));
        }
    }
}
