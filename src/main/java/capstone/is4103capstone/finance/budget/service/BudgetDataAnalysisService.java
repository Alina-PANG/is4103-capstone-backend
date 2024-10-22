package capstone.is4103capstone.finance.budget.service;

import capstone.is4103capstone.entities.finance.Plan;
import capstone.is4103capstone.entities.finance.PlanLineItem;
import capstone.is4103capstone.finance.Repository.PlanLineItemRepository;
import capstone.is4103capstone.finance.Repository.PlanRepository;
import capstone.is4103capstone.finance.budget.model.req.BudgetDataAnalysisReq;
import capstone.is4103capstone.finance.budget.model.req.CreateBudgetReq;
import capstone.is4103capstone.finance.budget.model.res.BudgetLineItemModel;
import capstone.is4103capstone.finance.budget.model.res.GetPlanLineItemRes;
import capstone.is4103capstone.finance.budget.model.res.GetPlanLineItemResNotTested;
import capstone.is4103capstone.finance.budget.model.res.GetUploadedFileDataRes;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.ExportToFileService;
import capstone.is4103capstone.general.service.ReadFromFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetDataAnalysisService {
    private static final Logger logger = LoggerFactory.getLogger(BudgetDataAnalysisService.class);
    @Autowired
    PlanLineItemRepository planLineItemRepository;
    @Autowired
    PlanRepository planRepository;
    @Autowired
    ExportToFileService exportToFileService;
    @Autowired
    BudgetLineItemService budgetLineItemService;
    @Autowired
    ReadFromFileService readFromFileService;
    @Autowired
    BudgetService budgetService;


    private static final String[] cols = {"Category, Sub1, Sub2, service_Code", "Amount", "Currency", "Comment"};

    public GeneralRes readUploadedFile(String filePath){
        try{
            List<List<String>> content = readFromFileService.readFromExcel(filePath);
            List<BudgetLineItemModel> items = budgetLineItemService.convertListToPlanLineItem(content);
            return new GetUploadedFileDataRes("Successfully Read the File!", false, items);
        }catch (Exception ex){
            ex.printStackTrace();
            return new GeneralRes("Error happened when trying to export the file! " + ex.getMessage(), true);
        }
    }

    public GeneralRes readUploadedFile(Path filePath){
        try{
            logger.info("Reading the Uploaded File...");
            List<List<String>> content = readFromFileService.readFromExcel(filePath.toAbsolutePath().toString());
            List<BudgetLineItemModel> items = budgetLineItemService.convertListToPlanLineItem(content);
            return new GetUploadedFileDataRes("Successfully Read the File!", false, items);
        }catch (Exception ex){
            ex.printStackTrace();
            return new GeneralRes("Error happened when trying to export the file! "+ex.getMessage(), true);
        }
    }


    public Object exportToBudgetFile(String filename, String id){
        try{
            logger.info("Exporting the Budget File...");
            List<ArrayList<String>> content = retrieveContentFromDb(id);
            return exportToFileService.exportToExcel(filename, cols, content);
        } catch(Exception ex){
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happened when trying to export the file!", true);
        }
    }

    private List<ArrayList<String>> retrieveContentFromDb(String id) throws  Exception{
        Plan p = planRepository.getOne(id);
        List<PlanLineItem> planLineItems = p.getLineItems();
        return budgetLineItemService.convertPlanLineItemToList(planLineItems);
    }
}
