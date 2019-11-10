package capstone.is4103capstone.finance.dashboard.service;

import capstone.is4103capstone.finance.dashboard.model.dbModel.VendorAndContractDBModel;
import capstone.is4103capstone.finance.dashboard.model.VendorContractAggreLineModel;
import capstone.is4103capstone.finance.dashboard.model.dbModel.VendorPurchaseAmountDBModel;
import capstone.is4103capstone.finance.dashboard.model.res.VendorContractAggregationRes;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.Repository.VendorRepository;
import capstone.is4103capstone.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ContractValueAnalysisService {

    //TODO: handle currency exchange!

    @Autowired
    VendorRepository vendorRepository;

    // Frist stage: don't do child contract
    public GeneralRes getAllVendorByYear(int year) throws Exception{
        List<VendorAndContractDBModel> vendorAndContracts = retrieveAllVendorAndAllActiveContractValue(year);
        List<VendorPurchaseAmountDBModel> vendorAndPurchase = retrieveVendorAndTotalPurchaseByYearAndToday(year);
        HashMap<String, VendorAndContractDBModel> vendorCodeMapping = new HashMap<>();

        for (VendorAndContractDBModel m:vendorAndContracts)
            vendorCodeMapping.put(m.getVendorCode(),m);

        //只需要one-to-one的map就行，因为目前只在vendor level上面
        List<VendorContractAggreLineModel>  summary = new ArrayList<>();

        for (VendorPurchaseAmountDBModel m:vendorAndPurchase){
            VendorAndContractDBModel relevantContract = vendorCodeMapping.get(m.getVendorCode());
            if (relevantContract == null){
                summary.add(new VendorContractAggreLineModel(m,false));
            }else{
                summary.add(new VendorContractAggreLineModel(relevantContract,m));
            }
        }

        if (summary.isEmpty())
            throw new Exception("No record available for year "+year);
        return new VendorContractAggregationRes("Successfully retrieved",false,summary.size(),year,summary);
    }

    public List<VendorAndContractDBModel> retrieveAllVendorAndAllActiveContractValue(int year){
        return vendorRepository.retrieveVendorAndItsTotalActiveContractValue(Tools.getFirstStrDayOfYear(year));
    }

    public List<VendorPurchaseAmountDBModel> retrieveVendorAndTotalPurchaseByYearAndToday(int year){
        String tmrDateStr = Tools.getTomorrowStr();
        String firstDayOfYear = Tools.getFirstStrDayOfYear(year);
        return vendorRepository.retrieveVendorAndAllPurchaseByYearAndToday(tmrDateStr,firstDayOfYear);
    }
}
