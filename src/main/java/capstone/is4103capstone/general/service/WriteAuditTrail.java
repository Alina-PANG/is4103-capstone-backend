package capstone.is4103capstone.general.service;

import capstone.is4103capstone.admin.service.AuditTrailActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriteAuditTrail {
    private static final Logger logger = LoggerFactory.getLogger(WriteAuditTrail.class);

    @Autowired
    AuditTrailActivityService auditService;

    @Autowired
    public void setAudit(AuditTrailActivityService service){
        WriteAuditTrail.audit = service;
    }

    static AuditTrailActivityService audit;
    private static final String AUTO_DETECT_FLAG = "autodetect";

    public static void autoAudit(String username){
        try {
            audit.createNewRecordUsingUsername(AUTO_DETECT_FLAG,username);
        }catch (Exception e){
            logger.error("Audit function error, username given from the internal controller is not correct. ");
        }
    }
    public static void autoAuditRecordByUserId(String uuid){
        try {
            audit.createNewRecordUsingUserUuid(AUTO_DETECT_FLAG,uuid);
        }catch (Exception e){
            logger.error("Audit function error, username given from the internal controller is not correct. ");
        }
    }
    public static void auditActivity(String activityDescription, String username){
        try {
            audit.createNewRecordUsingUsername(activityDescription,username);
        }catch (Exception e){
            logger.error("Audit function error, username given from the internal controller is not correct. ");
        }
    }
    public static void auditActivityRecordByUserId(String activityDescription, String uuid){
        try {
            audit.createNewRecordUsingUserUuid(activityDescription,uuid);
        }catch (Exception e){
            logger.error("Audit function error, username given from the internal controller is not correct. ");
        }
    }
}
