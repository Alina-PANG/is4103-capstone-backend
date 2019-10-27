package capstone.is4103capstone.supplychain.controller;

import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.req.CreateOutsourcingReq;
import capstone.is4103capstone.supplychain.service.OutsourcingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/outsourcing")
@CrossOrigin(origins = "http://localhost:3000")
public class OutsourcingController {
    private static final Logger logger = LoggerFactory.getLogger(OutsourcingController.class);

    @Autowired
    OutsourcingService outsourcingService;

    @PostMapping("/create-outsourcing")
    public ResponseEntity<GeneralRes> createOutsourcing(@RequestBody CreateOutsourcingReq createOutsourcingReq) {
        try {
            return ResponseEntity.ok().body(outsourcingService.createOutsourcing(createOutsourcingReq));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(), true));
        }
    }

    @GetMapping("/get-outsourcing/{id}")
    public ResponseEntity<GeneralRes> getOutsourcing(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok().body(outsourcingService.getOutsourcing(id));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(), true));
        }
    }

    @PostMapping("/update-outsourcing/{id}")
    public ResponseEntity<GeneralRes> updateOutsourcing(@RequestBody CreateOutsourcingReq updateOutsourcingReq, @PathVariable("id") String id) {
        try {
            return ResponseEntity.ok().body(outsourcingService.updateOutsourcing(updateOutsourcingReq, id));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(), true));
        }
    }

    @GetMapping("/get-all-outsourcings")
    public ResponseEntity<GeneralRes> getAllOutsourcings() {
        try {
            return ResponseEntity.ok().body(outsourcingService.getAllOutsourcings());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(), true));
        }
    }
}