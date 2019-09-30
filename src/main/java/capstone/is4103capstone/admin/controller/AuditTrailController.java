package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.controller.model.res.AuditTrailRes;
import capstone.is4103capstone.admin.service.AuditTrailActivityService;
import capstone.is4103capstone.general.service.WriteAuditTrail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/audit")
public class AuditTrailController {

    @Autowired
    AuditTrailActivityService atas;

    @GetMapping
    public ResponseEntity<AuditTrailRes> getAllAuditTrailRecords(@RequestHeader(name = "Authorization", required = false) String headerUsername) {
        try {
            WriteAuditTrail.autoAudit(headerUsername);
            return ResponseEntity
                    .ok()
                    .body(new AuditTrailRes(null, false, Optional.of(atas.entityToDto(atas.getAllAuditTrailRecords()))));
        } catch (Exception ex) {
            return ResponseEntity
                    .badRequest()
                    .body(new AuditTrailRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @GetMapping("/byActivity/{activityName}")
    public ResponseEntity<AuditTrailRes> getAuditTrailActivitiesByActivityName(@PathVariable(name = "activityName") String activityName, @RequestHeader(name = "Authorization", required = false) String headerUsername) {
        try {
            WriteAuditTrail.autoAudit(headerUsername);
            return ResponseEntity
                    .ok()
                    .body(new AuditTrailRes(null, false, Optional.of(atas.entityToDto(atas.getAuditTrailRecordsByActivity(activityName)))));
        } catch (Exception ex) {
            return ResponseEntity
                    .badRequest()
                    .body(new AuditTrailRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @GetMapping("/byUsername/{userName}")
    public ResponseEntity<AuditTrailRes> getAuditTrailActivitiesByUsername(@PathVariable(name = "userName") String userName, @RequestHeader(name = "Authorization", required = false) String headerUsername) {
        try {
            WriteAuditTrail.autoAudit(headerUsername);
            return ResponseEntity
                    .ok()
                    .body(new AuditTrailRes(null, false, Optional.of(atas.entityToDto(atas.getAuditTrailRecordsByUsername(userName)))));
        } catch (Exception ex) {
            return ResponseEntity
                    .badRequest()
                    .body(new AuditTrailRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @GetMapping("/byUserUuid/{userUuid}")
    public ResponseEntity<AuditTrailRes> getAuditTrailActivitiesByUserUuid(@PathVariable(name = "userUuid") String userUuid, @RequestHeader(name = "Authorization", required = false) String headerUsername) {
        try {
            WriteAuditTrail.autoAudit(headerUsername);
            return ResponseEntity
                    .ok()
                    .body(new AuditTrailRes(null, false, Optional.of(atas.entityToDto(atas.getAuditTrailRecordsByUserUuid(userUuid)))));
        } catch (Exception ex) {
            return ResponseEntity
                    .badRequest()
                    .body(new AuditTrailRes(ex.getMessage(), true, Optional.empty()));
        }
    }
}
