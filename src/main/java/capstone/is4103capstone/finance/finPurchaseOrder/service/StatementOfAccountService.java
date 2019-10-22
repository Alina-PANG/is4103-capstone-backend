package capstone.is4103capstone.finance.finPurchaseOrder.service;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.finance.BJF;
import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.entities.finance.StatementOfAcctLineItem;
import capstone.is4103capstone.finance.Repository.PurchaseOrderRepository;
import capstone.is4103capstone.finance.finPurchaseOrder.model.res.GetPurchaseOrderRes;
import capstone.is4103capstone.general.model.GeneralRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StatementOfAccountService {
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    public ResponseEntity<GeneralRes> createBySchedule(String frequency, Date startDate, Date endDate){
        return null;
    }

    public ResponseEntity<GeneralRes> createByInvoice(){
        return null;
    }
}
