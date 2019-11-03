package capstone.is4103capstone.supplychain.controller;


import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.service.SupplyChainDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supplychain-dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class SupplyChainDashboardController {
    @Autowired
    SupplyChainDashboardService supplyChainDashboardService;

    @GetMapping("/get-contract-distribution")
    public ResponseEntity<GeneralRes> getContractDistribution() {
        try {
            return ResponseEntity.ok().body(supplyChainDashboardService.getContractDistribution());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(), true));
        }
    }

    @GetMapping("/get-assessment-distribution")
    public ResponseEntity<GeneralRes> getAssessmentDistribution() {
        try {
            return ResponseEntity.ok().body(supplyChainDashboardService.getAssessmentDistribution());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(), true));
        }
    }

    @GetMapping("/get-self-assessment-distribution")
    public ResponseEntity<GeneralRes> getSelfAssessmentDistribution() {
        try {
            return ResponseEntity.ok().body(supplyChainDashboardService.getSelfAssessmentDistribution());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(), true));
        }
    }

    @GetMapping("/get-contracts-by-status/{status}")
    public ResponseEntity<GeneralRes> getContractsByStatus(@PathVariable("status") String status) {
        try {
            return ResponseEntity.ok().body(supplyChainDashboardService.getContractsByStatus(status));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(), true));
        }
    }

    @GetMapping("/get-assessments-by-status/{status}")
    public ResponseEntity<GeneralRes> getAssessmentsByStatus(@PathVariable("status") String status) {
        try {
            return ResponseEntity.ok().body(supplyChainDashboardService.getAssessmentByStatus(status));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(), true));
        }
    }

    @GetMapping("/get-self-assessments-by-status/{status}")
    public ResponseEntity<GeneralRes> getSelfAssessmentsByStatus(@PathVariable("status") String status) {
        try {
            return ResponseEntity.ok().body(supplyChainDashboardService.getSelfAssessmentsByStatus(status));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(), true));
        }
    }

}

