package capstone.is4103capstone.general.service;

import capstone.is4103capstone.admin.service.AuditTrailActivityService;
import capstone.is4103capstone.entities.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class WriteAuditTrail {
    private static final Logger logger = LoggerFactory.getLogger(WriteAuditTrail.class);
    private static final String AUTO_DETECT_FLAG = "autodetect";
    static AuditTrailActivityService audit;
    @Autowired
    AuditTrailActivityService auditService;

    public static void autoAudit(String username) {
        try {
            audit.createNewRecordUsingUsername(AUTO_DETECT_FLAG, username);
        } catch (Exception e) {
            logger.error("Audit function error, username given from the internal controller is not correct. ");
        }
    }

    public static void autoAuditUsingSpringSecurity() throws Exception{
        try {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            Employee user = (Employee) auth.getPrincipal();
//            audit.createNewRecordUsingUsername(AUTO_DETECT_FLAG, user.getUserName());
        } catch (Exception ex) {
            logger.error("Audit function error, username given from the internal controller is not correct. ");

            throw ex;
        }
    }

    public static void autoAuditRecordByUserId(String uuid) {
        try {
            audit.createNewRecordUsingUserUuid(AUTO_DETECT_FLAG, uuid);
        } catch (Exception e) {
            logger.error("Audit function error, username given from the internal controller is not correct. ");
        }
    }

    public static void auditActivity(String activityDescription, String username) {
        try {
            audit.createNewRecordUsingUsername(activityDescription, username);
        } catch (Exception e) {
            logger.error("Audit function error, username given from the internal controller is not correct. ");
        }
    }

    public static void auditActivityRecordByUserId(String activityDescription, String uuid) {
        try {
            audit.createNewRecordUsingUserUuid(activityDescription, uuid);
        } catch (Exception e) {
            logger.error("Audit function error, username given from the internal controller is not correct. ");
        }
    }

    @Autowired
    public void setAudit(AuditTrailActivityService service) {
        WriteAuditTrail.audit = service;
    }
}
