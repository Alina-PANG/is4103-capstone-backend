package capstone.is4103capstone.finance.admin;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.entities.finance.Plan;
import capstone.is4103capstone.entities.finance.PlanLineItem;
import capstone.is4103capstone.util.FinanceEntityCodeHPGenerator;
import capstone.is4103capstone.util.exception.RepositoryEntityMismatchException;
import org.springframework.data.jpa.repository.JpaRepository;

public class EntityCodeHPGeneration {
    static  FinanceEntityCodeHPGenerator g = new FinanceEntityCodeHPGenerator();

    public static String getCode(JpaRepository repo, DBEntityTemplate entity, String additionalInfo){
        try {
            String code = g.generateCode(repo,entity,additionalInfo);
            entity.setCode(code);
            return code;
        }catch (RepositoryEntityMismatchException e){
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    public static String getCode(JpaRepository repo, DBEntityTemplate entity){
        try {
            String code = g.generateCode(repo,entity);
            entity.setCode(code);
            return code;
        }catch (RepositoryEntityMismatchException e){
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static String setHP(DBEntityTemplate parent, DBEntityTemplate son){
        //for categories:cat: country's path + cat_name  -> parent'path + objectName;
        String sonNmae = son.getObjectName().replaceAll("\\s+", "_").toUpperCase();
        son.setHierachyPath(parent.getHierachyPath()+"-"+sonNmae);
        return son.getHierachyPath();
    }

    public static String setPlanHP(Plan plan, CostCenter cc){
        String hp = cc.getCode() + "-" + plan.getPlanType().name()+"-"+plan.getForYear();
        plan.setHierachyPath(hp);
        return hp;
    }
    public static String setPlanItemHP(PlanLineItem pl){
        String hp = pl.getPlanBelongsTo().getHierachyPath() + "-"+pl.getserviceCode();
        pl.setHierachyPath(hp);
        return hp;
    }
    public static String setPlanItemHP(PlanLineItem pl,String planHierachyPath){
        String hp = planHierachyPath + "-"+pl.getserviceCode();
        pl.setHierachyPath(hp);
        return hp;
    }
}
