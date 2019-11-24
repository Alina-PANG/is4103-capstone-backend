package capstone.is4103capstone.supplychain.service;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.entities.finance.Service;
import capstone.is4103capstone.entities.supplyChain.ChildContract;
import capstone.is4103capstone.entities.supplyChain.Contract;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.finance.Repository.ServiceRepository;
import capstone.is4103capstone.finance.admin.service.FXTableService;
import capstone.is4103capstone.general.AuthenticationTools;
import capstone.is4103capstone.general.model.GeneralEntityModel;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.Repository.ChildContractRepository;
import capstone.is4103capstone.supplychain.Repository.ContractRepository;
import capstone.is4103capstone.supplychain.SCMEntityCodeHPGeneration;
import capstone.is4103capstone.supplychain.model.ChildContractModel;
import capstone.is4103capstone.supplychain.model.ContractServiceModel;
import capstone.is4103capstone.supplychain.model.req.CreateChildContractReq;
import capstone.is4103capstone.supplychain.model.res.GetChildContractRes;
import capstone.is4103capstone.supplychain.model.res.GetChildContractsRes;
import capstone.is4103capstone.util.enums.ContractStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class ChildContractService {
    private static final Logger logger = LoggerFactory.getLogger(VendorService.class);

    @Autowired
    ContractRepository contractRepository;
    @Autowired
    ChildContractRepository childContractRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    FXTableService fxService;

    public GeneralRes createChildContract(CreateChildContractReq createChildContractReq){
        try{
            ChildContract newChildContract = new ChildContract();
            newChildContract.setObjectName(createChildContractReq.getChildContractName());
            newChildContract.setCreatedBy(createChildContractReq.getModifierUsername());
            newChildContract.setLastModifiedBy(createChildContractReq.getModifierUsername());
            newChildContract.setContractValue(createChildContractReq.getContractValue());

            Service service =  serviceRepository.findServiceByCode(createChildContractReq.getServiceCode());
            if(service != null) {
                newChildContract.setServiceCode(createChildContractReq.getServiceCode());
            }
            else{
                logger.info("Wrong service code!");
                return new GeneralRes("Wrong service code! No existing service with this service code!", true);
            }

            Contract masterContract = contractRepository.getOne(createChildContractReq.getMasterContractID());
            newChildContract.setMasterContract(masterContract);
            masterContract.getChildContractList().add(newChildContract);

            newChildContract.setContractValueInGBP(fxService.convertToGBPWithLatest(masterContract.getCurrencyCode(),newChildContract.getContractValue()));

            newChildContract = childContractRepository.saveAndFlush(newChildContract);
            if(newChildContract.getSeqNo() == null){
                newChildContract.setSeqNo((long) childContractRepository.findAll().size());
            }

            AuthenticationTools.configurePermissionMap(newChildContract);

            newChildContract = childContractRepository.saveAndFlush(newChildContract);
            newChildContract.setCode(SCMEntityCodeHPGeneration.getCode(childContractRepository, newChildContract));
            childContractRepository.saveAndFlush(newChildContract);
            contractRepository.saveAndFlush(masterContract);

            logger.info("Successfully created new child contract under master contract! -- "+masterContract.getCode());
            return new GeneralRes("Successfully created new child contract!", false);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return new GeneralRes(ex.getMessage(), true);
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
            return new GetChildContractsRes(ex.getMessage(), true, null);
        }
    }

    //cannot change master contract
    public GeneralRes updateChildContract (CreateChildContractReq updateChildContractReq, String id) {
        try {
            ChildContract childContract = childContractRepository.getOne(id);
            Contract itsMaster = childContract.getMasterContract();
            if(updateChildContractReq.getChildContractName() != null){
                childContract.setObjectName(updateChildContractReq.getChildContractName());
            }
            if (updateChildContractReq.getServiceCode() != null) {
                childContract.setServiceCode(updateChildContractReq.getServiceCode());
            }
            if(updateChildContractReq.getContractValue() != null){
                childContract.setContractValue(updateChildContractReq.getContractValue());
                childContract.setContractValueInGBP(fxService.convertToGBPWithLatest(itsMaster.getCurrencyCode(),childContract.getContractValue()));
            }

            childContract.setLastModifiedBy(updateChildContractReq.getModifierUsername());
            childContractRepository.saveAndFlush(childContract);
            return new GeneralRes("Successfully updated the child contract!", false);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new GeneralRes( ex.getMessage(), true);
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
            return new GetChildContractRes(ex.getMessage(), true, null);
        }
    }

    public ChildContractModel transformToChildContractModel(ChildContract childContract) throws Exception{
        GeneralEntityModel masterContract = null;

        String serviceName = null;

        if(childContract.getMasterContract() != null) {
            masterContract = new GeneralEntityModel(childContract.getMasterContract());
        }

        boolean canUpdate = false;
        ContractStatusEnum contractStatus = childContract.getMasterContract().getContractStatus();

        if(contractStatus.equals(ContractStatusEnum.REJECTED)||contractStatus.equals(ContractStatusEnum.DRAFT)){
            canUpdate = true;
        }

        Service service = serviceRepository.findServiceByCode(childContract.getServiceCode());
        if(service != null) {
            serviceName = service.getObjectName();
        }

        return new ChildContractModel(
                childContract.getObjectName(), childContract.getCode(), childContract.getId(),
                childContract.getSeqNo(), serviceName, childContract.getContractValue(),
                masterContract, childContract.getMasterContract().getCurrencyCode(), canUpdate);
    }


    public List<ContractServiceModel> getChildContractByServiceId(String serviceCode){
        List<ContractServiceModel> result = new ArrayList<>();
        List<ChildContract> childContractList = childContractRepository.findChildContractsByServiceCode(serviceCode);

        for(ChildContract cc: childContractList){
            Contract masterContract = cc.getMasterContract();

            //check whether these child contract is active
            if(masterContract.getContractStatus().equals(ContractStatusEnum.ACTIVE) || masterContract.getContractStatus().equals(ContractStatusEnum.MERGED)){
                Vendor vendor = masterContract.getVendor();

                //create ContractServiceModel
                ContractServiceModel contractServiceModel = new ContractServiceModel(
                        vendor.getObjectName(), vendor.getId(),
                        masterContract.getStartDate(), masterContract.getEndDate());

                result.add(contractServiceModel);
            }
        }
        return result;
    }
}
