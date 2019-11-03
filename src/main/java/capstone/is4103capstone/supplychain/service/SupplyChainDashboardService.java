package capstone.is4103capstone.supplychain.service;

import capstone.is4103capstone.supplychain.Repository.ContractRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingAssessmentRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingSelfAssessmentRepository;
import capstone.is4103capstone.supplychain.model.ContractDistributionModel;
import capstone.is4103capstone.supplychain.model.OutsourcingAssessmentDistributionModel;
import capstone.is4103capstone.supplychain.model.OutsourcingSelfAssessmentDistributionModel;
import capstone.is4103capstone.supplychain.model.res.GetAssessmentDistributionRes;
import capstone.is4103capstone.supplychain.model.res.GetContractDistributionRes;
import capstone.is4103capstone.supplychain.model.res.GetSelfAssessmentDistributionRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class SupplyChainDashboardService {
    private static final Logger logger = LoggerFactory.getLogger(SupplyChainDashboardService.class);

    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private OutsourcingSelfAssessmentRepository outsourcingSelfAssessmentRepository;
    @Autowired
    private OutsourcingAssessmentRepository outsourcingAssessmentRepository;


    public GetContractDistributionRes getContractDistribution(){
        try{
            logger.info("Getting Contract Distribution");

            //DRAFT=0, PENDING_APPROVAL=1, REJECTED=2, ACTIVE=3, MERGED=4, TERMINATED=5
            BigDecimal numOfDraft = contractRepository.findNumberOfContractByStatus("0");
            BigDecimal numOfPending = contractRepository.findNumberOfContractByStatus("1");
            BigDecimal numOfRejected = contractRepository.findNumberOfContractByStatus("2");
            BigDecimal numOfActive = contractRepository.findNumberOfContractByStatus("3");
            BigDecimal numOfMerged = contractRepository.findNumberOfContractByStatus("4");
            BigDecimal numOfTerminated = contractRepository.findNumberOfContractByStatus("5");

            BigDecimal total = numOfDraft.add(
                    numOfPending.add(
                            numOfRejected.add(
                                    numOfActive.add(
                                            numOfMerged.add(numOfTerminated)))));

            BigDecimal draftPercentage = numOfDraft.divide(total, 3, RoundingMode.HALF_UP);
            BigDecimal pendingPercentage = numOfPending.divide(total, 3, RoundingMode.HALF_UP);
            BigDecimal rejectedPercentage = numOfRejected.divide(total, 3, RoundingMode.HALF_UP);
            BigDecimal activePercentage = numOfActive.divide(total, 3, RoundingMode.HALF_UP);
            BigDecimal mergedPercentage = numOfMerged.divide(total, 3, RoundingMode.HALF_UP);
            BigDecimal terminatedPercentage = numOfTerminated.divide(total, 3, RoundingMode.HALF_UP);

            ContractDistributionModel model = new ContractDistributionModel(draftPercentage,pendingPercentage,activePercentage,
                    terminatedPercentage,mergedPercentage,rejectedPercentage);

            return new GetContractDistributionRes("", false, model);

        }catch(Exception ex){
            ex.printStackTrace();
            return new GetContractDistributionRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

    public GetAssessmentDistributionRes getAssessmentDistribution(){
        try{
            logger.info("Getting Assessment Distribution");

            //TEMPLATE=0, PENDING_BM_APPROVAL=1, PENDING_OUTSOURCING_APPROVAL=2,
            //APPROVED=3, OUTSOURCING_RECORD_CREATED=4, REJECTED=5
            BigDecimal numOfTemplate = outsourcingAssessmentRepository.findNumberOfAssessmentByStatus("0");
            BigDecimal numOfPendingBM = outsourcingAssessmentRepository.findNumberOfAssessmentByStatus("1");
            BigDecimal numOfPendingOutsourcing = outsourcingAssessmentRepository.findNumberOfAssessmentByStatus("2");
            BigDecimal numOfApproved = outsourcingAssessmentRepository.findNumberOfAssessmentByStatus("3");
            BigDecimal numOfOutsourcingCreated = outsourcingAssessmentRepository.findNumberOfAssessmentByStatus("4");
            BigDecimal numOfRejected = outsourcingAssessmentRepository.findNumberOfAssessmentByStatus("5");

            BigDecimal total = numOfTemplate.add(
                    numOfPendingBM.add(
                            numOfPendingOutsourcing.add(
                                    numOfApproved.add(
                                            numOfOutsourcingCreated.add(numOfRejected)))));

            BigDecimal templatePercentage = numOfTemplate.divide(total, 3, RoundingMode.HALF_UP);
            BigDecimal pendingPMPercentage = numOfPendingBM.divide(total, 3, RoundingMode.HALF_UP);
            BigDecimal pendingOutsourcingPercentage = numOfPendingOutsourcing.divide(total, 3, RoundingMode.HALF_UP);
            BigDecimal approvedPercentage = numOfApproved.divide(total, 3, RoundingMode.HALF_UP);
            BigDecimal outsourcingCreatedPercentage = numOfOutsourcingCreated.divide(total, 3, RoundingMode.HALF_UP);
            BigDecimal rejectedPercentage = numOfRejected.divide(total, 3, RoundingMode.HALF_UP);

            OutsourcingAssessmentDistributionModel model = new OutsourcingAssessmentDistributionModel(
                    templatePercentage,pendingPMPercentage,
                    pendingOutsourcingPercentage,approvedPercentage,
                    outsourcingCreatedPercentage,rejectedPercentage);

            return new GetAssessmentDistributionRes("", false, model);

        }catch(Exception ex){
            ex.printStackTrace();
            return new GetAssessmentDistributionRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

    public GetSelfAssessmentDistributionRes getSelfAssessmentDistribution(){
        try{
            logger.info("Getting Annual Self Assessment Distribution");

            //DRAFT=0, PENDING_APPROVAL=1,
            //REJECTED=2, APPROVED=3
            BigDecimal numOfDraft = outsourcingSelfAssessmentRepository.findNumberOfSelfAssessmentByStatus("0");
            BigDecimal numOfPending = outsourcingSelfAssessmentRepository.findNumberOfSelfAssessmentByStatus("1");
            BigDecimal numOfRejected = outsourcingSelfAssessmentRepository.findNumberOfSelfAssessmentByStatus("2");
            BigDecimal numOfApproved = outsourcingSelfAssessmentRepository.findNumberOfSelfAssessmentByStatus("3");


            BigDecimal total = numOfDraft.add(
                    numOfPending.add(
                            numOfRejected.add(numOfApproved)));

            BigDecimal draftPercentage = numOfDraft.divide(total, 3, RoundingMode.HALF_UP);
            BigDecimal pendingPercentage = numOfPending.divide(total, 3, RoundingMode.HALF_UP);
            BigDecimal rejectedPercentage = numOfRejected.divide(total, 3, RoundingMode.HALF_UP);
            BigDecimal approvedPercentage = numOfApproved.divide(total, 3, RoundingMode.HALF_UP);

            OutsourcingSelfAssessmentDistributionModel model = new OutsourcingSelfAssessmentDistributionModel(
                    draftPercentage,pendingPercentage,
                    rejectedPercentage,approvedPercentage);

            return new GetSelfAssessmentDistributionRes("", false, model);

        }catch(Exception ex){
            ex.printStackTrace();
            return new GetSelfAssessmentDistributionRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

//    public GetSefAssessmentDistributionRes getSelfAssessmentDistribution(){
//        try{
//            logger.info("Getting Annual Self Assessment Distribution");
//
//            return new GetSelfAssessmentDistributionRes("", false, model);
//
//        }catch(Exception ex){
//            ex.printStackTrace();
//            return new GetSelfAssessmentDistributionRes("An unexpected error happens: "+ex.getMessage(), true, null);
//        }
//    }
}
