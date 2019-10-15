package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.finance.budget.model.res.BudgetLineItemModel;
import capstone.is4103capstone.finance.budget.model.res.GetUploadedFileDataRes;
import capstone.is4103capstone.finance.budget.service.BudgetDataAnalysisService;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.ReadFromFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class SeatAllocationExcelService {

    private static final Logger logger = LoggerFactory.getLogger(SeatAllocationExcelService.class);

    @Autowired
    private ReadFromFileService readFromFileService;
    @Autowired
    private EmployeeService employeeService;

    public void readUploadedFile(String filePath){
        try{
            List<List<String>> content = readFromFileService.readFromExcel(filePath);
            // TODO: convert employee IDs
            // return new GetUploadedFileDataRes("Successfully Read the File!", false, items);
        }catch (Exception ex){
            ex.printStackTrace();
            // return new GeneralRes("Error happened when trying to export the file! " + ex.getMessage(), true);
        }
    }

    public void readUploadedFile(Path filePath){
        try{
            logger.info("Reading the Uploaded File...");
            List<List<String>> content = readFromFileService.readFromExcel(filePath.toAbsolutePath().toString());
            // TODO: convert employee IDs
            // return new GetUploadedFileDataRes("Successfully Read the File!", false, items);
        }catch (Exception ex){
            ex.printStackTrace();
            // return new GeneralRes("Error happened when trying to export the file! "+ex.getMessage(), true);
        }
    }

}
