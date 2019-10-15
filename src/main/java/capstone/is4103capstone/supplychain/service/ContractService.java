package capstone.is4103capstone.supplychain.service;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.admin.repository.TeamRepository;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.entities.finance.Plan;
import capstone.is4103capstone.entities.supplyChain.Contract;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.finance.budget.model.req.ApproveBudgetReq;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.model.GeneralEntityModel;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.ApprovalTicketService;
import capstone.is4103capstone.supplychain.Repository.ContractRepository;
import capstone.is4103capstone.supplychain.Repository.VendorRepository;
import capstone.is4103capstone.supplychain.SCMEntityCodeHPGeneration;
import capstone.is4103capstone.supplychain.model.ContractModel;
import capstone.is4103capstone.supplychain.model.req.ApproveContractReq;
import capstone.is4103capstone.supplychain.model.req.CreateContractReq;
import capstone.is4103capstone.supplychain.model.res.GetContractsRes;
import capstone.is4103capstone.supplychain.model.res.GetContractRes;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import capstone.is4103capstone.util.enums.BudgetPlanStatusEnum;
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
public class ContractService {
    private static final Logger logger = LoggerFactory.getLogger(ContractService.class);
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    VendorRepository vendorRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    TeamRepository teamRepository;

    public GeneralRes createContract(CreateContractReq createContractReq){
        try{
            Contract newContract = new Contract();
            newContract.setContractDescription(createContractReq.getContractDescription());
            newContract.setContractTerm(createContractReq.getContractTerm());
            newContract.setContractType(createContractReq.getContractType());
            newContract.setCpgReviewAlertDate(createContractReq.getCpgReviewAlertDate());
            newContract.setStartDate(createContractReq.getStartDate());
            newContract.setEndDate(createContractReq.getEndDate());
            newContract.setNoticeDaysToExit(createContractReq.getNoticeDaysToExit());
            newContract.setPurchaseType(createContractReq.getPurchaseType());
            newContract.setRenewalStartDate(createContractReq.getRenewalStartDate());
            newContract.setSpendType(createContractReq.getSpendType());
            newContract.setObjectName(createContractReq.getContractName());
            newContract.setCreatedBy(createContractReq.getModifierUsername());
            newContract.setLastModifiedBy(createContractReq.getModifierUsername());
            newContract.setTotalContractValue(createContractReq.getTotalContractValue());
            newContract.setCurrencyCode(createContractReq.getCurrencyCode());
            newContract.setDeleted(false);
            newContract.setContractStatus(ContractStatusEnum.PENDING_APPROVAL);
            newContract = contractRepository.saveAndFlush(newContract);
            if (newContract.getSeqNo() == null) {
                newContract.setSeqNo(new Long(contractRepository.findAll().size()));
            }
            Authentication.configurePermissionMap(newContract);

            Vendor vendor = vendorRepository.getOne(createContractReq.getVendorId());
            newContract.setVendor(vendor);
            vendor.getContracts().add(newContract);

            Employee employeeInCharge = employeeRepository.getOne(createContractReq.getEmployeeInChargeId());
            newContract.setEmployeeInChargeContract(employeeInCharge);
            employeeInCharge.getContractInCharged().add(newContract);

            Employee approver = employeeRepository.getOne(createContractReq.getApproverId());
            newContract.setApprover(approver);
            employeeRepository.saveAndFlush(approver);

            Team team  = teamRepository.getOne(createContractReq.getTeamId());
            newContract.setTeam(team);
            team.getContracts().add(newContract);

            newContract = contractRepository.saveAndFlush(newContract);
            newContract.setCode(SCMEntityCodeHPGeneration.getCode(contractRepository,newContract));
            contractRepository.saveAndFlush(newContract);
            vendorRepository.saveAndFlush(vendor);
            employeeRepository.saveAndFlush(employeeInCharge);
            teamRepository.saveAndFlush(team);

            //send approval request
            try{
                createApprovalTicket(createContractReq.getModifierUsername(),newContract,"Approver please review the contract.");
            }catch (Exception emailExc){
            }

            logger.info("Successfully created new contract! -- "+createContractReq.getModifierUsername()+" "+"Waiting for approval!");
            return new GeneralRes("Successfully created new contract!", false);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: "+ex.getMessage(), true);
        }
    }

    private void createApprovalTicket(String requesterUsername, Contract newContract, String content){
        Employee requester = employeeRepository.findEmployeeByUserName(requesterUsername);
        Employee approver = newContract.getApprover();
        ApprovalTicketService.createTicketAndSendEmail(requester,approver,newContract,content, ApprovalTypeEnum.CONTRACT);
    }

    @Transactional
    public boolean getApprovalRight(Contract contract, String username) throws Exception{
        Employee approver = contract.getApprover();

        if (approver.getUserName().equals(username)){
            return true;
        }else {
            return false;
        }
    }

//    public GeneralRes approveContract(ApproveContractReq approveContractReq) {
//        try {
//            Optional<Plan> planOp = planRepository.findById(approveBudgetReq.getPlanId());
//            if (!planOp.isPresent()) {
//                throw new Exception("Plan Id not found");
//            }
//            Plan plan = planOp.get();
//            if (!plan.getBudgetPlanStatus().equals(BudgetPlanStatusEnum.PENDING_BM_APPROVAL) && !plan.getBudgetPlanStatus().equals(BudgetPlanStatusEnum.PENDING_FUNCTION_APPROVAL)) {
//                logger.error("Internal error, a non-pending budget plan goes into approve function");
//                throw new Exception("Internal error, a non-pending budget plan goes into approve function");
//            }
//
//
//
//            if (approveBudgetReq.getApprovalType() == 0) { //0:bm
//                return BMApproval(approveBudgetReq, plan);
//            } else {
//                return functionApproval(approveBudgetReq, plan);
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return new GeneralRes( ex.getMessage(), true);
//        }
//    }
//
    private GeneralRes functionApproval(ApproveContractReq approveContractReq, Contract contract) throws Exception{
        if (!approveContractReq.getApproved()){
            contract.setContractStatus(ContractStatusEnum.REJECTED);
            ApprovalTicketService.rejectTicketByEntity(contract,approveContractReq.getComment(),approveContractReq.getUsername());
        }
        else{
            contract.setContractStatus(ContractStatusEnum.ACTIVE);
            ApprovalTicketService.approveTicketByEntity(contract,approveContractReq.getComment(),approveContractReq.getUsername());
        }

        contractRepository.saveAndFlush(contract);
        logger.info("APPROVER Successfully: "+contract.getContractStatus()+" the new contract! -- "+approveContractReq.getUsername()+" "+new Date());
        return new GeneralRes("APPROVER Successfully "+contract.getContractStatus()+" the contract!", false);
    }

    public GetContractRes getContract(String id){
        try{
            logger.info("Getting contract by contract id: " + id);
            Contract contract = contractRepository.getOne(id);

            if(contract == null){
                return new GetContractRes("There is no contract in the database with id " + id, true, null);
            }
            else if(contract.getDeleted()){
                return new GetContractRes("This contract is deleted", true, null);
            }
            else{
                ContractModel contractModel = transformToContractModel(contract);
                return new GetContractRes("Successfully retrieved the contract with id " + id, false, contractModel);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return new GetContractRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

    public GetContractsRes getAllContracts(){
        try {
            logger.info("Getting all contracts");
            List<ContractModel> returnList = new ArrayList<>();
            List<Contract> contractList = contractRepository.findAll();

            for(Contract contract: contractList){
                if(contract.getDeleted()){
                    continue;
                }

                ContractModel contractModel = transformToContractModel(contract);
                returnList.add(contractModel);
            }

            if(returnList.size() == 0){
                throw new Exception("No contract available.");
            }

            return new GetContractsRes("Successfully retrieved all contracts", false, returnList);
        }catch(Exception ex){
            ex.printStackTrace();
            return new GetContractsRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

    public GeneralRes updateContract(CreateContractReq updateContractReq, String id) {
        try {
            Contract contract = contractRepository.getOne(id);
            if (updateContractReq.getContractDescription()!= null) {
                contract.setContractDescription(updateContractReq.getContractDescription());
            }
            if (updateContractReq.getRenewalStartDate() != null) {
                contract.setRenewalStartDate(updateContractReq.getRenewalStartDate());
            }
            if(updateContractReq.getContractName() != null){
                contract.setObjectName(updateContractReq.getContractName());
            }
            if (updateContractReq.getPurchaseType() != null) {
                contract.setPurchaseType(updateContractReq.getPurchaseType());
            }
            if (updateContractReq.getNoticeDaysToExit() != null) {
                contract.setNoticeDaysToExit(updateContractReq.getNoticeDaysToExit());
            }
            if (updateContractReq.getStartDate() != null) {
                contract.setStartDate(updateContractReq.getStartDate());
            }
            if (updateContractReq.getEndDate() != null) {
                contract.setEndDate(updateContractReq.getEndDate());
            }
            if (updateContractReq.getCpgReviewAlertDate() != null) {
                contract.setCpgReviewAlertDate(updateContractReq.getCpgReviewAlertDate());
            }
            if (updateContractReq.getContractType() != null) {
                contract.setContractType(updateContractReq.getContractType());
            }
            if (updateContractReq.getContractTerm() != null) {
                contract.setContractTerm(updateContractReq.getContractTerm());
            }
            if (updateContractReq.getContractStatus() != null) {
                contract.setContractStatus(updateContractReq.getContractStatus());
            }
            if (updateContractReq.getSpendType() != null) {
                contract.setSpendType(updateContractReq.getSpendType());
            }
            if(updateContractReq.getTotalContractValue() != null){
                contract.setTotalContractValue(updateContractReq.getTotalContractValue());
            }
            if (updateContractReq.getCurrencyCode() != null) {
                contract.setCurrencyCode(updateContractReq.getCurrencyCode());
            }

            contract.setLastModifiedBy(updateContractReq.getModifierUsername());

            if (updateContractReq.getEmployeeInChargeId() != null) {
                Employee employee = employeeRepository.getOne(updateContractReq.getEmployeeInChargeId());
                contract.setEmployeeInChargeContract(employee);

                contract = contractRepository.saveAndFlush(contract);
                employee.getContractInCharged().add(contract);
                employeeRepository.saveAndFlush(employee);
            }
            if (updateContractReq.getVendorId() != null) {
                Vendor vendor = vendorRepository.getOne(updateContractReq.getVendorId());
                contract.setVendor(vendor);

                contract = contractRepository.saveAndFlush(contract);
                vendor.getContracts().add(contract);
                vendorRepository.saveAndFlush(vendor);
            }
            if(updateContractReq.getTeamId() != null){
                Team team = teamRepository.getOne(updateContractReq.getTeamId());
                contract.setTeam(team);

                contract = contractRepository.saveAndFlush(contract);
                team.getContracts().add(contract);
                teamRepository.saveAndFlush(team);
            }

            contractRepository.saveAndFlush(contract);
            return new GeneralRes("Successfully updated the contract!", false);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: " + ex.getMessage(), true);
        }
    }

    public ContractModel transformToContractModel(Contract contract){
        GeneralEntityModel vendor = null;
        GeneralEntityModel employeeInChargeContract = null;
        GeneralEntityModel team = null;

        if(contract.getVendor() != null){
            vendor = new GeneralEntityModel(contract.getVendor());
        }
        if(contract.getEmployeeInChargeContract() != null){
            employeeInChargeContract = new GeneralEntityModel(contract.getEmployeeInChargeContract());
        }
        if(contract.getTeam() != null) {
            team = new GeneralEntityModel(contract.getTeam());
        }

        ContractModel contractModel = new ContractModel(
                contract.getContractDescription(), contract.getObjectName(), contract.getCode(), contract.getId(), contract.getSeqNo(),
                contract.getPurchaseType(), contract.getSpendType(), contract.getContractTerm(),
                contract.getContractType(), contract.getContractStatus(), contract.getNoticeDaysToExit(),
                vendor, employeeInChargeContract, team, contract.getTotalContractValue(), contract.getCurrencyCode(),
                contract.getStartDate(), contract.getEndDate(), contract.getRenewalStartDate(), contract.getCpgReviewAlertDate());

        return contractModel;
    }


    public GetContractsRes getContractsByTeamId(String teamId){
        try {
            List<ContractModel> returnList = new ArrayList<>();
            List<Contract> contractList = contractRepository.findContractsByTeamId(teamId);

            for(Contract contract: contractList){
                if(contract.getDeleted()){
                    continue;
                }

                ContractModel contractModel = transformToContractModel(contract);
                returnList.add(contractModel);
            }

            if(returnList.size() == 0){
                throw new Exception("No contracts available.");
            }

            return new GetContractsRes("Successfully retrieved contracts by vendor ID", false, returnList);
        }catch(Exception ex){
            ex.printStackTrace();
            return new GetContractsRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

    public GetContractsRes getContractsByVendorId(String vendorId){
        try {
            logger.info("Getting contracts by vendor ID");
            List<ContractModel> returnList = new ArrayList<>();
            List<Contract> contractList = contractRepository.findContractsByVendorId(vendorId);

            for(Contract contract: contractList){
                if(contract.getDeleted()){
                    continue;
                }

                ContractModel contractModel = transformToContractModel(contract);
                returnList.add(contractModel);
            }

            if(returnList.size() == 0){
                throw new Exception("No contracts available.");
            }

            return new GetContractsRes("Successfully retrieved contracts by vendor ID", false, returnList);
        }catch(Exception ex){
            ex.printStackTrace();
            return new GetContractsRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }
}