package capstone.is4103capstone.supplychain.service;

import capstone.is4103capstone.entities.supplyChain.Contract;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessment;
import capstone.is4103capstone.entities.supplyChain.OutsourcingSelfAssessment;
import capstone.is4103capstone.supplychain.Repository.ContractRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingAssessmentRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingSelfAssessmentRepository;
import capstone.is4103capstone.supplychain.model.*;
import capstone.is4103capstone.supplychain.model.res.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

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
            BigDecimal draft = contractRepository.findNumberOfContractByStatus("0");
            BigDecimal pending = contractRepository.findNumberOfContractByStatus("1");
            BigDecimal rejected = contractRepository.findNumberOfContractByStatus("2");
            BigDecimal active = contractRepository.findNumberOfContractByStatus("3");
            BigDecimal merged = contractRepository.findNumberOfContractByStatus("4");
            BigDecimal terminated = contractRepository.findNumberOfContractByStatus("5");

            ContractDistributionModel model = new ContractDistributionModel(draft,pending,active,
                    terminated,merged,rejected);

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
            BigDecimal template = outsourcingAssessmentRepository.findNumberOfAssessmentByStatus("0");
            BigDecimal pendingBM = outsourcingAssessmentRepository.findNumberOfAssessmentByStatus("1");
            BigDecimal pendingOutsourcing = outsourcingAssessmentRepository.findNumberOfAssessmentByStatus("2");
            BigDecimal approved = outsourcingAssessmentRepository.findNumberOfAssessmentByStatus("3");
            BigDecimal outsourcingCreated = outsourcingAssessmentRepository.findNumberOfAssessmentByStatus("4");
            BigDecimal rejected = outsourcingAssessmentRepository.findNumberOfAssessmentByStatus("5");

            OutsourcingAssessmentDistributionModel model = new OutsourcingAssessmentDistributionModel(
                    template,pendingBM,pendingOutsourcing,approved,outsourcingCreated,rejected);

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
            BigDecimal draft = outsourcingSelfAssessmentRepository.findNumberOfSelfAssessmentByStatus("0");
            BigDecimal pending = outsourcingSelfAssessmentRepository.findNumberOfSelfAssessmentByStatus("1");
            BigDecimal rejected = outsourcingSelfAssessmentRepository.findNumberOfSelfAssessmentByStatus("2");
            BigDecimal approved = outsourcingSelfAssessmentRepository.findNumberOfSelfAssessmentByStatus("3");

            OutsourcingSelfAssessmentDistributionModel model = new OutsourcingSelfAssessmentDistributionModel(
                    draft,pending,rejected,approved);

            return new GetSelfAssessmentDistributionRes("", false, model);

        }catch(Exception ex){
            ex.printStackTrace();
            return new GetSelfAssessmentDistributionRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

    public GetGeneralContractsRes getContractsByStatus(String status){
        try{
            logger.info("Getting Contracts by Status:");

            //DRAFT=0, PENDING_APPROVAL=1, REJECTED=2, ACTIVE=3, MERGED=4, TERMINATED=5
            String statusCode = null;

            if(status.equals("DRAFT")){
                statusCode = "0";
            }
            if(status.equals("PENDING_APPROVAL")){
                statusCode = "1";
            }
            if(status.equals("REJECTED")){
                statusCode = "2";
            }
            if(status.equals("ACTIVE")){
                statusCode = "3";
            }
            if(status.equals("MERGED")){
                statusCode = "4";
            }if(status.equals("TERMINATED")){
                statusCode = "5";
            }

            if(statusCode == null){
                throw new Exception("Wrong status!");
            }

            List<Contract> contractList = contractRepository.findContractsByStatus(statusCode);

            //transform Contract to GeneralContractModel
            List<GeneralContractModel> result = new ArrayList<>();
            for(Contract c : contractList){
                GeneralContractModel model = new GeneralContractModel(
                        c.getContractDescription(), c.getObjectName(),
                        c.getCode(), c.getContractStatus());

                result.add(model);
            }

            return new GetGeneralContractsRes("", false, result);

        }catch(Exception ex){
            ex.printStackTrace();
            return new GetGeneralContractsRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

    public GetGeneralAssessmentsRes getAssessmentByStatus(String status){
        try{
            logger.info("Getting Assessment by Status:");

            //TEMPLATE=0, PENDING_BM_APPROVAL=1, PENDING_OUTSOURCING_APPROVAL=2,
            //APPROVED=3, OUTSOURCING_RECORD_CREATED=4, REJECTED=5
            String statusCode = "0";

            if(status.equals("TEMPLATE")){
                statusCode = "0";
            }
            if(status.equals("PENDING_BM_APPROVAL")){
                statusCode = "1";
            }
            if(status.equals("PENDING_OUTSOURCING_APPROVAL")){
                statusCode = "2";
            }
            if(status.equals("APPROVED")){
                statusCode = "3";
            }
            if(status.equals("OUTSOURCING_RECORD_CREATED")){
                statusCode = "4";
            }if(status.equals("REJECTED")){
                statusCode = "5";
            }

            if(statusCode == null){
                throw new Exception("Wrong status!");
            }

            List<OutsourcingAssessment> assessmentList = outsourcingAssessmentRepository.findAssessmentsByStatus(statusCode);

            //transform Contract to GeneralContractModel
            List<GeneralAssessmentModel> result = new ArrayList<>();
            for(OutsourcingAssessment a : assessmentList){
                GeneralAssessmentModel model = new GeneralAssessmentModel(
                        a.getBusinessCaseDescription(),a.getCode(),a.getOutsourcingAssessmentStatus());

                result.add(model);
            }

            return new GetGeneralAssessmentsRes("", false, result);

        }catch(Exception ex){
            ex.printStackTrace();
            return new GetGeneralAssessmentsRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

    public GetGeneralSelfAssessmentsRes getSelfAssessmentsByStatus(String status){
        try{
            logger.info("Getting Self Assessments by Status:");

            //DRAFT=0, PENDING_APPROVAL=1,
            //REJECTED=2, APPROVED=3
            String statusCode = "0";

            if(status.equals("DRAFT")){
                statusCode = "0";
            }
            if(status.equals("PENDING_APPROVAL")){
                statusCode = "1";
            }
            if(status.equals("REJECTED")){
                statusCode = "2";
            }
            if(status.equals("APPROVED")){
                statusCode = "3";
            }

            if(statusCode == null){
                throw new Exception("Wrong status!");
            }

            List<OutsourcingSelfAssessment> selfAssessmentList = outsourcingSelfAssessmentRepository.findSelfAssessmentsByStatus(statusCode);

            //transform Contract to GeneralContractModel
            List<GeneralSelfAssessmentModel> result = new ArrayList<>();
            for(OutsourcingSelfAssessment s : selfAssessmentList){
                GeneralSelfAssessmentModel model = new GeneralSelfAssessmentModel(
                        s.getCode(), s.getOutsourcingSelfAssessmentStatus(), s.getAnnualAssessmentDate());

                result.add(model);
            }

            return new GetGeneralSelfAssessmentsRes("", false, result);

        }catch(Exception ex){
            ex.printStackTrace();
            return new GetGeneralSelfAssessmentsRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }
}
