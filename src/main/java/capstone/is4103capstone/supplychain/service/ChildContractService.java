package capstone.is4103capstone.supplychain.service;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.supplyChain.ChildContract;
import capstone.is4103capstone.entities.supplyChain.Contract;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.model.GeneralEntityModel;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.ApprovalTicketService;
import capstone.is4103capstone.supplychain.Repository.ChildContractRepository;
import capstone.is4103capstone.supplychain.Repository.ContractRepository;
import capstone.is4103capstone.supplychain.SCMEntityCodeHPGeneration;
import capstone.is4103capstone.supplychain.model.ChildContractModel;
import capstone.is4103capstone.supplychain.model.req.ApproveChildContractReq;
import capstone.is4103capstone.supplychain.model.req.ApproveContractReq;
import capstone.is4103capstone.supplychain.model.req.CreateChildContractReq;
import capstone.is4103capstone.supplychain.model.res.GetChildContractRes;
import capstone.is4103capstone.supplychain.model.res.GetChildContractsRes;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import capstone.is4103capstone.util.enums.ChildContractStatusEnum;
import capstone.is4103capstone.util.enums.ContractStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChildContractService {
    private static final Logger logger = LoggerFactory.getLogger(VendorService.class);

    @Autowired
    ContractRepository contractRepository;
    @Autowired
    ChildContractRepository childContractRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    public GeneralRes createChildContract(CreateChildContractReq createChildContractReq){
        try{
            ChildContract newChildContract = new ChildContract();
            newChildContract.setObjectName(createChildContractReq.getChildContractName());
            newChildContract.setCreatedBy(createChildContractReq.getModifierUsername());
            newChildContract.setLastModifiedBy(createChildContractReq.getModifierUsername());
            newChildContract.setContractValue(createChildContractReq.getContractValue());
            newChildContract.setServiceCode(createChildContractReq.getServiceCode());
            newChildContract.setDeleted(false);
            newChildContract.setChildContractStatus(ChildContractStatusEnum.PENDING_APPROVAL);
            newChildContract = childContractRepository.saveAndFlush(newChildContract);

            Employee approver = employeeRepository.getOne(createChildContractReq.getApproverId());
            newChildContract.setApprover(approver);
            approver.getChildContractsApproved().add(newChildContract);

            if(newChildContract.getSeqNo() == null){
                newChildContract.setSeqNo(new Long(childContractRepository.findAll().size()));
            }

            Authentication.configurePermissionMap(newChildContract);

            Contract masterContract = contractRepository.getOne(createChildContractReq.getMasterContractID());
            newChildContract.setMasterContract(masterContract);
            masterContract.getChildContractList().add(newChildContract);
            newChildContract = childContractRepository.saveAndFlush(newChildContract);

            newChildContract.setCode(SCMEntityCodeHPGeneration.getCode(childContractRepository, newChildContract));
            childContractRepository.saveAndFlush(newChildContract);
            contractRepository.saveAndFlush(masterContract);
            employeeRepository.saveAndFlush(approver);

            //send approval request
            try{
                createApprovalTicket(createChildContractReq.getModifierUsername(),newChildContract,"Approver please review the child contract.");
            }catch (Exception emailExc){
            }

            logger.info("Successfully created new child contract under master contract! -- "+masterContract.getCode() +" Waiting for approval!");
            return new GeneralRes("Successfully created new child contract!", false);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: "+ex.getMessage(), true);
        }
    }

    public GetChildContractRes getChildContract(String id){
        try{
            logger.info("Getting child contract by child contract id: " + id);
            ChildContract childContract = childContractRepository.getOne(id);

            if(childContract == null){
                return new GetChildContractRes("There is no child contract in the database with id " + id, true, null);
            }
            else if(childContract.getDeleted()){
                return new GetChildContractRes("This child contract is deleted", true, null);
            }
            else{
                ChildContractModel childContractModel = transformToChildContractModel(childContract);
                return new GetChildContractRes("Successfully retrieved the contract with id " + id, false, childContractModel);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return new GetChildContractRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }


    public GetChildContractsRes getChildContractsByMasterContractId(String masterContractId){
        try {
            List<ChildContractModel> returnList = new ArrayList<>();
            List<ChildContract> childContractList = childContractRepository.findChildContractsByMasterContractId(masterContractId);

            for(ChildContract childContract: childContractList){
                if(childContract.getDeleted()){
                    continue;
                }

                ChildContractModel childContractModel = transformToChildContractModel(childContract);
                returnList.add(childContractModel);
            }

            if(returnList.size() == 0){
                throw new Exception("No child contracts under this master contract with ID: " + masterContractId);
            }

            return new GetChildContractsRes("Successfully retrieved child contracts by master contract ID", false, returnList);
        }catch(Exception ex){
            ex.printStackTrace();
            return new GetChildContractsRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

    //cannot change master contract
    public GeneralRes updateChildContract (CreateChildContractReq updateChildContractReq, String id) {
        try {
            ChildContract childContract = childContractRepository.getOne(id);

            if(updateChildContractReq.getChildContractName() != null){
                childContract.setObjectName(updateChildContractReq.getChildContractName());
            }
            if (updateChildContractReq.getServiceCode() != null) {
                childContract.setServiceCode(updateChildContractReq.getServiceCode());
            }
            if(updateChildContractReq.getContractValue() != null){
                childContract.setContractValue(updateChildContractReq.getContractValue());
            }
            if(updateChildContractReq.getChildContractStatusEnum() != null){
                childContract.setChildContractStatus(updateChildContractReq.getChildContractStatusEnum());
            }

            childContract.setLastModifiedBy(updateChildContractReq.getModifierUsername());
            childContractRepository.saveAndFlush(childContract);
            return new GeneralRes("Successfully updated the child contract!", false);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: " + ex.getMessage(), true);
        }
    }


    public ChildContractModel transformToChildContractModel(ChildContract childContract){
        GeneralEntityModel masterContract = null;

        if(childContract.getMasterContract() != null) {
            masterContract = new GeneralEntityModel(childContract.getMasterContract());
        }

        ChildContractModel childContractModel = new ChildContractModel(
                childContract.getObjectName(), childContract.getCode(), childContract.getId(),
                childContract.getSeqNo(), childContract.getServiceCode(), childContract.getContractValue(),
                masterContract, childContract.getChildContractStatus());

        return childContractModel;
    }


    private void createApprovalTicket(String requesterUsername, ChildContract newChildContract, String content){
        Employee requester = employeeRepository.findEmployeeByUserName(requesterUsername);
        Employee approver = newChildContract.getApprover();
        ApprovalTicketService.createTicketAndSendEmail(requester,approver,newChildContract,content, ApprovalTypeEnum.CHILD_CONTRACT);
    }

    @Transactional
    public boolean getApprovalRight(ChildContract childContract, String username) throws Exception{
        Employee approver = childContract.getApprover();

        if (approver.getUserName().equals(username)){
            return true;
        }else {
            return false;
        }
    }

    public GeneralRes approveChildContract(ApproveChildContractReq approveChildContractReq) {
        try {
            Optional<ChildContract> childContractOp = childContractRepository.findById(approveChildContractReq.getChildContractId());
            if (!childContractOp.isPresent()) {
                throw new Exception("Child Contract Id not found");
            }
            ChildContract childContract = childContractOp.get();
            if (!childContract.getChildContractStatus().equals(ChildContractStatusEnum.PENDING_APPROVAL)) {
                logger.error("Internal error, a non-pending child contract goes into approve function");
                throw new Exception("Internal error, a non-pending child contract goes into approve function");
            }

            if (!approveChildContractReq.getApproved()){
                childContract.setChildContractStatus(ChildContractStatusEnum.REJECTED);
                ApprovalTicketService.rejectTicketByEntity(childContract,approveChildContractReq.getComment(),approveChildContractReq.getUsername());
            }
            else{
                childContract.setChildContractStatus(ChildContractStatusEnum.APPROVED);
                ApprovalTicketService.approveTicketByEntity(childContract,approveChildContractReq.getComment(),approveChildContractReq.getUsername());
            }

            childContractRepository.saveAndFlush(childContract);
            logger.info("Approver Successfully: "+childContract.getChildContractStatus()+" the new child contract! -- "+approveChildContractReq.getUsername()+" "+new Date());
            return new GeneralRes("Approver Successfully "+childContract.getChildContractStatus()+" the child contract!", false);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new GeneralRes( ex.getMessage(), true);
        }
    }
}
