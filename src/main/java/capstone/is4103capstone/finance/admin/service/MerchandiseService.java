package capstone.is4103capstone.finance.admin.service;

import capstone.is4103capstone.entities.finance.BudgetSub2;
import capstone.is4103capstone.entities.finance.Merchandise;
import capstone.is4103capstone.entities.supplyChain.Contract;
import capstone.is4103capstone.entities.supplyChain.ContractLine;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.finance.Repository.BudgetSub2Repository;
import capstone.is4103capstone.finance.Repository.MerchandiseRepository;
import capstone.is4103capstone.finance.admin.EntityCodeHPGeneration;
import capstone.is4103capstone.finance.admin.model.MerchandiseModel;
import capstone.is4103capstone.finance.admin.model.req.CreateMerchandiseRequest;
import capstone.is4103capstone.finance.admin.model.res.MerchandiseListRes;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.supplychain.Repository.ContractLineRepository;
import capstone.is4103capstone.supplychain.Repository.ContractRepository;
import capstone.is4103capstone.supplychain.Repository.VendorRepository;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.MarshalledObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class MerchandiseService {
    private static final Logger logger = LoggerFactory.getLogger(MerchandiseService.class);
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    MerchandiseRepository merchandiseRepository;
    @Autowired
    BudgetSub2Repository sub2Repository;
    @Autowired
    VendorRepository vendorRepository;
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    ContractLineRepository contractLineRepository;


    //assume you can give either vendor code or id?
    @Transactional
    public JSONObject createMerchandise(CreateMerchandiseRequest req){
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

            Merchandise newItem = new Merchandise(req.getItemName().trim(),req.getMeasureUnit());
            newItem.setCreatedBy(req.getUsername());
            newItem.setHierachyPath(EntityCodeHPGeneration.setHP(sub2,newItem));
            Authentication.configurePermissionMap(newItem);
            newItem = merchandiseRepository.save(newItem);

            newItem.setCode(EntityCodeHPGeneration.getCode(merchandiseRepository,newItem));
            newItem.setVendor(vendor);
            newItem.setBudgetSub2(sub2);
            vendor.getMerchandises().size();
            sub2.getMerchandises().size();
            vendor.getMerchandises().add(newItem);
            sub2.getMerchandises().add(newItem);


            JSONObject res = new JSONObject();

            MerchandiseModel newItemModel = new MerchandiseModel(newItem.getObjectName(),newItem.getCode(),vendor.getCode(),vendor.getObjectName(),newItem.getMeasureUnit());

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
    public MerchandiseListRes viewMerchandiseItemsBySub2(String sub2Id){
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


            List<Merchandise> merchandises = merchandiseRepository.findMerchandiseByBudgetSub2Id(sub2.getId());
            List<MerchandiseModel> mlist = new ArrayList<>();
            MerchandiseModel model;
            for (Merchandise m : merchandises){
                model = new MerchandiseModel(m.getObjectName(),m.getCode(),m.getVendor().getCode(),m.getVendor().getObjectName(),m.getMeasureUnit());
                model = retrieveContractInformation(m,model);
                mlist.add(model);
            }
            return new MerchandiseListRes("Successfully retrieved merchandises under sub2["+sub2.getCode()+"]",false,mlist,mlist.size());
        }catch (Exception e){
            return new MerchandiseListRes(e.getMessage(),true);
        }

    }

    private MerchandiseModel retrieveContractInformation(Merchandise m, MerchandiseModel basicModel){
        //check active contract;
        if (m.getCurrentContractCode() == null || m.getCurrentContractCode().isEmpty()){
            basicModel.setHasActiveContract(false);
            return basicModel;
        }
        Contract contract = contractRepository.findContractByCode(m.getCurrentContractCode());
        if (contract == null || contractHasExpired(contract) || contract.getDeleted()){
            logProblem("[Internal Error]Contract has problem, either incorrect code or expired contract, or contract is deleted");
            basicModel.setHasActiveContract(false);
            return basicModel;
        }

        Optional<ContractLine> clOptional = contractLineRepository.findContractLineByMerchandiseCodeAndContractId(m.getCode(),contract.getId());
        if (!clOptional.isPresent()){
            logProblem("[Internal Error]No such merchandise in the contract");
            basicModel.setHasActiveContract(false);
            return basicModel;
        }
        ContractLine cl = clOptional.get();

        if (!cl.getPrice().equals(m.getCurrentPrice())){
            logProblem("[Internal Error]Price doesn't equalize, some problem, will take the price in contract line.");
        }

        basicModel.setCurrentPrice(cl.getPrice());
        basicModel.setHasActiveContract(true);
        basicModel.setCurrentContractCode(m.getCurrentContractCode());
        basicModel.setContractStartDate(formatter.format(contract.getStartDate()));
        basicModel.setContractEndDate(formatter.format(contract.getEndDate()));

        return basicModel;



    }

    private boolean contractHasExpired(Contract c){
        Date today = new Date();
        Date endDate = c.getEndDate() == null? new Date(Long.MAX_VALUE) : c.getEndDate();
        int r1 = c.getStartDate().compareTo(today);
        int r2 = today.compareTo(endDate);
        return c.getStartDate().compareTo(today) * today.compareTo(endDate) < 0;
    }

    private void checkRepeatedName(String sub2Id, String vendorId, String name) throws Exception {
        boolean result = merchandiseRepository.countMerchandisesByVendor(sub2Id,vendorId,name.trim()) > 0;
        if (result){
            throw new Exception("This item already exists for the vendor and sub2 category");
        }
    }

    private void logProblem(String message){

    }
}
