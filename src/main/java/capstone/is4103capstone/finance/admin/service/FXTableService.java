package capstone.is4103capstone.finance.admin.service;

import capstone.is4103capstone.entities.finance.FXRecord;
import capstone.is4103capstone.finance.Repository.FXRecordRepository;
import capstone.is4103capstone.finance.admin.EntityCodeHPGeneration;
import capstone.is4103capstone.finance.admin.model.FXRecordModel;
import capstone.is4103capstone.finance.admin.model.req.CreateFXRequest;
import capstone.is4103capstone.finance.admin.model.req.FXRecordQueryReq;
import capstone.is4103capstone.finance.admin.model.res.ViewFXRecordRes;
import capstone.is4103capstone.util.FinanceEntityCodeHPGenerator;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.util.resources.ga.LocaleNames_ga;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FXTableService {
    private static final Logger logger = LoggerFactory.getLogger(FXTableService.class);
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private Date MAX_DATE;
    private Date MIN_DATE;

    @Autowired
    FXRecordRepository fxRecordRepository;

    @PostConstruct
    public void configureDate(){
        try {
            MAX_DATE = formatter.parse("9999-12-31");
            MIN_DATE = formatter.parse("1970-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public JSONObject createFXRecord(CreateFXRequest request){
        JSONObject res = new JSONObject();

        try{
            Date effectiveDate = formatter.parse(request.getEffectiveDate());
            FXRecord newRecord = new FXRecord(request.getBaseCurr(),request.getPriceCurr(),request.getRate(),effectiveDate);
            newRecord.setCreatedBy(request.getUsername());
            newRecord = fxRecordRepository.save(newRecord);

            newRecord.setCode(EntityCodeHPGeneration.getCode(fxRecordRepository,newRecord));

            FXRecordModel model = new FXRecordModel(newRecord.getBaseCurrencyAbbr(), newRecord.getPriceCurrencyAbbr(), formatter.format(newRecord.getEffectiveDate()), newRecord.getExchangeRate(), newRecord.getId(), newRecord.getCode());

            res.put("newRecord",new JSONObject(model));
            res.put("hasError",false);
            res.put("message","Successfully created FXRecord, all the sub categories under this category is also removed.");
        }catch (Exception e){
            res.put("message",e.getMessage());
            res.put("hasError",true);
        }
        return res;
    }

    public ViewFXRecordRes viewFXRecordRes(FXRecordQueryReq req){
        try{
            Date startDate;
            Date endDate;
            try {
                startDate = req.getStartDate().isEmpty() ? MIN_DATE : formatter.parse(req.getStartDate());
                endDate = req.getEndDate().isEmpty() ? MAX_DATE : formatter.parse(req.getEndDate());
            }catch (ParseException pe){
                throw new Exception("Date format is incorrect, expected yyyy-MM-dd");
            }

            List<FXRecordModel> records = new ArrayList<>();
            List<FXRecord> fxUnderContraints = fxRecordRepository.findAllWithConstraints(req.getBaseCurr(),req.getPriceCurr(),startDate,endDate);

            if (fxUnderContraints.size()==0){
                throw new Exception("No FX exchange records match your search. ");
            }

            FXRecordModel model;
            for (FXRecord fx:fxUnderContraints){
                String efctStr = formatter.format(fx.getEffectiveDate());
                model = new FXRecordModel(fx.getBaseCurrencyAbbr(),fx.getPriceCurrencyAbbr(),efctStr,fx.getExchangeRate(),fx.getId(),fx.getCode());
                records.add(model);
            }

            return new ViewFXRecordRes("Retrieved the list of fx records",false,records.size(),formatter.format(startDate),formatter.format(endDate),records);
        }catch (Exception e){
            return new ViewFXRecordRes(e.getMessage(),true);
        }

    }




}
