package capstone.is4103capstone.finance.budget.service;

import capstone.is4103capstone.entities.finance.PlanLineItem;
import capstone.is4103capstone.finance.Repository.PlanLineItemRepository;
import capstone.is4103capstone.finance.Repository.PlanRepository;
import capstone.is4103capstone.finance.budget.model.req.ExportBudgetReq;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.ExportToFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BudgetDataAnalysisService {
    private static final Logger logger = LoggerFactory.getLogger(BudgetService.class);
    @Autowired
    PlanLineItemRepository planLineItemRepository;
    @Autowired
    PlanRepository planRepository;
    @Autowired
    ExportToFileService exportToFileService;
    @Autowired
    BudgetLineItemService budgetLineItemService;

    public Object exportToBudgetFile(String filename, ExportBudgetReq exportBudgetReq){
        try{
            if(exportBudgetReq.getColContent() == null || exportBudgetReq.getColContent().size() == 0){
                ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
                retrieveContentFromDb(exportBudgetReq.getCols(), exportBudgetReq.getColsRestriction(), content);
                return exportToFileService.exportToExcel(filename, exportBudgetReq.getCols(), content);
            }else{
                return exportToFileService.exportToExcel(filename, exportBudgetReq.getCols(), exportBudgetReq.getColContent());
            }
        } catch(Exception ex){
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happened when trying to export the file!", true);
        }
    }

    private void retrieveContentFromDb(String[] cols, List<ArrayList<String>> restrictions, List<ArrayList<String>> content) throws Exception{
        List<PlanLineItem> items = budgetLineItemService.filterPlanItem(cols, restrictions);
        // [TODO] add in items => content
    }
}
