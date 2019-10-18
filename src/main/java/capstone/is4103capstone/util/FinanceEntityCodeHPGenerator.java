package capstone.is4103capstone.util;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.finance.*;
import capstone.is4103capstone.util.exception.RepositoryEntityMismatchException;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public class FinanceEntityCodeHPGenerator {

    private final String APPROVAL_FOR_REQUEST_TEMPLATE = "RT-%1$s-%2$s";
    private final String ACTUALS_TABLE_TEMPLATE = "ACTUALS-%1$s-%2$s";
    private final String BJF_TEMPLATE = "BJF-%1$s-%2$s";
    private final String BUDGET_CATEGORY_TEMPLATE = "CAT-%1$s-%2$s";
    private final String BUDGET_SUB1_TEMPLATE = "CATS1-%1$s-%2$s";
    private final String BUDGET_SUB2_TEMPLATE = "CATS2-%1$s-%2$s";
    private final String FX_RECORD_TEMPLATE = "FX-%1$s-%2$s";
    private final String MERCHANDISE_TEMPALTE = "SP-%1$s-%2$s";
    private final String PLAN_TEMPLATE = "%1$s-%2$s";
    private final String PLAN_ITEM_TEMPLATE = "PLINE-%1$s";
    private final String PROJECT_TEMPLATE = "PJ-%1$s";
    private final String INVOICE_TEMPLATE = "INV-%1$s-%2$s";
    private final String STATEMENT_OF_ACCT_TEMPLATE = "SOA-%1$s-%2$s";
    private final String TRAVEL_PLAN_TEMPLATE = "TRAVEL-%1$s-%2$s";
    private final String TRAIN_PLAN_TEMPLATE = "TRAIN-%1$s-%2$s";
    private final String COST_CENTER_TEMPLATE = "%1$s-%2$s";


    public String generateHierachyPath(DBEntityTemplate parent, DBEntityTemplate son){
        //for categories:cat: country's path + cat_name  -> parent'path + objectName;
        String sonNmae = son.getObjectName().replaceAll("\\s+", "_").toUpperCase();
        son.setHierachyPath(parent.getHierachyPath()+"-"+sonNmae);
        return son.getHierachyPath();
    }

    public String getPlanHP(Plan plan, CostCenter cc){
        return cc.getCode() + "-" + plan.getPlanType().name()+"-"+plan.getForYear();
    }
    public String getPlanItemHP(PlanLineItem pl){
        return pl.getPlanBelongsTo().getHierachyPath() + "-"+pl.getserviceCode();
    }

    public String generateCode(JpaRepository repo, DBEntityTemplate entity,String additionalInfo) throws Exception{
        return process(repo,entity,additionalInfo);
    }

    public String generateCode(JpaRepository repo, DBEntityTemplate entity) throws Exception {
        return process(repo,entity,"");
    }

        //after connecting all the relationships:
    private String process(JpaRepository repo, DBEntityTemplate entity,String additionalInfo) throws Exception {
        Optional<DBEntityTemplate> entityOptional = repo.findById(entity.getId());
        String code = "";
        if (entityOptional.isPresent()){
            entity = entityOptional.get();
            if (entity.getSeqNo() == null){
                entity.setSeqNo(new Long(repo.findAll().size()));
            }
            String seqNoStr = String.valueOf(entity.getSeqNo());
//            if (seqNoStr == null){//it's only at creation
//                entity.setSeqNo(new Long(repo.findAll().size()));
//                seqNoStr = String.valueOf(entity.getSeqNo());
//            }
            String entityClassName = entity.getClass().getSimpleName();
            String objectName;
            try{
                objectName = entity.getObjectName().replaceAll("\\s+", "_");
            }catch (NullPointerException ne){
                objectName = "";
            }
            try {
                switch (entityClassName) {
                    case "ApprovalForRequest":
                        code = String.format(APPROVAL_FOR_REQUEST_TEMPLATE, entity.getCreatedBy(), seqNoStr);
                        break;
                    case "ActualsTable":
                        //                    ActualsTable at = (ActualsTable)entity;
                        //name = costcenter code + year
                        code = String.format(ACTUALS_TABLE_TEMPLATE, objectName, entity.getSeqNo());
                        break;
                    case "BJF":
                        //                    BJF bjf = (BJF)entity;
                        //name = bjf type + created by who
                        code = String.format(BJF_TEMPLATE, objectName, seqNoStr);
                        break;
                    case "BudgetCategory":
                        code = String.format(BUDGET_CATEGORY_TEMPLATE, objectName, seqNoStr);
                        break;
                    case "BudgetSub1":
                        code = String.format(BUDGET_SUB1_TEMPLATE, objectName,seqNoStr);
                        break;
                    case "BudgetSub2":
                        code = String.format(BUDGET_SUB2_TEMPLATE, objectName,seqNoStr);
                        break;
                    case "FXRecord":
                        //                    FXRecord fx = (FXRecord) entity;
                        //name = base+price+ddmmyy
                        code = String.format(FX_RECORD_TEMPLATE, objectName, seqNoStr);
                        break;
                    case "Service":
                        //                    service m = (service) entity;
                        code = String.format(MERCHANDISE_TEMPALTE, objectName, seqNoStr);
                        break;
                    case "Plan":
                        //                    Plan p = (Plan)entity;
                        //                    String abbr = p.getPlanType() == BudgetPlanEnum.BUDGET? "BGT":"RFC";
                        //name = abbr + cc + year
                        code = String.format(PLAN_TEMPLATE, objectName, seqNoStr);
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
                        code = String.format(INVOICE_TEMPLATE, objectName, seqNoStr);
                        break;
                    case "StatementOfAcctLineItem":
                        //                    StatementOfAcctLineItem soa = (StatementOfAcctLineItem) entity;
                        //name: ponumber+index
                        code = String.format(STATEMENT_OF_ACCT_TEMPLATE, objectName, seqNoStr);
                        break;
                    case "TravelForm":
                        code = String.format(TRAVEL_PLAN_TEMPLATE,additionalInfo,seqNoStr);
                    case "TrainingForm":
                        code = String.format(TRAIN_PLAN_TEMPLATE,additionalInfo,seqNoStr);
                    case "CostCenter":
                        code = additionalInfo.isEmpty()? String.format(COST_CENTER_TEMPLATE,"CC",seqNoStr):String.format(COST_CENTER_TEMPLATE,"PJCC",seqNoStr);
                    default:
                        System.out.println("Not found");
                }
            }catch (Exception e){
                throw new Exception("entity have null name");
            }
            code = code.toUpperCase();
            entity.setCode(code);
            repo.saveAndFlush(entity);

            return code;
        }else {
            throw new RepositoryEntityMismatchException("Repository class given does not match the entity type.");
        }
    }
}
