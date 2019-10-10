package capstone.is4103capstone.supplychain.service;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.admin.repository.TeamRepository;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.entities.supplyChain.Contract;
import capstone.is4103capstone.entities.supplyChain.ContractLine;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.model.GeneralEntityModel;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.Repository.ContractLineRepository;
import capstone.is4103capstone.supplychain.Repository.ContractRepository;
import capstone.is4103capstone.supplychain.Repository.VendorRepository;
import capstone.is4103capstone.supplychain.SCMEntityCodeHPGeneration;
import capstone.is4103capstone.supplychain.model.ContractModel;
import capstone.is4103capstone.supplychain.model.req.CreateContractReq;
import capstone.is4103capstone.supplychain.model.res.GetContractsRes;
import capstone.is4103capstone.supplychain.model.res.GetContractRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContractService {
    private static final Logger logger = LoggerFactory.getLogger(ContractService.class);
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    ContractLineRepository contractLineRepository;
    @Autowired
    VendorRepository vendorRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    TeamRepository teamRepository;

    //contract and contractLine will be created together.
    public GeneralRes CreateContract(CreateContractReq createContractReq){
        try{
            Contract newContract = new Contract();
            newContract.setContractStatus(createContractReq.getContractStatus());
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
            newContract.setDeleted(false);
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

            Team team  = teamRepository.getOne(createContractReq.getTeamId());
            newContract.setTeam(team);
            team.getContracts().add(newContract);

            newContract = contractRepository.saveAndFlush(newContract);

            if(createContractReq.getContractLineList() != null && createContractReq.getContractLineList().size() != 0){
                List<ContractLine> savedContractLineList = createContractLine(newContract, createContractReq.getContractLineList(), createContractReq);
                newContract.setContractLines(savedContractLineList);
            }

            newContract.setCode(SCMEntityCodeHPGeneration.getCode(contractRepository,newContract));
            contractRepository.saveAndFlush(newContract);
            vendorRepository.saveAndFlush(vendor);
            employeeRepository.saveAndFlush(employeeInCharge);
            teamRepository.saveAndFlush(team);
            logger.info("Successfully created new contract! -- "+createContractReq.getModifierUsername()+" "+new Date());
            return new GeneralRes("Successfully created new contract!", false);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: "+ex.getMessage(), true);
        }
    }

    private List<ContractLine> createContractLine(Contract contract, List<ContractLine> contractLineList, CreateContractReq createContractReq){
        List<ContractLine> newContractLineList = new ArrayList<>();
        for(ContractLine e: contractLineList){
            e.setCreatedBy(createContractReq.getModifierUsername());
            e.setLastModifiedBy(createContractReq.getModifierUsername());
            e.setContract(contract);
            contractLineRepository.saveAndFlush(e);
            logger.info("Successfully created new contract line!");
            newContractLineList.add(e);
        }
        return newContractLineList;
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

    public GeneralRes UpdateContract (CreateContractReq updateContractReq, String id) {
        try {
            Contract contract = contractRepository.getOne(id);
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

            if (updateContractReq.getContractLineList() != null && updateContractReq.getContractLineList().size() != 0) {
                contract = contractRepository.saveAndFlush(contract);
                List<ContractLine> updatedContractLineList = updateContractLine(updateContractReq);

                contract.setContractLines(updatedContractLineList);
                contract = contractRepository.saveAndFlush(contract);
            }

            contractRepository.saveAndFlush(contract);
            return new GeneralRes("Successfully updated the contract!", false);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: " + ex.getMessage(), true);
        }
    }

    private List<ContractLine> updateContractLine(CreateContractReq updatedContractReq){
        List<ContractLine> updatedContractLineList = new ArrayList<>();
        for(ContractLine e: updatedContractReq.getContractLineList()){
            ContractLine contractLine = contractLineRepository.getOne(e.getId());
            if(e.getPrice() != null){
                contractLine.setPrice(e.getPrice());
            }
            if(e.getCurrencyCode() != null){
                contractLine.setCurrencyCode(e.getCurrencyCode());
            }
            if(e.getMerchandiseCode() != null){
                contractLine.setMerchandiseCode(e.getMerchandiseCode());
            }
            if(e.getObjectName() != null){
                contractLine.setObjectName(e.getObjectName());
            }
            contractLine.setLastModifiedBy(updatedContractReq.getModifierUsername());

            contractLine = contractLineRepository.saveAndFlush(contractLine);
            updatedContractLineList.add(contractLine);
        }
        return updatedContractLineList;
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
                contract.getObjectName(), contract.getCode(), contract.getId(), contract.getSeqNo(),
                contract.getPurchaseType(), contract.getSpendType(), contract.getContractTerm(),
                contract.getContractType(), contract.getContractStatus(), contract.getNoticeDaysToExit(),
                vendor, employeeInChargeContract, team, contract.getTotalContractValue(),
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