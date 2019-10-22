package capstone.is4103capstone.general.service;

import capstone.is4103capstone.admin.service.AuditTrailActivityService;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.general.AuthenticationTools;
import capstone.is4103capstone.util.enums.OperationTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriteAuditTrail {
    private static final Logger logger = LoggerFactory.getLogger(WriteAuditTrail.class);
    private static final String AUTO_DETECT_FLAG = "autodetect";

    static AuditTrailActivityService audit;
    @Autowired
    AuditTrailActivityService auditService;

    // TODO - deprecate this method as all should transition to createBasicAuditRecord()
    public static void autoAudit(String username) {
        try {
            audit.createNewRecordUsingUsername(AUTO_DETECT_FLAG, username);
        } catch (Exception e) {
            logger.error("WriteAuditTrail - autoAudit: Exception thrown. The exception was: " + e.getMessage());
        }
    }

    public static void createBasicAuditRecord() {
        try {
            audit.createNewRecordUsingUserUuid(AUTO_DETECT_FLAG, SecurityTools.getLoggedInUser().getId());
        } catch (Exception ex) {
            logger.error("WriteAuditTrail - autoAuditUsingSpringSecurity: Exception thrown. The exception was: " + ex.getMessage());
        }
    }

    public static void createExtendedAuditRecord(DBEntityTemplate dbObject, OperationTypeEnum operationTypeEnum) {
        try {
            audit.createExtendedRecordUsingUserUuidAndObject(AuthenticationTools.getCurrentUser().getId(), dbObject, operationTypeEnum);
        } catch (Exception ex) {
            logger.error("WriteAuditTrail - createExtendedAuditRecord: Exception thrown. The exception was: " + ex.getMessage());
        }
    }

    @Autowired
    public void setAudit(AuditTrailActivityService service) {
        WriteAuditTrail.audit = service;
    }

}
