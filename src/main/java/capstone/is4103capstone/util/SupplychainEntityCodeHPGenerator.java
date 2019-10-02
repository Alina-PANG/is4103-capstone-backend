package capstone.is4103capstone.util;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.util.exception.RepositoryEntityMismatchException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public class SupplychainEntityCodeHPGenerator {
    private final String VENDOR_TEMPLATE = "Vendor-%1$s-%2$s";
    private final String CONTRACT_TEMPLATE = "Contract-%1$s-%2$s";

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
                    default:
                        System.out.println("Not found");
                }
            } catch (Exception e) {
                throw new Exception("entity have null name");
            }
            code = code.toUpperCase();
            entity.setCode(code);
            repo.saveAndFlush(entity);

            return code;
        } else {
            throw new RepositoryEntityMismatchException("Repository class given does not match the entity type.");
        }
    }
}

