package capstone.is4103capstone.finance.finPurchaseOrder.service;

import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.entities.finance.StatementOfAcctLineItem;
import capstone.is4103capstone.finance.Repository.PurchaseOrderRepository;
import capstone.is4103capstone.finance.Repository.StatementOfAccountLineItemRepository;
import capstone.is4103capstone.finance.finPurchaseOrder.model.req.CreateSoAByInvoiceReq;
import capstone.is4103capstone.finance.finPurchaseOrder.model.req.CreateSoAByScheduleReq;
import capstone.is4103capstone.finance.finPurchaseOrder.model.res.GetPurchaseOrderListRes;
import capstone.is4103capstone.finance.finPurchaseOrder.model.res.GetSoARes;
import capstone.is4103capstone.general.model.GeneralRes;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StatementOfAccountService {
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    StatementOfAccountLineItemRepository statementOfAccountLineItemRepository;

    public ResponseEntity<GeneralRes> createBySchedule(CreateSoAByScheduleReq createSoAByScheduleReq, String username){
        try{
            Date current = createSoAByScheduleReq.getStartDate();
            List<StatementOfAcctLineItem> items = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            PurchaseOrder po = purchaseOrderRepository.getOne(createSoAByScheduleReq.getPoId());
            if(po == null) return ResponseEntity.notFound().build();
            int toAdd = 0, numberOfLoops = 0;

            DateTime dateTime1 = new DateTime(createSoAByScheduleReq.getStartDate());
            DateTime dateTime2 = new DateTime(createSoAByScheduleReq.getEndDate());

            // 0: weekly, 1: monthly, 2: yearly
            if(createSoAByScheduleReq.getFrequency() == 0){
                toAdd = Calendar.DAY_OF_WEEK;
                numberOfLoops = Weeks.weeksBetween(dateTime1, dateTime2).getWeeks();
            }
            else if(createSoAByScheduleReq.getFrequency() == 1) {
                toAdd = Calendar.MONTH;
                numberOfLoops = Months.monthsBetween(dateTime1, dateTime2).getMonths();
            }
            else {
                toAdd = Calendar.YEAR;
                numberOfLoops = Years.yearsBetween(dateTime1, dateTime2).getYears();
            }

            numberOfLoops /= createSoAByScheduleReq.getNumFrequency();
            BigDecimal actualPmt = createSoAByScheduleReq.getTotalAmount().divide(BigDecimal.valueOf(numberOfLoops));

            while (current.before(createSoAByScheduleReq.getEndDate())) {
                StatementOfAcctLineItem statementOfAcctLineItem = new StatementOfAcctLineItem();
                statementOfAcctLineItem.setActualPmt(actualPmt);
                statementOfAcctLineItem.setPurchaseOrder(po);
                statementOfAcctLineItem.setScheduleDate(current);
                items.add(statementOfAccountLineItemRepository.saveAndFlush(statementOfAcctLineItem));
                calendar.setTime(current);
                calendar.add(toAdd, createSoAByScheduleReq.getNumFrequency());
                current = calendar.getTime();
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

            StatementOfAcctLineItem statementOfAcctLineItem = new StatementOfAcctLineItem(createSoAByInvoiceReq.getReceiveDate(), createSoAByInvoiceReq.getPaidAmt(), createSoAByInvoiceReq.getActualPmt(), createSoAByInvoiceReq.getAccruals());
            statementOfAcctLineItem.setPurchaseOrder(po);

           return ResponseEntity.ok().body(new GeneralRes("Successfully created the statement of accounts!", false));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }

    public ResponseEntity<GeneralRes> updateSoa(String id){
        try{
            StatementOfAcctLineItem statementOfAcctLineItem = statementOfAccountLineItemRepository.getOne(id);
            if(statementOfAcctLineItem == null) return ResponseEntity.notFound().build();

            return ResponseEntity.ok().body(new GeneralRes("Successfully retrieved the purchase orders!", false));
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
            else return ResponseEntity.ok().body(new GetSoARes("Successfully retrieved the purchase orders!", true, list));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes("An unexpected error has occured: "+ ex.toString(), true));
        }
    }
}
