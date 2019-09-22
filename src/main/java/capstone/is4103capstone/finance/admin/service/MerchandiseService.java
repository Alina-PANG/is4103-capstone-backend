package capstone.is4103capstone.finance.admin.service;

import capstone.is4103capstone.entities.finance.BudgetSub2;
import capstone.is4103capstone.entities.finance.Merchandise;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.finance.Repository.BudgetSub2Repository;
import capstone.is4103capstone.finance.Repository.MerchandiseRepository;
import capstone.is4103capstone.finance.admin.EntityCodeHPGeneration;
import capstone.is4103capstone.finance.admin.model.MerchandiseModel;
import capstone.is4103capstone.finance.admin.model.req.CreateMerchandiseRequest;
import capstone.is4103capstone.supplychain.Repository.VendorRepository;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


//Merchandise must have vendor, may not have contract.
//I can just create the "Headset"-"Sony"
//so it just connect sub2 with vendor?
@Service
public class MerchandiseService {
    private static final Logger logger = LoggerFactory.getLogger(MerchandiseService.class);

    @Autowired
    MerchandiseRepository merchandiseRepository;
    @Autowired
    BudgetSub2Repository sub2Repository;
    @Autowired
    VendorRepository vendorRepository;


    //assume you can give either vendor code or id?
    @Transactional
    public JSONObject createMerchandise(CreateMerchandiseRequest req){
        try{
            Vendor vendor = vendorRepository.findVendorByCode(req.getVendorCode());
            if (vendor == null){
                Optional<Vendor> v = vendorRepository.findById(req.getVendorCode());
                if (v.isPresent())
                    vendor = v.get();
                else{
                    throw new Exception("Vendor with code/id ["+req.getVendorCode()+"] does not exists!");
                }
            }

            BudgetSub2 sub2 = sub2Repository.findBudgetSub2ByCode(req.getSub2Code());
            if (vendor == null){
                Optional<BudgetSub2> s = sub2Repository.findById(req.getSub2Code());;
                if (s.isPresent())
                    sub2 = s.get();
                else{
                    throw new Exception("Sub2 category with code/id ["+req.getVendorCode()+"] does not exists!");
                }
            }


            checkRepeatedName(sub2.getId(), vendor.getId(),req.getItemName());



            Merchandise newItem = new Merchandise(req.getItemName(),req.getMeasureUnit());
            newItem.setCreatedBy(req.getUsername());
            newItem.setHierachyPath(EntityCodeHPGeneration.setHP(sub2,newItem));
            newItem = merchandiseRepository.save(newItem);
            newItem.setCode(EntityCodeHPGeneration.getCode(merchandiseRepository,newItem));
            newItem.setVendor(vendor);
            newItem.setBudgetSub2(sub2);
            vendor.getMerchandises().add(newItem);
            sub2.getMerchandises().add(newItem);


            JSONObject res = new JSONObject();


            MerchandiseModel newItemModel = new MerchandiseModel(newItem.getObjectName(),newItem.getCode(),vendor.getCode(),vendor.getObjectName(),newItem.getMeasureUnit(),null);

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

    private void checkRepeatedName(String sub2Id, String vendorId, String name) throws Exception {
        boolean result = merchandiseRepository.countMerchandisesByVendor(sub2Id,vendorId,name) > 0;
        if (result){
            throw new Exception("This item already exists for the vendor and sub2 category");
        }
    }

}
