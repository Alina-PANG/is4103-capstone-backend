package capstone.is4103capstone.supplychain.controller;

import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.req.ApproveOutsourcingSelfAssessmentReq;
import capstone.is4103capstone.supplychain.model.req.CreateOutsourcingSelfAssessmentReq;
import capstone.is4103capstone.supplychain.service.OutsourcingSelfAssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/outsourcing-self-assessment")
@CrossOrigin(origins = "http://localhost:3000")
public class OutsourcingSelfAssessmentController {
    private static final Logger logger = LoggerFactory.getLogger(OutsourcingSelfAssessmentController.class);
    @Autowired
    OutsourcingSelfAssessmentService outsourcingSelfAssessmentService;

    @PostMapping("/create-outsourcing-self-assessment")
    public ResponseEntity<GeneralRes> createOutsourcingSelfAssessment(@RequestBody CreateOutsourcingSelfAssessmentReq createOutsourcingSelfAssessmentReq) {
        try {
            return ResponseEntity.ok().body(outsourcingSelfAssessmentService.createOutsourcingSelfAssessment(createOutsourcingSelfAssessmentReq));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(), true));
        }
    }

    @GetMapping("/get-outsourcing-self-assessment/{id}")
    public ResponseEntity<GeneralRes> getOutsourcingSelfAssessment(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok().body(outsourcingSelfAssessmentService.getOutsourcingSelfAssessment(id));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(), true));
        }
    }

    @PostMapping("/update-outsourcing-self-assessment/{id}")
    public ResponseEntity<GeneralRes> updateOutsourcingSelfAssessment(@RequestBody CreateOutsourcingSelfAssessmentReq updateOutsourcingSelfAssessmentReq, @PathVariable("id") String id) {
        try {
            return ResponseEntity.ok().body(outsourcingSelfAssessmentService.updateOutsourcingSelfAssessment(updateOutsourcingSelfAssessmentReq, id));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(), true));
        }
    }

    @GetMapping("/get-all-outsourcing-self-assessments-by-outsourcing-id/{id}")
    public ResponseEntity<GeneralRes> getAllOutsourcingSelfAssessmentsByOutsourcingId(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok().body(outsourcingSelfAssessmentService.getAllOutsourcingSelfAssessmentsByOutsourcingId(id));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(), true));
        }
    }

    @PostMapping("/approve-outsourcing-self-assessment")
    public ResponseEntity approveOutsourcingSelfAssessment(@RequestBody ApproveOutsourcingSelfAssessmentReq approveOutsourcingSelfAssessmentReq){
        try {
            return ResponseEntity.ok().body(outsourcingSelfAssessmentService.approveOutsourcingSelfAssessment(approveOutsourcingSelfAssessmentReq));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(), true));
        }
    }

    @PostMapping("/request-approve/{outsourcingSelfAssessmentId}")
    public ResponseEntity requestApprove(@PathVariable("outsourcingSelfAssessmentId") String outsourcingSelfAssessmentId){
        try {
            return ResponseEntity.
                    ok().
                    body(outsourcingSelfAssessmentService.createApprovalTicket(outsourcingSelfAssessmentId,"Approver please review the contract and related child contracts."));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(), true));
        }
    }
}
