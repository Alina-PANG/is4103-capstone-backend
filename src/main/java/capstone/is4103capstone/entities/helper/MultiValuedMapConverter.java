package capstone.is4103capstone.entities.helper;

import capstone.is4103capstone.util.enums.OperationTypeEnum;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;

/*
    READ, ryan
    READ, yingshi
    READ, all_users
   "READ,ryan|READ,yingshi|READ,all_users"
 */
public class MultiValuedMapConverter implements AttributeConverter<MultiValuedMap<OperationTypeEnum, String>, String> {
    private final String INNER_SEP = ",";
    private final String OUTER_SEP = "|";

    @Override
    public String convertToDatabaseColumn(MultiValuedMap<OperationTypeEnum, String> map) {
        ArrayList<String> pairs = new ArrayList<>();

        MapIterator<OperationTypeEnum,String> iterator = map.mapIterator();
        while (iterator.hasNext()){
            String permissionType = iterator.next().toString();
            String sid = iterator.getValue();
            pairs.add(permissionType+INNER_SEP+sid);
        }

        return String.join(OUTER_SEP,pairs);
    }

    @Override
    public MultiValuedMap<OperationTypeEnum, String> convertToEntityAttribute(String dbData) {
        MultiValuedMap<OperationTypeEnum,String> map = new HashSetValuedHashMap<>();
        if (dbData.length() == 0){
            return map;
        }

        String[] pairs;
        if (dbData.contains(OUTER_SEP)){
            pairs = dbData.split(OUTER_SEP);
        }else{
            pairs = new String[1];
            pairs[0] = dbData;
        }

        for (int i=0;i<pairs.length;i++){
            String[] permission_sid = pairs[i].split(INNER_SEP);
            String permission = permission_sid[0];
            String sid = permission_sid[1];
            map.put(OperationTypeEnum.valueOf(permission),sid);
        }

        return map;
    }
}
