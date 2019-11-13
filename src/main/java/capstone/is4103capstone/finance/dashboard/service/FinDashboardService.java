package capstone.is4103capstone.finance.dashboard.service;

import capstone.is4103capstone.admin.service.CountryService;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Country;
import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.entities.finance.StatementOfAcctLineItem;
import capstone.is4103capstone.finance.Repository.PlanLineItemRepository;
import capstone.is4103capstone.finance.Repository.PurchaseOrderRepository;
import capstone.is4103capstone.finance.Repository.SpendingRecordRepository;
import capstone.is4103capstone.finance.Repository.StatementOfAccountLineItemRepository;
import capstone.is4103capstone.finance.dashboard.model.FinDashboardAggLineModel;
import capstone.is4103capstone.finance.dashboard.model.PoPmtScheduleModel;
import capstone.is4103capstone.finance.dashboard.model.dbModel.BudgetLineBGCCDBModel;
import capstone.is4103capstone.finance.dashboard.model.dbModel.ReforecastLineGBCCMonth;
import capstone.is4103capstone.finance.dashboard.model.dbModel.SpendingRecordAggreDBModel;
import capstone.is4103capstone.finance.dashboard.model.res.FinDashboardRes;
import capstone.is4103capstone.finance.dashboard.model.res.PoSchedulRes;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.EntityMappingService;
import capstone.is4103capstone.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class FinDashboardService {

    @Autowired
    SpendingRecordRepository spendingRecordRepo;
    @Autowired
    PlanLineItemRepository planLineItemRepo;
    @Autowired
    StatementOfAccountLineItemRepository soaRepo;
    @Autowired
    PurchaseOrderRepository poRepo;
    @Autowired
    EntityMappingService entityMappingService;
    @Autowired
    CountryService countryService;

    public FinDashboardRes byCompanyStructureEstimated(String topLevel, String id, int year) {

        return new FinDashboardRes("Retreived the result,", false);
    }

    //allow until country level selection.
    public FinDashboardRes byBudgetCategoryActualSpending(String topLevel, String id, int year) throws Exception {
        String yearStartDate = Tools.getFirstStrDayOfYear(year);
        String yearEndDate = Tools.getFirstStrDayOfYear(year + 1);
        String defaultCurrency = "";
        DBEntityTemplate topLevelEntity = mapTopLevel(topLevel, id);

        //This is only for actuals, need to gather budget & reforecast later.
        List<SpendingRecordAggreDBModel> spendingByCountry =
                spendingRecordRepo.retrieveSpendingRecordByCountryAndYear(
                        topLevelEntity.getCode(), yearStartDate, yearEndDate);
        System.out.println("*****" + spendingByCountry.size());

        Set<String> allServices = new HashSet<>();

        // if topLevel entity is a country, then in all the records there's only 1 country
        // otherwise there's multiple country.
        // but this function is to aggregate by budget categories, not company structure
        // so if there's multiple countries in the spending record returned, we just sum up the spending by services
        Map<String, BigDecimal> serviceAndActualMapping = new HashMap<>();
        for (SpendingRecordAggreDBModel m : spendingByCountry) {
            String servCode = m.getServiceCode();
            if (serviceAndActualMapping.containsKey(servCode)) {
                BigDecimal previuos = serviceAndActualMapping.get(servCode);
                serviceAndActualMapping.replace(servCode, previuos.add(m.getTotalSpendingPerService()));
            } else {
                serviceAndActualMapping.put(servCode, m.getTotalSpendingPerService());
            }
            allServices.add(servCode);
        }

        // find the budget & lastest reforecast for each service by country;
        // god damn
        // find budget first
        List<BudgetLineBGCCDBModel> yearlyBudgetByServiceAndCountry = planLineItemRepo.findTotalBudgetGroupByCountryAndService(topLevelEntity.getCode(), year);


        //service as key
        HashMap<String, FinDashboardAggLineModel> finalCombine = new HashMap<>();

        List<ReforecastLineGBCCMonth> latestReforecastRecords = planLineItemRepo.findLatestReforecastLinesGroupByCountryAndService(topLevelEntity.getCode(), year);

        //cost center, service & amount
        HashMap<String, BigDecimal> budgetReforecastMapping = new HashMap<>();
        for (ReforecastLineGBCCMonth reforecastItem : latestReforecastRecords) {
            String ccCode = reforecastItem.getCostCenterCode();
            String serviceCode = reforecastItem.getServiceCode();
            String combinedKey = ccCode + "|" + serviceCode;
            budgetReforecastMapping.put(combinedKey, reforecastItem.getAmount());
            allServices.add(serviceCode);
        }

        HashMap<String, BigDecimal> budgetServiceMapping = new HashMap<>();
        for (BudgetLineBGCCDBModel budgetModel : yearlyBudgetByServiceAndCountry) {
            String serviceCode = budgetModel.getServiceCode();
            allServices.add(serviceCode);
            String combinedKey = budgetModel.getCostCenterCode() + "|" + budgetModel.getServiceCode();
            // 这个cost center 的这个service既有budget又有reforecast
            if (budgetReforecastMapping.containsKey(combinedKey)) {

            } else {
                //这个cost center的这个service只有budget没有reforecast
                //那么reforecast就用budget来算。因为reforecast是在budget的基础上的，但budget 不reflect reforecast
                budgetReforecastMapping.put(combinedKey, budgetModel.getAmount());
            }

            if (budgetServiceMapping.containsKey(serviceCode)) {
                BigDecimal originalValue = budgetServiceMapping.get(serviceCode);
                budgetServiceMapping.replace(serviceCode, originalValue.add(budgetModel.getAmount()));
            } else {
                budgetServiceMapping.put(serviceCode, budgetModel.getAmount());
            }
        }

        HashMap<String, BigDecimal> reforecastServMapping = new HashMap<>();
        for (Map.Entry<String, BigDecimal> entry : budgetReforecastMapping.entrySet()) {
            String serviceCode = entry.getKey().split("|")[1];
            BigDecimal reforecastAmt = entry.getValue();

            if (reforecastServMapping.containsKey(serviceCode)) {
                BigDecimal originalValue = reforecastServMapping.get(serviceCode);
                reforecastServMapping.replace(serviceCode, originalValue.add(reforecastAmt));
            } else {
                reforecastServMapping.put(serviceCode, reforecastAmt);
            }
        }
        HashMap<String, FinDashboardAggLineModel> serviceLevelAggre = new HashMap<>();
        List<FinDashboardAggLineModel> results = new ArrayList<>();
        Iterator<String> serviceIterator = allServices.iterator();
        while (serviceIterator.hasNext()) {
            String serviceCode = serviceIterator.next();
            FinDashboardAggLineModel thisService = new FinDashboardAggLineModel();
            thisService.setLevelCode(serviceCode);
            thisService.setLevelName(serviceCode);
            thisService.setActuals(serviceAndActualMapping.getOrDefault(serviceCode, BigDecimal.ZERO));
            thisService.setTotalBudget(budgetServiceMapping.getOrDefault(serviceCode, BigDecimal.ZERO));
            thisService.setLatestReforecast(reforecastServMapping.getOrDefault(serviceCode, BigDecimal.ZERO));
            thisService.setCurrencyCode("GBP");
            serviceLevelAggre.put(serviceCode, thisService);
            results.add(thisService);
        }

        return new FinDashboardRes("Retreived the result,", false, results);
    }

    public GeneralRes retrievePODashboard(String country, int type, String startDateStr, String endDateStr) throws Exception {
        Country c = countryService.validateCountry(country);
        //0: history: commited and paid
        //1: future: expected payment
//        String startDateStr = Tools.dateFormatter.format(startDate);
//        String endDateStr = Tools.dateFormatter.format(endDate);
        if (type == 0) {
            return retrievePOHistory(c.getId(), startDateStr,endDateStr);
        } else if (type == 1) {
            return retrieveExpectedActualsFuture(c.getId(), startDateStr,endDateStr);
        } else {
            throw new Exception("Query type not supported");
        }
    }

    public GeneralRes retrievePODashboard(String country, int type, int timeFrameSelection) throws Exception {
        Country c = countryService.validateCountry(country);
        //0: history: commited and paid
        //1: future: expected payment

        if (type == 0) {
            String endDate = Tools.getTomorrowStr();
            String startDate = Tools.getXMonthCompareToToday(-timeFrameSelection);
            return retrievePOHistory(c.getId(), startDate,endDate);
        } else if (type == 1) {
            String startDate = Tools.getTodayStr();
            String endDate = Tools.getXMonthCompareToToday(timeFrameSelection);
            return retrieveExpectedActualsFuture(c.getId(),  startDate,endDate);
        } else {
            throw new Exception("Query type not supported");
        }
    }

    //0
    //1
    private GeneralRes retrievePOHistory(String countryId, String startDate, String endDate) throws Exception {

        List<PurchaseOrder> poToCalculateCommited = poRepo.findPOCreatedBetweenInCountry(countryId, startDate, endDate);
        List<StatementOfAcctLineItem> soaToCalculatePaid = soaRepo.findRecordsBetweenDatesInCountryLstMdf(countryId, startDate, endDate);

        //month: total amt
        Map<String, BigDecimal> monthMapCommited = new HashMap<>();
        Map<String, BigDecimal> monthMapPaid = new HashMap<>();
        Set<String> availableTimePoint = new HashSet<>();
        for (PurchaseOrder po: poToCalculateCommited){
            String monthBelongTo = Tools.monthYearFormatter.format(po.getCreatedDateTime());
            availableTimePoint.add(monthBelongTo);
            if (monthMapCommited.containsKey(monthBelongTo)){
                BigDecimal oriValue = monthMapCommited.get(monthBelongTo);
                monthMapCommited.replace(monthBelongTo,oriValue.add(po.getTotalAmtInGBP()));
            }else{
                monthMapCommited.put(monthBelongTo,po.getTotalAmtInGBP());
            }
        }

        for (StatementOfAcctLineItem soa: soaToCalculatePaid){
            String monthBelongTo = Tools.monthYearFormatter.format(soa.getLastModifiedDateTime());
            availableTimePoint.add(monthBelongTo);
            if (monthMapPaid.containsKey(monthBelongTo)){
                BigDecimal oriValue = monthMapPaid.get(monthBelongTo);
                monthMapPaid.replace(monthBelongTo,oriValue.add(soa.getPaidAmtInGBP()));
            }else{
                monthMapPaid.put(monthBelongTo,soa.getPaidAmtInGBP());
            }
        }
        List<PoPmtScheduleModel> records = new ArrayList<>();

        List<String> months = new ArrayList<>(availableTimePoint);
        Collections.sort(months, Comparator.comparing(s -> LocalDate.parse("01-"+s, Tools.localDateMonthYearFormatter)));

        for (String m: months){
            PoPmtScheduleModel model = new PoPmtScheduleModel(m,monthMapCommited.get(m),monthMapPaid.get(m));
            records.add(model);
        }


        return new PoSchedulRes("Retrieved records",false,records);
    }

    private GeneralRes retrieveExpectedActualsFuture(String countryId, String startDate, String endDate) {

        List<StatementOfAcctLineItem> soaToCalculateExpectedActuals = soaRepo.findRecordsBetweenDatesInCountryScheduleDate(countryId, startDate, endDate);
        Map<String, BigDecimal> monthMapActuals = new HashMap<>();

        for (StatementOfAcctLineItem soa: soaToCalculateExpectedActuals){
            String monthBelongTo = Tools.monthYearFormatter.format(soa.getScheduleDate());
            if (monthMapActuals.containsKey(monthBelongTo)){
                BigDecimal oriValue = monthMapActuals.get(monthBelongTo);
                monthMapActuals.replace(monthBelongTo,oriValue.add(soa.getActualPmtInGBP()));
            }else{
                monthMapActuals.put(monthBelongTo,soa.getActualPmtInGBP());
            }
        }
        List<PoPmtScheduleModel> records = new ArrayList<>();
        List<String> months = new ArrayList<>(monthMapActuals.keySet());
        Collections.sort(months, Comparator.comparing(s -> LocalDate.parse("01-"+s, Tools.localDateMonthYearFormatter)));

        for (String key : months) {
            records.add(new PoPmtScheduleModel(key,monthMapActuals.get(key)));
        }

        return new PoSchedulRes("Retrieved records",false,records);
    }

    public FinDashboardRes byBudgetCategoryEstimated(String topLevel, String id, int year) {
        return new FinDashboardRes("Retreived the result,", false);
    }

    private DBEntityTemplate mapTopLevel(String topLevel, String id) throws Exception {
        return entityMappingService.getEntityByClassNameAndId(topLevel, id);
    }


}
