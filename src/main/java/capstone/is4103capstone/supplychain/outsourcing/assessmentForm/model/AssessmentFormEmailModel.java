package capstone.is4103capstone.supplychain.outsourcing.assessmentForm.model;

import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessmentLine;

import java.util.List;

public class AssessmentFormEmailModel {
    private List<AssessmentFormLineEmailModel> lines;
    private int seqNo;

    public AssessmentFormEmailModel() {
    }

    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public List<AssessmentFormLineEmailModel> getLines() {
        return lines;
    }

    public void setLines(List<AssessmentFormLineEmailModel> lines) {
        this.lines = lines;
    }
}
