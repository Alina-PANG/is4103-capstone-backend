package capstone.is4103capstone.supplychain.outsourcing.assessmentForm.controller;

import capstone.is4103capstone.finance.budget.controller.BudgetController;
import capstone.is4103capstone.finance.budget.model.req.ApproveBudgetReq;
import capstone.is4103capstone.finance.budget.model.req.CreateBudgetReq;
import capstone.is4103capstone.finance.budget.service.BudgetService;
import capstone.is4103capstone.finance.budget.service.PlansComparisonService;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.req.CreateAssessmentFromReq;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.req.CreateSchedulerReq;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.service.AssessmentFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/assessmentForm")
@CrossOrigin(origins = "http://localhost:3000")
public class AssessmentFormController {
    private static final Logger logger = LoggerFactory.getLogger(AssessmentFormController.class);

    @Autowired
    private AssessmentFormService assessmentFormService;


    @PostMapping("/create/{isTemplate}")
    public ResponseEntity<GeneralRes> createAssessmentForm(@PathVariable("isTemplate") int isTemplate,@RequestBody CreateAssessmentFromReq createAssessmentFromReq) {
        logger.info("*************** Creating Assessment Form *****************");
        if(Authentication.authenticateUser(createAssessmentFromReq.getUsername())) {
            return assessmentFormService.createForm(isTemplate == 1, createAssessmentFromReq, null);
        }
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @PostMapping("/update/{id}/{isTemplate}")
    public ResponseEntity<GeneralRes> updateForm(@PathVariable("isTemplate") int isTemplate, @RequestBody CreateAssessmentFromReq createAssessmentFromReq,  @PathVariable("id") String id) {
        logger.info("*************** Updating Assessment Form *****************");
        if(Authentication.authenticateUser(createAssessmentFromReq.getUsername())){
            return assessmentFormService.createForm(isTemplate == 1, createAssessmentFromReq, id);
        }
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @GetMapping("/getDetails/{id}")
    public ResponseEntity<GeneralRes> getForm(@PathVariable("id") String id, @RequestParam(name="username", required=true) String username){
        logger.info("*************** Getting Outsourcing Assessment Form Details "+id+"*****************");
        if(Authentication.authenticateUser(username))
            return assessmentFormService.getForm(id);
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @GetMapping("/getTemplate")
    public ResponseEntity<GeneralRes> getTemplateForm(@RequestParam(name="username", required=true) String username){
        logger.info("*************** Getting Outsourcing Assessment Form Template *****************");
        if(Authentication.authenticateUser(username))
            return assessmentFormService.getTemplateForm();
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @PostMapping("/approve/{level}/{id}")
    public ResponseEntity<GeneralRes> approve(@PathVariable("id") String id, @PathVariable("level") int level, ApproveBudgetReq approveBudgetReq){
        logger.info("*************** Approving Outsourcing Assessment Form "+id+" *****************");
        if(Authentication.authenticateUser(approveBudgetReq.getUsername()))
            return assessmentFormService.approve(id, approveBudgetReq.getApproved(), approveBudgetReq.getUsername(), level);
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @PostMapping("/notification/{id}")
    public ResponseEntity<GeneralRes> setTimer(@PathVariable("id") String id, @RequestParam(name="username", required=true) String username, Date date){
        logger.info("*************** Creating Outsourcing Assessment Form Notification *****************");
        if(Authentication.authenticateUser(username))
            return assessmentFormService.setTimer(date, id);
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }
}
