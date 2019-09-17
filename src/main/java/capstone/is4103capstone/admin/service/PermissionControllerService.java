package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.SecurityGroup;
import capstone.is4103capstone.entities.enums.PermissionTypeEnum;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PermissionControllerService {

    public boolean checkUserPermissions(DBEntityTemplate inputObject, Employee employee, PermissionTypeEnum operationType) {
        // store the SIDs in a list
        ArrayList<String> sidList = new ArrayList<>();

        // store the user's SID into the list
        sidList.add(employee.getCreatedBy());

        // store the user's group's SIDs into the list
        for (SecurityGroup sg : employee.getMemberOfSecurityGroups()) {
            sidList.add(sg.getSecurityId());
        }

        // check the operation type against the sidList
        for (String currentSid : sidList) {
            if (inputObject.getPermissionMap().get(operationType).contains(currentSid)) {
                return true;
            }
        }
        return false;
    }
}
