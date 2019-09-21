package capstone.is4103capstone.finance.budget.service;

import capstone.is4103capstone.entities.finance.Plan;
import capstone.is4103capstone.entities.finance.PlanLineItem;
import capstone.is4103capstone.finance.Repository.PlanLineItemRepository;
import capstone.is4103capstone.finance.Repository.PlanRepository;
import capstone.is4103capstone.finance.budget.model.req.BudgetDataAnalysisReq;
import capstone.is4103capstone.finance.budget.model.res.GetPlanLineItemRes;
import capstone.is4103capstone.finance.budget.model.res.GetPlanLineItemResNotTested;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.ExportToFileService;
import capstone.is4103capstone.general.service.ReadFromFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    private static final String[] cols = {"Merchandise_Code", "Amount", "Currency", "Comment"};

    public GeneralRes readUploadedFile(String filePath){
        try{
            List<List<String>> content = readFromFileService.readFromExcel(filePath);
            List<PlanLineItem> items = budgetLineItemService.convertListToPlanLineItem(content);
            return new GetPlanLineItemRes("Successfully Read the File!", false, items);
        }catch (Exception ex){
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happened when trying to export the file!", true);
        }
    }

    public Object exportToBudgetFile(String filename, String id){
        try{
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

    // ======== TODO ============ //

    public GeneralRes aggregateBudgetLineItem(BudgetDataAnalysisReq budgetDataAnalysisReq){
        return null;
    }

    private List<ArrayList<String>> retrieveContentFromDb(BudgetDataAnalysisReq budgetDataAnalysisReq) throws Exception{
        List<PlanLineItem> items = budgetLineItemService.filterPlanItem(budgetDataAnalysisReq.getCols(), budgetDataAnalysisReq.getColsRestriction());
        return budgetLineItemService.convertPlanLineItemToList(items, budgetDataAnalysisReq.getColsToShow());
    }
}
