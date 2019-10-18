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
    private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);

    @Autowired
    private AssessmentFormService assessmentFormService;


    @PostMapping("/create/{isTemplate}")
    public ResponseEntity<GeneralRes> createAssessmentForm(@PathVariable("isTemplate") boolean isTemplate,@RequestBody CreateAssessmentFromReq createAssessmentFromReq) {
        if(Authentication.authenticateUser(createAssessmentFromReq.getUsername()))
            return assessmentFormService.createForm(isTemplate, createAssessmentFromReq, null);
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @PostMapping("/update/{id}/{isTemplate}")
    public ResponseEntity<GeneralRes> updateForm(@PathVariable("isTemplate") boolean isTemplate, @RequestBody CreateAssessmentFromReq createAssessmentFromReq,  @PathVariable("id") String id) {
        if(Authentication.authenticateUser(createAssessmentFromReq.getUsername()))
            return assessmentFormService.createForm(isTemplate, createAssessmentFromReq, id);
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @GetMapping("/getDetails/{id}")
    public ResponseEntity<GeneralRes> getForm(@PathVariable("id") String id, @RequestParam(name="username", required=true) String username){
        if(Authentication.authenticateUser(username))
            return assessmentFormService.getForm(id);
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @GetMapping("/getTemplate")
    public ResponseEntity<GeneralRes> getTemplateForm(@RequestParam(name="username", required=true) String username){
        if(Authentication.authenticateUser(username))
            return assessmentFormService.getTemplateForm();
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @PostMapping("/approve/{level}/{id}")
    public ResponseEntity<GeneralRes> approve(@PathVariable("id") String id, @PathVariable("level") int level, @RequestParam(name="username", required=true) String username, @RequestParam(name="approved", required=true) Boolean approved){
        if(Authentication.authenticateUser(username))
            return assessmentFormService.approve(id, approved, username, level);
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @PostMapping("/notification/{id}")
    public ResponseEntity<GeneralRes> setTimer(@PathVariable("id") String id, @RequestParam(name="username", required=true) String username, Date date){
        if(Authentication.authenticateUser(username))
            return assessmentFormService.setTimer(date, id);
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }
}
