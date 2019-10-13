package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.dto.EmployeeDto;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.SecurityGroup;
import capstone.is4103capstone.util.enums.OperationTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PermissionControllerService {

    public static String GLOBAL_SID_EVERYONE = "S-EVERYONE";
    private static boolean CREATOR_ALWAYS_HAS_ACCESS = true;

    @Autowired
    private EmployeeService employeeService;

    public boolean checkUserPermissions(DBEntityTemplate inputObject, Employee employee, OperationTypeEnum operationType) {

        // make list to store SIDs
        ArrayList<String> sidList = new ArrayList<>();

        // store the user's SID into the list
        sidList.add(employee.getSecurityId());

        // store the user's group's SIDs into the list
        for (SecurityGroup sg : employee.getMemberOfSecurityGroups()) {
            sidList.add(sg.getSecurityId());
        }

        // check the operation type against the sidList
        for (String currentSid : sidList) {
            // if EVERYONE has permissions then return true
            if (inputObject.getPermissionMap().get(operationType).contains(GLOBAL_SID_EVERYONE)) return true;
            // check if the current permission/user pairing exists
            if (inputObject.getPermissionMap().get(operationType).contains(currentSid)) return true;
            // if allowed, then the creator always has access to his object
            if (CREATOR_ALWAYS_HAS_ACCESS) {
                try {
                    // lookup the creator first
                    EmployeeDto employeeDto = employeeService.getEmployeeByUuid(inputObject.getCreatedBy());
                    // check if the creator matches the current presented SID
                    if (employee.getSecurityId().equalsIgnoreCase(employeeDto.getSecurityId().get())) {
                        return true;
                    }
                } catch (Exception ex) {
                    // the user probably doesn't exist anymore, return false.
                    return false;
                }
            }

        }
        return false;
    }
}
