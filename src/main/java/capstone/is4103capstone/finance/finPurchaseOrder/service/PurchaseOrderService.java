package capstone.is4103capstone.finance.finPurchaseOrder.service;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.finance.*;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.finance.Repository.BjfRepository;
import capstone.is4103capstone.finance.Repository.PurchaseOrderRepository;
import capstone.is4103capstone.finance.Repository.SpendingRecordRepository;
import capstone.is4103capstone.finance.admin.service.FXTableService;
import capstone.is4103capstone.finance.admin.service.ServiceServ;
import capstone.is4103capstone.finance.finPurchaseOrder.POEntityCodeHPGeneration;
import capstone.is4103capstone.finance.finPurchaseOrder.model.POModel;
import capstone.is4103capstone.finance.finPurchaseOrder.model.SpendingModel;
import capstone.is4103capstone.finance.finPurchaseOrder.model.req.CreatePOReq;
import capstone.is4103capstone.finance.finPurchaseOrder.model.res.GetPurchaseOrderListRes;
import capstone.is4103capstone.finance.finPurchaseOrder.model.res.GetPurchaseOrderRes;
import capstone.is4103capstone.finance.requestsMgmt.service.BJFService;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.ApprovalTicketService;
import capstone.is4103capstone.supplychain.Repository.VendorRepository;
import capstone.is4103capstone.supplychain.service.VendorService;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import org.apache.poi.ss.SpreadsheetVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class PurchaseOrderService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    VendorService vendorService;
    @Autowired
    BjfRepository bjfRepository;
    @Autowired
    BJFService bjfService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ServiceServ serviceServ;
    @Autowired
    SpendingRecordRepository spendingRecordRepository;
    @Autowired
    FXTableService fxService;

    public ResponseEntity<GeneralRes> createPO(CreatePOReq createPOReq){
        logger.info("Creating new purchase order...");
//        boolean firstTimeCreate = true;
        try{
            PurchaseOrder purchaseOrder = new PurchaseOrder();

            if (createPOReq.getPoNumber() == null || createPOReq.getPoNumber().isEmpty())
                throw new Exception("PO Number cannot be null or empty.");

            Vendor v = vendorService.validateVendor(createPOReq.getVendorid());

            if (createPOReq.getSpendingRecordList().isEmpty())
                throw new Exception("Please select at least 1 service");

            String currentUsername = employeeService.getCurrentLoginUsername();
            purchaseOrder.setVendor(v);
            purchaseOrder.setStatus(ApprovalStatusEnum.APPROVED);
            purchaseOrder.setTotalAmount(createPOReq.getAmount());
            purchaseOrder.setCurrencyCode(createPOReq.getCurrencyCode());
            purchaseOrder.setTotalAmtInGBP(fxService.convertToGBPWithLatest(purchaseOrder.getCurrencyCode(),purchaseOrder.getTotalAmount()));
            purchaseOrder.setCreatedBy(currentUsername);
            purchaseOrder.setRelatedBJF(createPOReq.getRelatedBJF());
            purchaseOrder.setCode(createPOReq.getPoNumber());
            purchaseOrder.setObjectName(createPOReq.getPoNumber());

            purchaseOrder.setCountryId(employeeService.getCountryEmployeeBelongTo(currentUsername).getId());

            purchaseOrder = purchaseOrderRepository.saveAndFlush(purchaseOrder);
            purchaseOrder.setApprover(createPOReq.getApproverUsername());
            if(purchaseOrder.getSeqNo() == null){
                purchaseOrder.setSeqNo(new Long(purchaseOrderRepository.findAll().size()));
            }
            logger.info("Creating purchase order with related BJF: "+createPOReq.getRelatedBJF());
            purchaseOrder = purchaseOrderRepository.saveAndFlush(purchaseOrder);
//            purchaseOrder.setCode(POEntityCodeHPGeneration.getCode(purchaseOrderRepository,purchaseOrder));

            List<SpendingRecord> spendings = new ArrayList<>();
            int codeCnt = 0;
            //去重

            for (SpendingRecord spending:createPOReq.getSpendingRecordList()){
                Service service = serviceServ.validateService(spending.getServiceId());
                SpendingRecord s = new SpendingRecord(service.getId(),spending.getSpendingAmt(),purchaseOrder.getCurrencyCode(),purchaseOrder,service.getObjectName());
                s.setSpendingAmtInGBP(fxService.convertToGBPWithLatest(s.getCurrencyCode(),s.getSpendingAmt()));
                s.setCode("SP-"+purchaseOrder.getCode()+"-"+service.getCode()+"-"+codeCnt);
                s = spendingRecordRepository.save(s);
                spendings.add(s);
                codeCnt ++;
            }

            purchaseOrder.setDetailedSpending(spendings);
            purchaseOrderRepository.saveAndFlush(purchaseOrder);
            bjfService.afterPOUpdated(purchaseOrder);
            return ResponseEntity
                    .ok()
                    .body(new GeneralRes("Successfully Created The PO! ID["+purchaseOrder.getId()+"]", false));
        } catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    @Transactional
    public ResponseEntity<GeneralRes> updatePO(CreatePOReq createPOReq, String id) throws Exception{
        PurchaseOrder purchaseOrder = purchaseOrderRepository.getOne(id);
        if (purchaseOrder == null)
            throw new EntityNotFoundException("PO not found.");

        if (createPOReq.getSpendingRecordList().isEmpty())
            throw new Exception("Please select at least 1 service");

        purchaseOrder.setTotalAmount(createPOReq.getAmount());
        purchaseOrder.setCurrencyCode(createPOReq.getCurrencyCode());
        purchaseOrder.setTotalAmtInGBP(fxService.convertToGBPWithLatest(purchaseOrder.getCurrencyCode(),purchaseOrder.getTotalAmount()));

        purchaseOrder.setLastModifiedBy(employeeService.getCurrentLoginUsername());
        purchaseOrder.setRelatedBJF(createPOReq.getRelatedBJF());

        purchaseOrder = purchaseOrderRepository.saveAndFlush(purchaseOrder);

        List<SpendingRecord> spendings = purchaseOrder.getDetailedSpending();
        //去重

        for (SpendingRecord existing: spendings){
            existing.setDeleted(true);
            existing.setSpendingAmt(BigDecimal.ZERO);
            existing.setCode(null);
            existing.setRelatedPO(null);
            spendingRecordRepository.saveAndFlush(existing);
        }
        purchaseOrder.setDetailedSpending(new ArrayList<>());

        int codeCnt = 0;
        for (SpendingRecord spending:createPOReq.getSpendingRecordList()){
            Service requestServ = serviceServ.validateService(spending.getServiceId());
            SpendingRecord s = new SpendingRecord(requestServ.getId(),spending.getSpendingAmt(),purchaseOrder.getCurrencyCode(),purchaseOrder,requestServ.getObjectName());
            s.setSpendingAmtInGBP(fxService.convertToGBPWithLatest(s.getCurrencyCode(),s.getSpendingAmt()));
            s.setCode("SP-"+purchaseOrder.getCode()+"-"+requestServ.getCode()+"-"+codeCnt);
            s = spendingRecordRepository.saveAndFlush(s);
            spendings.add(s);
            codeCnt ++;
        }

        purchaseOrder.setDetailedSpending(spendings);
        System.out.println("IsClosePOInstruct "+createPOReq.isClosePOInstruc());
        if (createPOReq.isClosePOInstruc()) {
            bjfService.purchaseOrderClosed(purchaseOrder);
            purchaseOrder.setStatus(ApprovalStatusEnum.CLOSED);
        }
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        return ResponseEntity
                .ok()
                .body(new GeneralRes("Successfully updated The PO! ID["+purchaseOrder.getId()+"]", false));
    }

    public ResponseEntity<GeneralRes> getPO(String id){
        try{
            PurchaseOrder po = purchaseOrderRepository.getOne(id);
            Vendor v = po.getVendor();
            List<String> bjfList = po.getRelatedBJF();
            List<String> bjfCode = new ArrayList<>();
            for(String bjfId: bjfList){
                BJF bjf = bjfService.validateBJF(bjfId);
                bjfCode.add(bjf.getCode());
            }
            for(StatementOfAcctLineItem l: po.getStatementOfAccount()){
            }
            if(po == null) return ResponseEntity
                    .notFound().build();

            POModel model = new POModel();
            model.setCurrencyCode(po.getCurrencyCode());
            model.setTotalAmount(po.getTotalAmount());
            model.setVendorId(v.getId());
            model.setVendorName(v.getObjectName());
            model.setCode(po.getCode());
            model.setId(po.getId());
            po.getStatementOfAccount().size();
            model.setTotalPaidAmount(calculatePOPaidAmt(po));

            po.getDetailedSpending();
            List<SpendingModel> spendings = new ArrayList<>();
            for (SpendingRecord s:po.getDetailedSpending()){
                if (s.getDeleted())
                    continue;
                spendings.add(new SpendingModel(s));
            }
            model.setServicesSpending(spendings);
            model.setPoStatus(po.getStatus().name());
            return ResponseEntity.ok().body(new GetPurchaseOrderRes("Successfully retrieved the purchase order!", true, model, bjfCode));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    public ResponseEntity<GeneralRes> getAllPO(){
        try{
            Employee currEmp = employeeService.getCurrentLoginEmployee();
            if (!currEmp.getCode().toUpperCase().contains("PO_TEAM")){
                throw new  Exception("You are not allowed to access this page.");
            }
            List<PurchaseOrder> list = purchaseOrderRepository.findAll();
            List<PurchaseOrder> returnList = new ArrayList<>();
            for (PurchaseOrder o:list){
                if (o.getDeleted()){
                    continue;
                }
                returnList.add(o);
            }
            if(returnList == null) return ResponseEntity
                    .notFound().build();
            else return ResponseEntity.ok().body(new GetPurchaseOrderListRes("Successfully retrieved the purchase orders!", true, returnList));

        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    public ResponseEntity<GeneralRes> getPOAsApprover(String username){
        try{
            List<PurchaseOrder> list = purchaseOrderRepository.findAllByApprover(username);
            if(list == null) return ResponseEntity
                    .notFound().build();
            else return ResponseEntity.ok().body(new GetPurchaseOrderListRes("Successfully retrieved the purchase orders!", true, list));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }

    }

    public ResponseEntity<GeneralRes> getPOAsRequestor(String username){
        try{
            List<PurchaseOrder> list = purchaseOrderRepository.findAllByCreatedBy(username);
            if(list == null) return ResponseEntity
                    .notFound().build();
            else return ResponseEntity.ok().body(new GetPurchaseOrderListRes("Successfully retrieved the purchase orders!", true, list));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }

    }

    public ResponseEntity<GeneralRes> approvePO(String id, Boolean approved){
        try{
            logger.info("Getting purchase order with id "+id);
            Optional<PurchaseOrder> poOptional = purchaseOrderRepository.findById(id);
            if (!poOptional.isPresent()) {
                logger.info("Purchase Order Not Found!");
                return ResponseEntity
                        .notFound().build();
            }
            PurchaseOrder po = poOptional.get();
            if(po.getDeleted()) return ResponseEntity
                    .notFound().build();
            if(approved)  po.setStatus(ApprovalStatusEnum.APPROVED);
            else po.setStatus(ApprovalStatusEnum.REJECTED);
            purchaseOrderRepository.saveAndFlush(po);
            logger.info("Approving/Rejecting the purchase order created by "+po.getCreatedBy());
            bjfService.purchaseOrderApproved(po);

            return ResponseEntity.ok().body(new GeneralRes("Successfully approved/rejected the purchase orders!", true));

        }catch(Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    private BigDecimal calculatePOPaidAmt(PurchaseOrder po){
        try {
            List<StatementOfAcctLineItem> soa = po.getStatementOfAccount();
            BigDecimal total = BigDecimal.ZERO;
            for (StatementOfAcctLineItem l : soa) {
                total = total.add(l.getPaidAmt());
            }
            return total;
        }catch (Exception ex){
            return null;
        }
    }
}
