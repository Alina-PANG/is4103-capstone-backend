package capstone.is4103capstone.supplychain.outsourcing.assessmentForm.controller;

import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.general.AuthenticationTools;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.req.CreateResponseReq;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model.req.CreateTemplateReq;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.service.AssessmentFormService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/assessmentForm")
@CrossOrigin(origins = "http://localhost:3000")
public class AssessmentFormController {
    @Autowired
    EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(AssessmentFormController.class);

    @Autowired
    private AssessmentFormService assessmentFormService;


    @PostMapping("/createResponse")
    public ResponseEntity<GeneralRes> createResponse(@RequestBody CreateResponseReq createResponseReq) {
        logger.info("*************** Creating Assessment Form *****************");
        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername())) {
            return assessmentFormService.createResponseForm(createResponseReq, null, employeeService.getCurrentLoginUsername());
        }
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @PostMapping("/updateTemplate")
    public ResponseEntity<GeneralRes> updateForm(@RequestBody CreateTemplateReq createTemplateReq) {
        logger.info("*************** Updating Assessment Form *****************");
        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername())){
            return assessmentFormService.createTemplateForm( createTemplateReq, employeeService.getCurrentLoginUsername());
        }
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<GeneralRes> updateForm(@RequestBody CreateResponseReq createTemplateReq, @PathVariable("id") String id) {
        logger.info("*************** Updating Assessment Form *****************");
        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername())){
            return assessmentFormService.createResponseForm(createTemplateReq,id, employeeService.getCurrentLoginUsername());
        }
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @GetMapping("/getDetails/{id}")
    public ResponseEntity<GeneralRes> getForm(@PathVariable("id") String id){
        logger.info("*************** Getting Outsourcing Assessment Form Details "+id+"*****************");
        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername()))
            return assessmentFormService.getForm(id);
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @GetMapping("/getTemplate")
    public ResponseEntity<GeneralRes> getTemplateForm(){
        logger.info("*************** Getting Outsourcing Assessment Form Template *****************");
        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername()))
            return assessmentFormService.getTemplateForm();
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @PostMapping("/approve/{id}") // TODO: approve the assessment form
    public ResponseEntity<GeneralRes> approveA(@PathVariable("id") String id, @RequestParam(name="approved", required=true) int approved, @RequestParam(name="type", required=true) int type){
        logger.info("*************** Approving Outsourcing Assessment Form 1st Level"+id+" *****************");
        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername())) {
            return assessmentFormService.approve(id, approved == 1, type == 0, employeeService.getCurrentLoginUsername());
        }
        else{
            logger.info("authentication error");
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }

    @PostMapping("/notification/{id}") // TODO: notification of assessment form
    public ResponseEntity<GeneralRes> setTimer(@PathVariable("id") String id, Date date){
        logger.info("*************** Creating Outsourcing Assessment Form Notification *****************");
        if(AuthenticationTools.authenticateUser(employeeService.getCurrentLoginUsername()))
            return assessmentFormService.setTimer(date, id);
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }
}
