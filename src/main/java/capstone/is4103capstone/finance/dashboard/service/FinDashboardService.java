package capstone.is4103capstone.finance.dashboard.service;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.finance.Repository.PlanLineItemRepository;
import capstone.is4103capstone.finance.Repository.SpendingRecordRepository;
import capstone.is4103capstone.finance.dashboard.model.FinDashboardAggLineModel;
import capstone.is4103capstone.finance.dashboard.model.SimplestServiceAmountModel;
import capstone.is4103capstone.finance.dashboard.model.dbModel.BudgetLineBGCCDBModel;
import capstone.is4103capstone.finance.dashboard.model.dbModel.BudgetLineDBModel;
import capstone.is4103capstone.finance.dashboard.model.dbModel.ReforecastLineGBCCMonth;
import capstone.is4103capstone.finance.dashboard.model.dbModel.SpendingRecordAggreDBModel;
import capstone.is4103capstone.finance.dashboard.model.res.FinDashboardRes;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.EntityMappingService;
import capstone.is4103capstone.util.Tools;
import capstone.is4103capstone.util.enums.BudgetPlanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class FinDashboardService {

    @Autowired
    SpendingRecordRepository spendingRecordRepo;
    @Autowired
    PlanLineItemRepository planLineItemRepo;
    @Autowired
    EntityMappingService entityMappingService;

    public FinDashboardRes byCompanyStructureEstimated(String topLevel, String id, int year){

        return new FinDashboardRes("Retreived the result,",false);
    }

    //allow until country level selection.
    public FinDashboardRes byBudgetCategoryActualSpending(String topLevel, String id, int year) throws Exception{
        String yearStartDate = Tools.getFirstStrDayOfYear(year);
        String yearEndDate = Tools.getFirstStrDayOfYear(year+1);
        String defaultCurrency = "";
        DBEntityTemplate topLevelEntity = mapTopLevel(topLevel,id);

        //This is only for actuals, need to gather budget & reforecast later.
        List<SpendingRecordAggreDBModel> spendingByCountry =
                spendingRecordRepo.retrieveSpendingRecordByCountryAndYear(
                        topLevelEntity.getCode(),yearStartDate,yearEndDate);
        System.out.println("*****"+spendingByCountry.size());

        Set<String> allServices = new HashSet<>();

        // if topLevel entity is a country, then in all the records there's only 1 country
        // otherwise there's multiple country.
        // but this function is to aggregate by budget categories, not company structure
        // so if there's multiple countries in the spending record returned, we just sum up the spending by services
        Map<String, BigDecimal> serviceAndActualMapping = new HashMap<>();
        for (SpendingRecordAggreDBModel m: spendingByCountry){
            String servCode = m.getServiceCode();
            if (serviceAndActualMapping.containsKey(servCode)){
                BigDecimal previuos = serviceAndActualMapping.get(servCode);
                serviceAndActualMapping.replace(servCode,previuos.add(m.getTotalSpendingPerService()));
            }else {
                serviceAndActualMapping.put(servCode, m.getTotalSpendingPerService());
            }
            allServices.add(servCode);
        }

        // find the budget & lastest reforecast for each service by country;
        // god damn
        // find budget first
        List<BudgetLineBGCCDBModel> yearlyBudgetByServiceAndCountry = planLineItemRepo.findTotalBudgetGroupByCountryAndService(topLevelEntity.getCode(),year);



        //service as key
        HashMap<String,FinDashboardAggLineModel> finalCombine = new HashMap<>();

        List<ReforecastLineGBCCMonth> latestReforecastRecords = planLineItemRepo.findLatestReforecastLinesGroupByCountryAndService(topLevelEntity.getCode(),year);

        //cost center, service & amount
        HashMap<String, BigDecimal> budgetReforecastMapping = new HashMap<>();
        for (ReforecastLineGBCCMonth reforecastItem: latestReforecastRecords){
            String ccCode = reforecastItem.getCostCenterCode();
            String serviceCode = reforecastItem.getServiceCode();
            String combinedKey = ccCode +"|"+ serviceCode;
            budgetReforecastMapping.put(combinedKey,reforecastItem.getAmount());
            allServices.add(serviceCode);
        }

        HashMap<String, BigDecimal> budgetServiceMapping = new HashMap<>();
        for (BudgetLineBGCCDBModel budgetModel: yearlyBudgetByServiceAndCountry){
            String serviceCode = budgetModel.getServiceCode();
            allServices.add(serviceCode);
            String combinedKey = budgetModel.getCostCenterCode()+"|"+budgetModel.getServiceCode();
            // 这个cost center 的这个service既有budget又有reforecast
            if (budgetReforecastMapping.containsKey(combinedKey)){

            }else{
                //这个cost center的这个service只有budget没有reforecast
                //那么reforecast就用budget来算。因为reforecast是在budget的基础上的，但budget 不reflect reforecast
                budgetReforecastMapping.put(combinedKey,budgetModel.getAmount());
            }

            if (budgetServiceMapping.containsKey(serviceCode)){
                BigDecimal originalValue = budgetServiceMapping.get(serviceCode);
                budgetServiceMapping.replace(serviceCode,originalValue.add(budgetModel.getAmount()));
            }else{
                budgetServiceMapping.put(serviceCode,budgetModel.getAmount());
            }
        }

        HashMap<String, BigDecimal> reforecastServMapping = new HashMap<>();
        for (Map.Entry<String, BigDecimal> entry : budgetReforecastMapping.entrySet()) {
            String serviceCode = entry.getKey().split("|")[1];
            BigDecimal reforecastAmt = entry.getValue();

            if (reforecastServMapping.containsKey(serviceCode)){
                BigDecimal originalValue = reforecastServMapping.get(serviceCode);
                reforecastServMapping.replace(serviceCode,originalValue.add(reforecastAmt));
            }else{
                reforecastServMapping.put(serviceCode,reforecastAmt);
            }
        }
        HashMap<String, FinDashboardAggLineModel> serviceLevelAggre = new HashMap<>();
        List<FinDashboardAggLineModel> results = new ArrayList<>();
        Iterator<String> serviceIterator = allServices.iterator();
        while (serviceIterator.hasNext()){
            String serviceCode = serviceIterator.next();
            FinDashboardAggLineModel thisService = new FinDashboardAggLineModel();
            thisService.setLevelCode(serviceCode);
            thisService.setLevelName(serviceCode);
            thisService.setActuals(serviceAndActualMapping.getOrDefault(serviceCode,BigDecimal.ZERO));
            thisService.setTotalBudget(budgetServiceMapping.getOrDefault(serviceCode,BigDecimal.ZERO));
            thisService.setLatestReforecast(reforecastServMapping.getOrDefault(serviceCode,BigDecimal.ZERO));
            thisService.setCurrencyCode("GBP");
            serviceLevelAggre.put(serviceCode,thisService);
            results.add(thisService);
        }



        return new FinDashboardRes("Retreived the result,",false,results);
    }

    public FinDashboardRes byBudgetCategoryEstimated(String topLevel, String id, int year){
        return new FinDashboardRes("Retreived the result,",false);
    }

    private DBEntityTemplate mapTopLevel(String topLevel, String id) throws Exception{
        return entityMappingService.getEntityByClassNameAndId(topLevel,id);
    }
}
