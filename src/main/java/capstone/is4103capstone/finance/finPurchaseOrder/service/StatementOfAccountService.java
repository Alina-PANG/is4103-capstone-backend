package capstone.is4103capstone.finance.finPurchaseOrder.service;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import org.springframework.stereotype.Service;

import javax.xml.ws.Response;
import java.util.Date;

@Service
public class StatementOfAccountService {
    public Response<DBEntityTemplate> createBySchedule(String frequency, Date startDate, Date endDate){
        return null;
    }

    public Response<DBEntityTemplate> createByInvoice(){
        return null;
    }

    public Response<DBEntityTemplate> getByPO(String poId){
        return null;
    }
}
