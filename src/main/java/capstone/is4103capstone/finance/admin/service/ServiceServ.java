package capstone.is4103capstone.finance.admin.service;

import capstone.is4103capstone.entities.finance.BudgetSub2;
import capstone.is4103capstone.entities.finance.Service;
import capstone.is4103capstone.entities.supplyChain.Contract;
import capstone.is4103capstone.entities.supplyChain.ChildContract;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.finance.Repository.BudgetSub2Repository;
import capstone.is4103capstone.finance.Repository.ServiceRepository;
import capstone.is4103capstone.finance.admin.EntityCodeHPGeneration;
import capstone.is4103capstone.finance.admin.model.ServiceModel;
import capstone.is4103capstone.finance.admin.model.req.CreateServiceRequest;
import capstone.is4103capstone.finance.admin.model.res.ServiceListRes;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.supplychain.Repository.ChildContractRepository;
import capstone.is4103capstone.supplychain.Repository.ContractRepository;
import capstone.is4103capstone.supplychain.Repository.VendorRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Service
public class ServiceServ {
    private static final Logger logger = LoggerFactory.getLogger(ServiceServ.class);
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    BudgetSub2Repository sub2Repository;
    @Autowired
    VendorRepository vendorRepository;
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    ChildContractRepository childContractRepository;


    //assume you can give either vendor code or id?
    @Transactional
    public JSONObject createservice(CreateServiceRequest req){
        try{
            Vendor vendor = vendorRepository.findVendorByCode(req.getVendorCode());
            if (vendor == null || vendor.getDeleted()){
                Optional<Vendor> v = vendorRepository.findById(req.getVendorCode());
                if (v.isPresent())
                    vendor = v.get();
                else{
                    throw new Exception("Vendor with code/id ["+req.getVendorCode()+"] does not exists!");
                }
            }

            BudgetSub2 sub2 = sub2Repository.findBudgetSub2ByCode(req.getSub2Code());
            if (sub2 == null || sub2.getDeleted()){
                Optional<BudgetSub2> s = sub2Repository.findById(req.getSub2Code());;
                if (s.isPresent())
                    sub2 = s.get();
                else{
                    throw new Exception("Sub2 category with code/id ["+req.getVendorCode()+"] does not exists!");
                }
            }


            checkRepeatedName(sub2.getId(), vendor.getId(),req.getItemName());

            if (req.getMeasureUnit()==null){
                throw new Exception("Measure Unit field cannot be null!");
            }

            Service newItem = new Service(req.getItemName().trim(),req.getMeasureUnit(),req.getReferencePrice(),req.getCurrency());
            newItem.setCreatedBy(req.getUsername());
            newItem.setHierachyPath(EntityCodeHPGeneration.setHP(sub2,newItem));
            Authentication.configurePermissionMap(newItem);
            newItem = serviceRepository.save(newItem);

            newItem.setCode(EntityCodeHPGeneration.getCode(serviceRepository,newItem));
//            newItem.setVendor(vendor);
            newItem.setBudgetSub2(sub2);
//            vendor.getservices().size();
//            sub2.getservices().size();
//            vendor.getservices().add(newItem);
//            sub2.getservices().add(newItem);


            JSONObject res = new JSONObject();

            ServiceModel newItemModel = new ServiceModel(newItem.getObjectName(),newItem.getCode(),newItem.getMeasureUnit(),newItem.getReferencePrice(),newItem.getCurrencyCode());

            res.put("newItem", new JSONObject(newItemModel));

            res.put("message","Successfully created new item");
            res.put("hasError",false);

            return res;
        }catch (Exception e){
            JSONObject res = new JSONObject();
            res.put("hasError",true);
            res.put("message",e.getMessage());
            return res;
        }
    }

    //sub2 can be either code or id will take as code by default and double check id;
    public ServiceListRes viewserviceItemsBySub2(String sub2Id){
        try{
            BudgetSub2 sub2 = sub2Repository.findBudgetSub2ByCode(sub2Id);
            if (sub2 == null || sub2.getDeleted()){
                Optional<BudgetSub2> s = sub2Repository.findById(sub2Id);;
                if (s.isPresent())
                    sub2 = s.get();
                else{
                    throw new Exception("Sub2 category with code/id ["+sub2Id+"] does not exists!");
                }
            }


            List<Service> services = serviceRepository.findServiceByBudgetSub2Id(sub2.getId());
            List<ServiceModel> mlist = new ArrayList<>();
            ServiceModel model;
            for (Service m : services){
                model = new ServiceModel(m.getObjectName(),m.getCode(),m.getMeasureUnit(),m.getReferencePrice(),m.getCurrencyCode());
                //model = retrieveContractInformation(m,model);
                mlist.add(model);
            }
            return new ServiceListRes("Successfully retrieved services under sub2["+sub2.getCode()+"]",false,mlist,mlist.size());
        }catch (Exception e){
            return new ServiceListRes(e.getMessage(),true);
        }

    }

//    private ServiceModel retrieveContractInformation(Service m, ServiceModel basicModel){
//        //check active contract;
//        if (m.getCurrentContractCode() == null || m.getCurrentContractCode().isEmpty()){
//            basicModel.setHasActiveContract(false);
//            return basicModel;
//        }
//        Contract contract = contractRepository.findContractByCode(m.getCurrentContractCode());
//        if (contract == null || contractHasExpired(contract) || contract.getDeleted()){
//            logProblem("[Internal Error]Contract has problem, either incorrect code or expired contract, or contract is deleted");
//            basicModel.setHasActiveContract(false);
//            return basicModel;
//        }
//
//        Optional<ChildContract> clOptional = childContractRepository.findChildContractByMerchandiseCodeAndContractId(m.getCode(),contract.getId());
//        if (!clOptional.isPresent()){
//            logProblem("[Internal Error]No such service in the contract");
//            basicModel.setHasActiveContract(false);
//            return basicModel;
//        }
//        ChildContract cl = clOptional.get();
//
//        if (!cl.getPrice().equals(m.getCurrentPrice())){
//            logProblem("[Internal Error]Price doesn't equalize, some problem, will take the price in contract line.");
//        }
//
//        basicModel.setCurrentPrice(cl.getPrice());
//        basicModel.setHasActiveContract(true);
//        basicModel.setCurrentContractCode(m.getCurrentContractCode());
//        basicModel.setContractStartDate(formatter.format(contract.getStartDate()));
//        basicModel.setContractEndDate(formatter.format(contract.getEndDate()));
//
//        return basicModel;
//
//
//
//    }

    private boolean contractHasExpired(Contract c){
        Date today = new Date();
        Date endDate = c.getEndDate() == null? new Date(Long.MAX_VALUE) : c.getEndDate();
        int r1 = c.getStartDate().compareTo(today);
        int r2 = today.compareTo(endDate);
        return c.getStartDate().compareTo(today) * today.compareTo(endDate) < 0;
    }

    private void checkRepeatedName(String sub2Id, String vendorId, String name) throws Exception {
        boolean result = serviceRepository.countServicesByVendor(sub2Id,vendorId,name.trim()) > 0;
        if (result){
            throw new Exception("This item already exists for the vendor and sub2 category");
        }
    }

    public Service validateService(String idOrCode) throws EntityNotFoundException {
        Optional<Service> service = serviceRepository.findById(idOrCode);
        if (service.isPresent())
            if (!service.get().getDeleted())
                return service.get();
            else
                throw new EntityNotFoundException("Product or service already deleted.");

        Service e = serviceRepository.findServiceByCode(idOrCode);
        if (e != null && !e.getDeleted())
            return e;

        throw new EntityNotFoundException("Product or service code or id not valid");
    }

    private void logProblem(String message){

    }
}
