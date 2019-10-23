package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.model.res.AuditTrailRes;
import capstone.is4103capstone.admin.model.res.GenericObjectRes;
import capstone.is4103capstone.admin.service.AuditTrailActivityService;
import capstone.is4103capstone.general.StandardStatusMessages;
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
    public ResponseEntity<AuditTrailRes> getAllAuditTrailRecords() {
        try {
            WriteAuditTrail.createBasicAuditRecord();
            return ResponseEntity
                    .ok()
                    .body(new AuditTrailRes(null, false, Optional.of(atas.entityToDto(atas.getAllAuditTrailRecords()))));
        } catch (Exception ex) {
            return ResponseEntity
                    .badRequest()
                    .body(new AuditTrailRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<GenericObjectRes> getAllAuditTrailRecordsWithUsername() {
        try {
            WriteAuditTrail.createBasicAuditRecord();
            return ResponseEntity
                    .ok()
                    .body(new GenericObjectRes(StandardStatusMessages.OPERATION_COMPLETED_SUCCESSFULLY, false, Optional.of(atas.getAllAuditTrailWithUsername())));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new GenericObjectRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @GetMapping("/byActivity/{activityName}")
    public ResponseEntity<AuditTrailRes> getAuditTrailActivitiesByActivityName(@PathVariable(name = "activityName") String activityName) {
        try {
            WriteAuditTrail.createBasicAuditRecord();
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
    public ResponseEntity<AuditTrailRes> getAuditTrailActivitiesByUsername(@PathVariable(name = "userName") String userName) {
        try {
            WriteAuditTrail.createBasicAuditRecord();
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
    public ResponseEntity<AuditTrailRes> getAuditTrailActivitiesByUserUuid(@PathVariable(name = "userUuid") String userUuid) {
        try {
            WriteAuditTrail.createBasicAuditRecord();
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
