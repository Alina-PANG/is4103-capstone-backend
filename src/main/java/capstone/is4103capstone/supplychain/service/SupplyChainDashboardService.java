package capstone.is4103capstone.supplychain.service;

import capstone.is4103capstone.admin.repository.BusinessUnitRepository;
import capstone.is4103capstone.admin.repository.TeamRepository;
import capstone.is4103capstone.entities.supplyChain.Outsourcing;
import capstone.is4103capstone.supplychain.Repository.ContractRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingAssessmentRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingSelfAssessmentRepository;
import capstone.is4103capstone.supplychain.Repository.VendorRepository;
import capstone.is4103capstone.supplychain.model.OutsourcingModel;
import capstone.is4103capstone.supplychain.model.res.GetOutsourcingRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplyChainDashboardService {
    private static final Logger logger = LoggerFactory.getLogger(SupplyChainDashboardService.class);

    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private OutsourcingSelfAssessmentRepository outsourcingSelfAssessmentRepository;
    @Autowired
    private OutsourcingAssessmentRepository outsourcingAssessmentRepository;


//    public GetOutsourcingRes getOutsourcing (String id){
//        try{
//            logger.info("Getting outsourcing by outsourcing id: " + id);
//            Outsourcing outsourcing = outsourcingRepository.getOne(id);
//
//            if(outsourcing == null){
//                return new GetOutsourcingRes("There is no outsourcing in the database with id " + id, true, null);
//            }
//            else if(outsourcing.getDeleted()){
//                return new GetOutsourcingRes("This outsourcing is deleted", true, null);
//            }
//            else{
//                OutsourcingModel outsourcingModel = transformToOutsourcingModelDetails(outsourcing);
//                return new GetOutsourcingRes("Successfully retrieved the outsourcing with id " + id, false, outsourcingModel);
//            }
//        }catch(Exception ex){
//            ex.printStackTrace();
//            return new GetOutsourcingRes("An unexpected error happens: "+ex.getMessage(), true, null);
//        }
//    }
}
