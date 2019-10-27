package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.finance.budget.model.res.GetUploadedFileDataRes;
import capstone.is4103capstone.general.service.ReadFromFileService;
import capstone.is4103capstone.seat.model.EmployeeGroupModelWithValidityChecking;
import capstone.is4103capstone.seat.model.EmployeeModelWithValidityChecking;
import capstone.is4103capstone.util.exception.EmployeeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeatAllocationExcelService {

    private static final Logger logger = LoggerFactory.getLogger(SeatAllocationExcelService.class);

    @Autowired
    private ReadFromFileService readFromFileService;
    @Autowired
    private EmployeeService employeeService;

    public EmployeeGroupModelWithValidityChecking convertUploadedEmployeeIdExcelFile(Path filePath){
        logger.info("Reading the Uploaded File...");
        List<List<String>> content = readFromFileService.readFromExcel(filePath.toAbsolutePath().toString());
        List<String> employeeIds = convertExcelRowsToEmployeeIdString(content);
        EmployeeGroupModelWithValidityChecking employeeGroupModelWithValidityChecking = checkValidityAndConvertEmployeeIdStringToEmployeeModel(employeeIds);
        return employeeGroupModelWithValidityChecking;
    }


    private List<String> convertExcelRowsToEmployeeIdString(List<List<String>> content) {

        List<String> employeeIds = new ArrayList<>();
        for(int i = 1; i < content.size(); i++) {
            List<String> row = content.get(i);
            String employeeId = row.get(0);
            if (employeeId != null && employeeId.trim().length() != 0) {
                employeeId = employeeId.trim();
                employeeIds.add(employeeId);
            }
        }
        return employeeIds;
    }


    // Pre-condition: the each employee ID string is processed in advance to be ensured that it will not be a null/empty string.
    private EmployeeGroupModelWithValidityChecking checkValidityAndConvertEmployeeIdStringToEmployeeModel(List<String> employeeIds) {
        EmployeeGroupModelWithValidityChecking employeeGroupModelWithValidityChecking = new EmployeeGroupModelWithValidityChecking();
        List<EmployeeModelWithValidityChecking> employeeModelsWithValidityChecking = new ArrayList<>();

        for (String employeeId :
                employeeIds) {
            EmployeeModelWithValidityChecking employeeModelWithValidityChecking = new EmployeeModelWithValidityChecking();
            try {
                Employee employee = employeeService.retrieveEmployeeById(employeeId);
                employeeModelWithValidityChecking.setId(employee.getId());
                employeeModelWithValidityChecking.setName(employee.getFullName());
                employeeModelWithValidityChecking.setHasValidEmployeeId(true);
            } catch (EmployeeNotFoundException ex) {
                employeeModelWithValidityChecking.setId(employeeId);
                employeeModelWithValidityChecking.setHasValidEmployeeId(false);
            }
            employeeModelsWithValidityChecking.add(employeeModelWithValidityChecking);
        }

        employeeGroupModelWithValidityChecking.setEmployees(employeeModelsWithValidityChecking);
        return employeeGroupModelWithValidityChecking;
    }

}
