package capstone.is4103capstone.finance.finPurchaseOrder;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.supplychain.SCMEntityCodeHPGeneration;
import capstone.is4103capstone.util.SupplychainEntityCodeHPGenerator;
import capstone.is4103capstone.util.exception.RepositoryEntityMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

public class POEntityCodeHPGeneration {
    private static final Logger logger = LoggerFactory.getLogger(POEntityCodeHPGeneration.class);

    static POEntityCodeHPGenerator g = new POEntityCodeHPGenerator();

    public static String getCode(JpaRepository repo, DBEntityTemplate entity) {
        try {
            String code = g.generateCode(repo, entity);
            return code;
        } catch (RepositoryEntityMismatchException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}