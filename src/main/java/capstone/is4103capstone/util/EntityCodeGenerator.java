package capstone.is4103capstone.util;

import capstone.is4103capstone.configuration.DBEntityTemplate;
//import capstone.is4103capstone.entities.ApprovalForRequest;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.finance.*;
import capstone.is4103capstone.util.enums.BudgetPlanEnum;
import capstone.is4103capstone.util.exception.RepositoryEntityMismatchException;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EntityCodeGenerator {

    private final String APPROVAL_FOR_REQUEST_TEMPLATE = "RT-%1$s-%2$s";
    private final String ACTUALS_TABLE_TEMPLATE = "ACTUALS-%1$s-%2$s";
    private final String BJF_TEMPLATE = "BJF-%1$s-%2$s";
    private final String BUDGET_CATEGORY_TEMPLATE = "CAT-%1$s-%2$s";
    private final String BUDGET_SUB1_TEMPLATE = "CATS1-%1$s";
    private final String BUDGET_SUB2_TEMPLATE = "CATS2-%1$s";
    private final String FX_RECORD_TEMPLATE = "FX-%1$s-%2$s";
    private final String MERCHANDISE_TEMPALTE = "M-%1$s-%2$s";
    private final String PLAN_TEMPLATE = "%1$s-%2$s";
    private final String PLAN_ITEM_TEMPLATE = "PLINE-%1$s";
    private final String PROJECT_TEMPLATE = "PJ-%1$s";
    private final String INVOICE_TEMPLATE = "INV-%1$s-%2$s";
    private final String STATEMENT_OF_ACCT_TEMPLATE = "SOA-%1$s-%2$s";

//    private final String

//    private final Map<Class,String> classTempalteMap = new HashMap<Class,String>(){
//        {
//            put(ApprovalForRequest.class,APPROVAL_FOR_REQUEST_TEMPLATE);
//        }
//    };

    //after connecting all the relationships:
    public String generateCode(JpaRepository repo, DBEntityTemplate entity) throws Exception {
        Optional<DBEntityTemplate> entityOptional = repo.findById(entity.getId());
        String code = "";
        if (entityOptional.isPresent()){
            entity = entityOptional.get();
            String seqNoStr = String.valueOf(entity.getSeqNo());

            String entityClassName = entity.getClass().getSimpleName();
            try {
                switch (entityClassName) {
                    case "ApprovalForRequest":
                        code = String.format(APPROVAL_FOR_REQUEST_TEMPLATE, entity.getCreatedBy(), seqNoStr);
                        break;
                    case "ActualsTable":
                        //                    ActualsTable at = (ActualsTable)entity;
                        //name = costcenter code + year
                        code = String.format(ACTUALS_TABLE_TEMPLATE, entity.getObjectName(), entity.getSeqNo());
                        break;
                    case "BJF":
                        //                    BJF bjf = (BJF)entity;
                        //name = bjf type + created by who
                        code = String.format(BJF_TEMPLATE, entity.getObjectName(), seqNoStr);
                        break;
                    case "BudgetCategory":
                        //                    BudgetCategory cat = (BudgetCategory) entity;
                        //name = name;
                        code = String.format(BUDGET_CATEGORY_TEMPLATE, entity.getObjectName(), seqNoStr);
                        break;
                    case "BudgetSub1":
                        code = String.format(BUDGET_SUB1_TEMPLATE, seqNoStr);
                        break;
                    case "BudgetSub2":
                        code = String.format(BUDGET_SUB2_TEMPLATE, seqNoStr);
                        break;
                    case "FXRecord":
                        //                    FXRecord fx = (FXRecord) entity;
                        //name = base+price+ddmmyy
                        code = String.format(FX_RECORD_TEMPLATE, entity.getObjectName(), seqNoStr);
                        break;
                    case "Merchandise":
                        //                    Merchandise m = (Merchandise) entity;
                        code = String.format(MERCHANDISE_TEMPALTE, entity.getObjectName(), seqNoStr);
                        break;
                    case "Plan":
                        //                    Plan p = (Plan)entity;
                        //                    String abbr = p.getPlanType() == BudgetPlanEnum.BUDGET? "BGT":"RFC";
                        //name = abbr + cc + year
                        code = String.format(PLAN_TEMPLATE, entity.getObjectName(), seqNoStr);
                        break;
                    case "PlanLineItem":
                        code = String.format(PLAN_ITEM_TEMPLATE, seqNoStr);
                        break;
                    case "Project":
                        code = String.format(PROJECT_TEMPLATE, seqNoStr);
                        break;
                    case "PurchaseOrder":
                        //not required
                        break;
                    case "Invoice":
                        //                    Invoice inv = (Invoice) entity;
                        //name: "invoice" + ponumber
                        code = String.format(INVOICE_TEMPLATE, entity.getObjectName(), seqNoStr);
                        break;
                    case "StatementOfAcctLineItem":
                        //                    StatementOfAcctLineItem soa = (StatementOfAcctLineItem) entity;
                        //name: ponumber+index
                        code = String.format(STATEMENT_OF_ACCT_TEMPLATE, entity.getObjectName(), seqNoStr);
                        break;
                    default:
                        System.out.println("Not found");
                }
            }catch (Exception e){
                throw new Exception("entity have null name");
            }
            entity.setCode(code);
            repo.saveAndFlush(entity);

            return code;
        }else {
            throw new RepositoryEntityMismatchException("Repository class given does not match the entity type.");
        }
    }

}
