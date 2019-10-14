package capstone.is4103capstone.finance.finPurchaseOrder.service;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.finance.Plan;
import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.finance.Repository.PurchaseOrderRepository;
import capstone.is4103capstone.finance.finPurchaseOrder.model.req.CreatePOReq;
import capstone.is4103capstone.finance.finPurchaseOrder.model.res.GetPurchaseOrderListRes;
import capstone.is4103capstone.finance.finPurchaseOrder.model.res.GetPurchaseOrderRes;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.ApprovalTicketService;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    EmployeeRepository employeeRepository;


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
            }

            purchaseOrder.setStatus(ApprovalStatusEnum.PENDING);
            purchaseOrder.setTotalAmount(createPOReq.getAmount());
            purchaseOrder.setCurrencyCode(createPOReq.getCurrencyCode());
            purchaseOrder.setCreatedBy(createPOReq.getUsername());
            purchaseOrder.setCreatedDateTime(new Date());
            purchaseOrder.setRelatedBJF(createPOReq.getRelatedBJF());
            purchaseOrder = purchaseOrderRepository.saveAndFlush(purchaseOrder);

            logger.info("Creating purchase order with related BJF: "+createPOReq.getRelatedBJF());
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

    public ResponseEntity<GeneralRes> getListPO(int status){
        try{
            logger.info("Getting all purchase orders with status "+status);
            List<PurchaseOrder> list = purchaseOrderRepository.findPurchaseOrderByStatus(status);
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

    private void createApprovalTicket(String requesterUsername, Employee receiver, PurchaseOrder purchaseOrder, String content){
        Employee requesterEntity = employeeRepository.findEmployeeByUserName(requesterUsername);
        ApprovalTicketService.createTicketAndSendEmail(requesterEntity,receiver,purchaseOrder,content, ApprovalTypeEnum.PURCHASEORDER);
    }

    public ResponseEntity<GeneralRes> approvePO(String id, Boolean approved, String username){
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
//            Employee creater = employeeRepository.findEmployeeByUserName(po.getCreatedBy());
//            createApprovalTicket(username, creater, po, "Your purchase order has been approved!");
            return ResponseEntity.ok().body(new GeneralRes("Successfully approved/rejected the purchase orders!", true));
        }catch(Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }
}
