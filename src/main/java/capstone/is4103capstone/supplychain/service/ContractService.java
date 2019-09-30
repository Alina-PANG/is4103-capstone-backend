package capstone.is4103capstone.supplychain.service;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.supplyChain.Contract;
import capstone.is4103capstone.entities.supplyChain.ContractLine;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.general.model.GeneralEntityModel;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.Repository.ContractLineRepository;
import capstone.is4103capstone.supplychain.Repository.ContractRepository;
import capstone.is4103capstone.supplychain.Repository.VendorRepository;
import capstone.is4103capstone.supplychain.model.ContractModel;
import capstone.is4103capstone.supplychain.model.req.CreateContractReq;
import capstone.is4103capstone.supplychain.model.res.GetAllContractsRes;
import capstone.is4103capstone.supplychain.model.res.GetContractRes;
import com.sun.tools.javah.Gen;
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
            newContract.setCreatedDateTime(new Date());
            newContract.setLastModifiedDateTime(new Date());
            newContract.setDeleted(false);

            Vendor vendor = vendorRepository.getOne(createContractReq.getVendorId());
            newContract.setVendor(vendor);
            vendor.getContracts().add(newContract);

            Employee employeeInCharge = employeeRepository.getOne(createContractReq.getEmployeeInChargeId());
            newContract.setEmployeeInChargeContract(employeeInCharge);
            employeeInCharge.getContractInCharged().add(newContract);

            newContract = contractRepository.saveAndFlush(newContract);
            if(createContractReq.getContractLineList() != null && createContractReq.getContractLineList().size() != 0){
                List<ContractLine> savedContractLineList = createContractLine(newContract, createContractReq.getContractLineList(), createContractReq);
                newContract.setContractLines(savedContractLineList);
            }

            contractRepository.saveAndFlush(newContract);
            vendorRepository.saveAndFlush(vendor);
            employeeRepository.saveAndFlush(employeeInCharge);
            logger.info("Successully created new contract! -- "+createContractReq.getModifierUsername()+" "+new Date());
            return new GeneralRes("Succuesfully created new contract!", false);
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
            e.setCreatedDateTime(new Date());
            e.setLastModifiedDateTime(new Date());
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

    public GetAllContractsRes getAllContracts(){
        try {
            logger.info("Getting all contracts");
            List<ContractModel> returnList = new ArrayList<>();
            List<Contract> contractList = contractRepository.findAll();

            if(contractList.size() == 0){
                throw new Exception("No contract available.");
            }

            for(Contract contract: contractList){
                if(contract.getDeleted()){
                    continue;
                }

                ContractModel contractModel = transformToContractModel(contract);
                returnList.add(contractModel);
            }

            return new GetAllContractsRes("Successfully retrieved all contracts", false, returnList);
        }catch(Exception ex){
            ex.printStackTrace();
            return new GetAllContractsRes("An unexpected error happens: "+ex.getMessage(), true, null);
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

            contract.setLastModifiedBy(updateContractReq.getModifierUsername());
            contract.setLastModifiedDateTime(new Date());

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
            contractLine.setLastModifiedDateTime(new Date());
            contractLine.setLastModifiedBy(updatedContractReq.getModifierUsername());

            contractLine = contractLineRepository.saveAndFlush(contractLine);
            updatedContractLineList.add(contractLine);
        }
        return updatedContractLineList;
    }

    public ContractModel transformToContractModel(Contract contract){
        GeneralEntityModel vendor = new GeneralEntityModel(contract.getVendor());
        GeneralEntityModel employeeInChargeContract = new GeneralEntityModel(contract.getEmployeeInChargeContract());

        ContractModel contractModel = new ContractModel(
                contract.getObjectName(), contract.getCode(), contract.getId(),
                contract.getPurchaseType(), contract.getSpendType(), contract.getContractTerm(),
                contract.getContractType(), contract.getContractStatus(), contract.getNoticeDaysToExit(),
                vendor, employeeInChargeContract,
                contract.getStartDate(), contract.getEndDate(), contract.getRenewalStartDate(), contract.getCpgReviewAlertDate());

        return contractModel;
    }
}