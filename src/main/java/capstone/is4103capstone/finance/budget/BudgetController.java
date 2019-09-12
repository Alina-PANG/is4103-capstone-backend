package capstone.is4103capstone.finance.budget;

import capstone.is4103capstone._demoModule.controller.FileController;
import capstone.is4103capstone._demoModule.service.FileStorageService;
import capstone.is4103capstone.finance.budget.model.req.CreateBudgetReq;
import capstone.is4103capstone.general.model.GeneralRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private BudgetService budgetService;

    @GetMapping("/saveDraft")
    public GeneralRes saveDraft(CreateBudgetReq createBudgetReq) {
        return budgetService.createBudget(createBudgetReq);
    }

    @GetMapping("/createBudget")
    public GeneralRes createBudget(CreateBudgetReq createBudgetReq) {
        return budgetService.createBudget(createBudgetReq);
    }

    @GetMapping("/updateBudget")
    public GeneralRes updateBudget(CreateBudgetReq createBudgetReq) {
        return budgetService.createBudget(createBudgetReq);
    }
}
