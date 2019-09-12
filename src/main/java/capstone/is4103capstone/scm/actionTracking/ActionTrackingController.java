package capstone.is4103capstone.scm.actionTracking;

import capstone.is4103capstone._demoModule.controller.FileController;
import capstone.is4103capstone.finance.budget.BudgetService;
import capstone.is4103capstone.finance.budget.model.req.CreateBudgetReq;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.scm.actionTracking.model.req.CreateActionTrackingReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/actionTracking")
public class ActionTrackingController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private ActionTrackingService actionTrackingService;

    @PostMapping("/createActionTracking")
    public GeneralRes createActionTracking(CreateActionTrackingReq createActionTrackingReq) {
        if(Authentication.authenticateUser(createActionTrackingReq.getUsername()))
            return actionTrackingService.createActionTracking(createActionTrackingReq);
        else
            return new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true);
    }
}
