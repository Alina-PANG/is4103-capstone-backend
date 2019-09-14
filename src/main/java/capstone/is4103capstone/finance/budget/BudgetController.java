package capstone.is4103capstone.finance.budget;

import capstone.is4103capstone._demoModule.controller.FileController;
import capstone.is4103capstone._demoModule.service.FileStorageService;
import capstone.is4103capstone.finance.budget.model.req.ApproveBudgetReq;
import capstone.is4103capstone.finance.budget.model.req.CreateBudgetReq;
import capstone.is4103capstone.finance.budget.model.req.UpdateBudgetReq;
import capstone.is4103capstone.finance.budget.model.res.GetBudgetListRes;
import capstone.is4103capstone.finance.budget.model.res.GetBudgetRes;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {
    private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);

    @Autowired
    private BudgetService budgetService;

    @PostMapping("/createBudget")
    public GeneralRes createBudget(CreateBudgetReq createBudgetReq) {
        if(Authentication.authenticateUser(createBudgetReq.getUsername()))
            return budgetService.createBudget(createBudgetReq);
        else
            return new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true);
    }

    @PostMapping("/updateBudget")
    public GeneralRes updateBudget(UpdateBudgetReq updateBudgetReq) {
        if(Authentication.authenticateUser(updateBudgetReq.getUsername()))
            return budgetService.updateBudget(updateBudgetReq);
        else
            return new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true);
    }

    @GetMapping("/getBudget")
    public GeneralRes getBudget(@RequestParam(name="id", required=true) String id, @RequestParam(name="username", required=true) String username){
        if(Authentication.authenticateUser(username))
            return budgetService.getBudget(id);
        else
            return new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true);
    }

    @GetMapping("/getBudgetList")
    public GeneralRes getPendingBudgetList(@RequestParam(name="username", required=true) String username, @RequestParam(name="type", required=true) String type, @RequestParam(name="status", required=true) String status){
        if(Authentication.authenticateUser(username))
            return budgetService.getBudgetList(username, type, status);
        else
            return new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true);
    }

    @PostMapping("/approveBudgetReq")
    public GeneralRes approveBudget(ApproveBudgetReq approveBudgetReq){
        if(Authentication.authenticateUser(approveBudgetReq.getUsername()))
            return budgetService.approveBudget(approveBudgetReq);
        else
            return new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true);
    }
}
