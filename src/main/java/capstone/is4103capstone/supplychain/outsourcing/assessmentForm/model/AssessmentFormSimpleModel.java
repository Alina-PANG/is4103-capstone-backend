package capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model;

import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessment;
import capstone.is4103capstone.util.enums.OutsourcingAssessmentStatusEnum;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.Date;

public interface AssessmentFormSimpleModel{

    @Value("#{target.bjf_code}")
    String getBJFCode();
    @Value("#{target.bjf_id}")
    String getRelatedBjfID();
    @Value("#{target.requester}")
    String getRequesterUsername();
    @Value("#{target.form_status}")
    OutsourcingAssessmentStatusEnum getAssessmentFormStatus();
    @Value("#{target.form_id}")
    String getAssessmentFormId();
    @Value("#{target.form_code}")
    String getAssessmentFormCode();
    @Value("#{target.description}")
    String getDescription();
    @Value("#{target.outsourcing_id}")
    String getOutsourcingRecordId();
    @Value("#{target.bm_approver}")
    String getBMApprover();
    @Value("#{target.last_modified_dt}")
    Date getLastModifiedDateTime();


}
