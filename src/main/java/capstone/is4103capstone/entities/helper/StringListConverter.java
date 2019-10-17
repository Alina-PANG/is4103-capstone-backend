package capstone.is4103capstone.entities.helper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        if (stringList == null || stringList.size()==0){
            return "";
        }
        return String.join(SPLIT_CHAR,stringList);
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        if (s == null || s.length()==0){
            return new ArrayList<>();
        }
        return Arrays.asList(s.split(SPLIT_CHAR));
    }
}
