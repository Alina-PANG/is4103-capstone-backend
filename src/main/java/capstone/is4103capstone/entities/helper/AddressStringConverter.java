package capstone.is4103capstone.entities.helper;

import capstone.is4103capstone.entities.Address;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class AddressStringConverter implements AttributeConverter<Address, String> {
    private final String sep = ";";
    private final String[] compoenentSequence = {"Address"};
    @Override
    public String convertToDatabaseColumn(Address address) {
        String fullAddress = "";
        ArrayList<String> addressComponents = new ArrayList<>();
        addressComponents.add(address.getAddressLine1());
        addressComponents.add(address.getAdddressLine2());
        addressComponents.add(address.getCity());
        addressComponents.add(address.getCountryCode());
        addressComponents.add(address.getRegionCode());
        addressComponents.add(address.getPostalCode());
        fullAddress = String.join(sep,addressComponents);
        return fullAddress;
    }

    @Override
    public Address convertToEntityAttribute(String s) {
        List<String> componentsList = Arrays.asList(s.split(sep));
        for (String ss: componentsList){
            if (ss.isEmpty()){
                componentsList.remove(ss);
            }
        }
        String[] components = componentsList.toArray(new String[componentsList.size()]);
        //may contains error. must constraint, user cannot put "#" in the address line
        Address a = new Address();
        a.setAddressLine1(components[0]);
        a.setAdddressLine2(components[1]);
        a.setCity(components[2]);
        a.setCountryCode(components[3]);
        a.setRegionCode(components[4]);
        a.setPostalCode(components[5]);
        return null;
    }
}
