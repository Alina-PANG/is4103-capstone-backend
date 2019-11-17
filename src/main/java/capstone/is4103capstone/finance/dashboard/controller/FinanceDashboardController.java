package capstone.is4103capstone.finance.dashboard.controller;

import capstone.is4103capstone.finance.dashboard.model.res.FinDashboardRes;
import capstone.is4103capstone.finance.dashboard.service.FinDashboardService;
import capstone.is4103capstone.general.model.GeneralRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PreUpdate;
import java.util.Date;

@RestController
@RequestMapping("/api/dashboard/fin")
@CrossOrigin(origins = "http://localhost:3000")
public class FinanceDashboardController {

    private boolean useEstimatedValueCompanyStructure = true;
    private boolean useEstimateValueBudgetCategory = false;

    @Autowired
    FinDashboardService finDashboardService;

    @GetMapping("/company")
    public ResponseEntity<FinDashboardRes> getResultsByCompanyStructure(@RequestParam(name = "topLevel")String topLevel,
                                                                        @RequestParam(name = "id") String id,
                                                                        @RequestParam(name = "year") String yearStr){
        try{
            int year = Integer.valueOf(yearStr);
            if (useEstimatedValueCompanyStructure){
                return ResponseEntity.ok().body(finDashboardService.byBudgetCategoryEstimated(topLevel,id,year));
            }else
                throw new Exception("Actual values by company structure is not available yet.");

        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new FinDashboardRes(ex.getMessage(),true));
        }
    }

    @GetMapping("/category")
    public ResponseEntity<FinDashboardRes> getResultsByBudgetCategory(@RequestParam(name = "topLevel")String topLevel,
                                                                             @RequestParam(name = "id") String id,
                                                                      @RequestParam(name = "year") String yearStr){
        try{
            int year = Integer.valueOf(yearStr);
            if (useEstimateValueBudgetCategory){
                return ResponseEntity.ok().body(finDashboardService.byBudgetCategoryEstimated(topLevel,id,year));

            }else{
                return ResponseEntity.ok().body(finDashboardService.byBudgetCategoryActualSpending(topLevel,id,year));
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new FinDashboardRes(ex.getMessage(),true));
        }
    }

    @GetMapping("/po/month/{country}")
    public ResponseEntity<GeneralRes> poHistroyIntegrate(@PathVariable(name = "country") String countryId,
                                                         @RequestParam(name = "type") int type,
                                                         @RequestParam(name = "timeFrame")int timeFrame){
        try{
            return ResponseEntity.ok().body(finDashboardService.retrievePODashboard(countryId,type,timeFrame));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(),true));
        }
    }

    @GetMapping("/po/date/{country}")
    public ResponseEntity<GeneralRes> poHistroyIntegrateByDate(@PathVariable(name = "country") String countryId,
                                                         @RequestParam(name = "type") int type,
                                                         @RequestParam(name = "startDate") String startDate,
                                                         @RequestParam(name = "endDate") String endDate){
        try{
            return ResponseEntity.ok().body(finDashboardService.retrievePODashboard(countryId,type,startDate,endDate));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(),true));
        }

    }
}
