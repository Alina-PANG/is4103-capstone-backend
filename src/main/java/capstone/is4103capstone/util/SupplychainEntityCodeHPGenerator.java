package capstone.is4103capstone.util;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.supplyChain.ChildContract;
import capstone.is4103capstone.supplychain.service.ContractService;
import capstone.is4103capstone.util.exception.RepositoryEntityMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public class SupplychainEntityCodeHPGenerator {
    private static final Logger logger = LoggerFactory.getLogger(SupplychainEntityCodeHPGenerator.class);
    private final String VENDOR_TEMPLATE = "Vendor-%1$s-%2$s";
    private final String CONTRACT_TEMPLATE = "Contract-%1$s";
    private final String CHILD_CONTRACT_TEMPLATE = "ChildContract-%1$s";
    private final String OUTSOURCING_TEMPLATE = "Outsourcing-%1$s";
    private final String OUTSOURCING_SELF_ASSESSMENT_TEMPLATE = "OutsourcingSelfAssessment-%1$s";
    private final String OUTSOURCING_SELF_ASSESSMENT_QUESTION_TEMPLATE = "OutsourcingSelfAssessmentQuestion-%1$s";
    private final String OUTSOURCING_ASS_FORM = "OutsourcingAssessment-%1$s";
    private final String OUTSOURCING_ASS_FORM_SECTION = "OutsourcingAssessmentSection-%1$s";
    private final String OUTSOURCING_ASS_FORM_LINE = "OutsourcingAssessmentLine-%1$s";

    public String generateCode(JpaRepository repo, DBEntityTemplate entity) throws Exception {
        Optional<DBEntityTemplate> entityOptional = repo.findById(entity.getId());
        String code = "";
        if (entityOptional.isPresent()) {
            entity = entityOptional.get();

            if (entity.getSeqNo() == null) {
                entity.setSeqNo(new Long(repo.findAll().size()));
            }
            String seqNoStr = String.valueOf(entity.getSeqNo());

            String entityClassName = entity.getClass().getSimpleName();
            String objectName;
            try {
                objectName = entity.getObjectName().replaceAll("\\s+", "_");
            } catch (NullPointerException ne) {
                objectName = "";
            }
            try {
                switch (entityClassName) {
                    case "Vendor":
                        code = String.format(VENDOR_TEMPLATE, seqNoStr, objectName);
                        break;
                    case "Contract":
                        code = String.format(CONTRACT_TEMPLATE, seqNoStr);
                        break;
                    case "ChildContract":
                        code = String.format(CHILD_CONTRACT_TEMPLATE, seqNoStr);
                        break;
                    case "Outsourcing":
                        code = String.format(OUTSOURCING_TEMPLATE, seqNoStr);
                        break;
                    case "OutsourcingSelfAssessment":
                        code = String.format(OUTSOURCING_SELF_ASSESSMENT_TEMPLATE, seqNoStr);
                        break;
                    case "OutsourcingSelfAssessmentQuestion":
                        code = String.format(OUTSOURCING_SELF_ASSESSMENT_QUESTION_TEMPLATE, seqNoStr);
                        break;
                    case "OutsourcingAssessment":
                        code = String.format(OUTSOURCING_ASS_FORM, seqNoStr);
                        break;
                    case "OutsourcingAssessmentSection":
                        code = String.format(OUTSOURCING_ASS_FORM_SECTION, seqNoStr);
                        break;
                    case "OutsourcingAssessmentLine":
                        code = String.format(OUTSOURCING_ASS_FORM_LINE, seqNoStr);
                        break;
                    default:
                        System.out.println("Not found");
                }
            } catch (Exception e) {
                throw new Exception("entity have null name");
            }
            code = code.toUpperCase();

            return code;
        } else {
            throw new RepositoryEntityMismatchException("Repository class given does not match the entity type.");
        }
    }
}

