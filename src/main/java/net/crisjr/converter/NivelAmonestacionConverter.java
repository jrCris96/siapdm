package net.crisjr.converter;

import jakarta.persistence.AttributeConverter;
import net.crisjr.enums.NivelAmonestacion;

public class NivelAmonestacionConverter implements AttributeConverter<NivelAmonestacion, String> {
    @Override
    public String convertToDatabaseColumn(NivelAmonestacion attribute) {
        return attribute != null ? attribute.getLabel() : null;
    }

    @Override
    public NivelAmonestacion convertToEntityAttribute(String dbData) {
        return dbData != null ? NivelAmonestacion.fromLabel(dbData) : null;
    }
}
