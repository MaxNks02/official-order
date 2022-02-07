package uz.davrbank.officialorder.entity.lov;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum OffState {
    UNDEFINED("Неопределен"),
    ENTERED("Введен"),
    VALIDATED("Утверждён"),
    REJECTED("Отклонён"),
    DELETED("Удален");

    public final String value;


    public static OffState create(String value) {
        for (OffState offState : values()) {
            if (value.equals(offState.getValue())) {
                return offState;
            }
        }
        return null;
    }


    OffState(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Converter(autoApply = true)
    public static class StateConverter implements AttributeConverter<OffState, String> {
        @Override
        public String convertToDatabaseColumn(OffState attribute) {
            return attribute.getValue();
        }

        @Override
        public OffState convertToEntityAttribute(String dbData) {
            return create(dbData);
        }
    }
}
