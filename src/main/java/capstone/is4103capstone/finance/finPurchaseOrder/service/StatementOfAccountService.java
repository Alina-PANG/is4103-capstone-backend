package capstone.is4103capstone.finance.finPurchaseOrder.service;

import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.entities.finance.StatementOfAcctLineItem;
import capstone.is4103capstone.finance.Repository.PurchaseOrderRepository;
import capstone.is4103capstone.finance.Repository.StatementOfAccountLineItemRepository;
import capstone.is4103capstone.finance.finPurchaseOrder.POEntityCodeHPGeneration;
import capstone.is4103capstone.finance.finPurchaseOrder.model.SOAModel;
import capstone.is4103capstone.finance.finPurchaseOrder.model.req.CreateSoAByInvoiceReq;
import capstone.is4103capstone.finance.finPurchaseOrder.model.req.CreateSoAByScheduleReq;
import capstone.is4103capstone.finance.finPurchaseOrder.model.res.GetPurchaseOrderListRes;
import capstone.is4103capstone.finance.finPurchaseOrder.model.res.GetSoARes;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.util.Tools;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StatementOfAccountService {
    private static final Logger logger = LoggerFactory.getLogger(StatementOfAccountService.class);
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    StatementOfAccountLineItemRepository statementOfAccountLineItemRepository;
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public ResponseEntity<GeneralRes> createBySchedule(CreateSoAByScheduleReq createSoAByScheduleReq, String username){
        try{
            Date endDate = dateFormatter.parse(createSoAByScheduleReq.getEndDate());
            Date current = dateFormatter.parse(createSoAByScheduleReq.getStartDate());
            List<StatementOfAcctLineItem> items = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            PurchaseOrder po = purchaseOrderRepository.getOne(createSoAByScheduleReq.getPoId());
            if(po == null) return ResponseEntity.notFound().build();
            int toAdd = 0, numberOfLoops = 0;

            DateTime dateTime1 = new DateTime(createSoAByScheduleReq.getStartDate());
            DateTime dateTime2 = new DateTime(createSoAByScheduleReq.getEndDate());

            // 0: weekly, 1: monthly, 2: yearly
            if(createSoAByScheduleReq.getFrequency() == 0) toAdd = Calendar.WEEK_OF_YEAR;
            else if(createSoAByScheduleReq.getFrequency() == 1) toAdd = Calendar.MONTH;
            else toAdd = Calendar.YEAR;

            while (current.before(endDate)) {
                StatementOfAcctLineItem statementOfAcctLineItem = new StatementOfAcctLineItem();
                statementOfAcctLineItem.setPurchaseOrder(po);
                statementOfAcctLineItem.setScheduleDate(current);
                statementOfAcctLineItem.setCreatedBy(username);
                statementOfAcctLineItem.setCreatedDateTime(new Date());
                items.add(statementOfAccountLineItemRepository.saveAndFlush(statementOfAcctLineItem));
                calendar.setTime(current);
                calendar.add(toAdd, createSoAByScheduleReq.getNumFrequency());
                current = calendar.getTime();
                numberOfLoops ++;
            }

            logger.info("There will be "+numberOfLoops+" loops.");
            BigDecimal actualPmt = createSoAByScheduleReq.getTotalAmount().divide(BigDecimal.valueOf(numberOfLoops));

            for(StatementOfAcctLineItem s: items){
                s.setActualPmt(actualPmt);
                s.setCode(POEntityCodeHPGeneration.getCode(statementOfAccountLineItemRepository,s));
                statementOfAccountLineItemRepository.saveAndFlush(s);
            }

            po.setStatementOfAccount(items);
            purchaseOrderRepository.saveAndFlush(po);
            return ResponseEntity.ok().body(new GeneralRes("Successfully saved the statement of accounts!", false));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    public ResponseEntity<GeneralRes> createByInvoice(CreateSoAByInvoiceReq createSoAByInvoiceReq, String username){
        try{
            PurchaseOrder po = purchaseOrderRepository.getOne(createSoAByInvoiceReq.getPoId());
            if(po == null) return ResponseEntity.notFound().build();

            StatementOfAcctLineItem statementOfAcctLineItem = new StatementOfAcctLineItem();
            statementOfAcctLineItem.setScheduleDate(dateFormatter.parse(createSoAByInvoiceReq.getReceiveDate()));
            statementOfAcctLineItem.setPaidAmt(createSoAByInvoiceReq.getPaidAmt());
            statementOfAcctLineItem.setActualPmt(createSoAByInvoiceReq.getActualPmt());
            statementOfAcctLineItem.setPurchaseOrder(po);
            statementOfAcctLineItem.setCreatedBy(username);
            statementOfAcctLineItem.setCreatedDateTime(new Date());

            statementOfAcctLineItem = statementOfAccountLineItemRepository.saveAndFlush(statementOfAcctLineItem);
            statementOfAcctLineItem.setCode(POEntityCodeHPGeneration.getCode(statementOfAccountLineItemRepository,statementOfAcctLineItem));
            statementOfAccountLineItemRepository.saveAndFlush(statementOfAcctLineItem);

            if(po.getStatementOfAccount() == null) po.setStatementOfAccount(new ArrayList<StatementOfAcctLineItem>());
            po.getStatementOfAccount().add(statementOfAcctLineItem);
            purchaseOrderRepository.saveAndFlush(po);

           return ResponseEntity.ok().body(new GeneralRes("Successfully created the statement of accounts!", false));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    public ResponseEntity<GeneralRes> updateSoa(CreateSoAByInvoiceReq createSoAByInvoiceReq, String username, String id){
        try{
            StatementOfAcctLineItem statementOfAcctLineItem = statementOfAccountLineItemRepository.getOne(id);
            if(statementOfAcctLineItem == null) return ResponseEntity.notFound().build();

            if(createSoAByInvoiceReq.getActualPmt() != null) statementOfAcctLineItem.setActualPmt(createSoAByInvoiceReq.getActualPmt());
            if(createSoAByInvoiceReq.getPaidAmt() != null) {
                statementOfAcctLineItem.setPaidAmt(createSoAByInvoiceReq.getPaidAmt());
                if(statementOfAcctLineItem.getInvoice() != null){
                    statementOfAcctLineItem.getInvoice().setPaymentAmount(createSoAByInvoiceReq.getPaidAmt());
                }
            }
            if(createSoAByInvoiceReq.getReceiveDate() != null) statementOfAcctLineItem.setScheduleDate(dateFormatter.parse(createSoAByInvoiceReq.getReceiveDate()));
            statementOfAcctLineItem.setLastModifiedBy(username);
            statementOfAcctLineItem.setLastModifiedDateTime(new Date());

            statementOfAccountLineItemRepository.saveAndFlush(statementOfAcctLineItem);
            return ResponseEntity.ok().body(new GeneralRes("Successfully updated the statement of account!", false));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    public ResponseEntity<GeneralRes> getSoaByPo(String poId){
        try{
            PurchaseOrder po = purchaseOrderRepository.getOne(poId);
            if(po == null) return ResponseEntity
                    .notFound().build();
            List<StatementOfAcctLineItem> list = po.getStatementOfAccount();

            if(list == null) return ResponseEntity
                    .notFound().build();
            List<SOAModel> result = new ArrayList<>();
            for(StatementOfAcctLineItem l: list){
                result.add(new SOAModel(l.getPaidAmt(), l.getActualPmt(), l.getScheduleDate(), l.getInvoice().getId()));
            }
            return ResponseEntity.ok().body(new GetSoARes("Successfully retrieved the purchase orders!", true, result));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }
}
