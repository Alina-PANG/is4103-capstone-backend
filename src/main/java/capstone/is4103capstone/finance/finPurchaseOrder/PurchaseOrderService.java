package capstone.is4103capstone.finance.finPurchaseOrder;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.finance.Plan;
import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.entities.finance.PurchaseOrderLineItem;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.finance.Repository.PurchaseOrderLineItemRepository;
import capstone.is4103capstone.finance.Repository.PurchaseOrderRepository;
import capstone.is4103capstone.finance.finPurchaseOrder.model.req.CreatePOReq;
import capstone.is4103capstone.finance.finPurchaseOrder.model.res.GetPurchaseOrderListRes;
import capstone.is4103capstone.finance.finPurchaseOrder.model.res.GetPurchaseOrderRes;
import capstone.is4103capstone.finance.purchaseOrder;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.ApprovalTicketService;
import capstone.is4103capstone.supplychain.Repository.VendorRepository;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import capstone.is4103capstone.util.enums.BudgetPlanStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseOrderService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    PurchaseOrderLineItemRepository purchaseOrderLineItemRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    VendorRepository vendorRepository;

    private void deleteItem(List<PurchaseOrderLineItem> items) throws Exception{
        for(PurchaseOrderLineItem i: items){
            i.setDeleted(true);
            purchaseOrderLineItemRepository.saveAndFlush(i);
        }
    }

    public ResponseEntity<GeneralRes> createPO(CreatePOReq createPOReq, String id){
        logger.info("Creating new purchase order...");
        try{
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            if(id != null){
                PurchaseOrder temp = purchaseOrderRepository.getOne(id);
                if(temp != null){
                    temp.setDeleted(true);
                    purchaseOrderRepository.saveAndFlush(temp);
                }
                deleteItem(temp.getPurchaseOrderLineItems());
            }

            if(createPOReq.getToSubmit()){
                purchaseOrder.setStatus(BudgetPlanStatusEnum.PENDING_BM_APPROVAL);
            }
            else{
                purchaseOrder.setStatus(BudgetPlanStatusEnum.DRAFT);
            }

            purchaseOrder.setCurrencyCode(createPOReq.getCurrencyCode());
            Vendor v = vendorRepository.findById(createPOReq.getVendorId()).get();

            purchaseOrder.setVendor(v);
            purchaseOrder.setCreatedBy(createPOReq.getUsername());
            purchaseOrder.setCreatedDateTime(new Date());
            purchaseOrder = purchaseOrderRepository.saveAndFlush(purchaseOrder);
            purchaseOrder.setRelatedBJF(createPOReq.getRelatedBJF());

            List<PurchaseOrderLineItem> list = saveLineItem(createPOReq.getPurchaseOrderLineItemList(), purchaseOrder);
            purchaseOrder.setPurchaseOrderLineItems(list);
            purchaseOrderRepository.saveAndFlush(purchaseOrder);

            return ResponseEntity
                    .ok()
                    .body(new GeneralRes("Successfully Created The Response Entity!", false));
        } catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    private List<PurchaseOrderLineItem> saveLineItem(List<PurchaseOrderLineItem> items, PurchaseOrder purchaseOrder) throws Exception{
        List<PurchaseOrderLineItem> newItems = new ArrayList<>();
        for(PurchaseOrderLineItem i: items){
            i.setPurchaseOrder(purchaseOrder);
            newItems.add(purchaseOrderLineItemRepository.saveAndFlush(i));
        }
        return newItems;
    }

    public ResponseEntity<GeneralRes> getPO(String id){
        try{
            PurchaseOrder po = purchaseOrderRepository.getOne(id);
            if(po == null) return ResponseEntity
                    .notFound().build();
            else return ResponseEntity.ok().body(new GetPurchaseOrderRes("Successfully retrieved the purchase order!", true, po));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    public ResponseEntity<GeneralRes> getListPO(BudgetPlanStatusEnum budgetPlanStatusEnum){
        try{
            List<PurchaseOrder> list = purchaseOrderRepository.findPurchaseOrderByStatus(budgetPlanStatusEnum);
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

    private void createApprovalTicket(String requesterUsername, Employee receiver, Plan newPlan, String content){
        Employee requesterEntity = employeeRepository.findEmployeeByUserName(requesterUsername);
        ApprovalTicketService.createTicketAndSendEmail(requesterEntity,receiver,newPlan,content, ApprovalTypeEnum.BUDGETPLAN);
    }

}
