package capstone.is4103capstone.finance.finPurchaseOrder;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.util.SupplychainEntityCodeHPGenerator;
import capstone.is4103capstone.util.exception.RepositoryEntityMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public class POEntityCodeHPGenerator {
    private static final Logger logger = LoggerFactory.getLogger(POEntityCodeHPGenerator.class);
    private final String PURCHASE_ORDER = "PurchaseOrder-%1$s";
    private final String SOA = "SOA-%1$s";
    private final String INVOICE = "Invoice-%1$s"; // use soa seq no

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

                    case "PurchaseOrder":
                        code = String.format(PURCHASE_ORDER, seqNoStr);
                        break;
                    case "StatementOfAcctLineItem":
                        code = String.format(SOA, seqNoStr);
                        break;
                    case "Invoice":
                        code = String.format(INVOICE, seqNoStr);
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
