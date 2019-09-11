package capstone.is4103capstone.entities.helper;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.util.UUID;

@Convert
public class UuidStringConverter implements AttributeConverter<UUID,String> {
    @Override
    public String convertToDatabaseColumn(UUID uuid) {
        return uuid.toString();
    }

    @Override
    public UUID convertToEntityAttribute(String s) {
        return null;
    }
}
