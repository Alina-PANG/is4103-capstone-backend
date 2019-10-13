package capstone.is4103capstone.finance.requestsMgmt.controller;

import capstone.is4103capstone.finance.requestsMgmt.model.req.CreateTrainingRequest;
import capstone.is4103capstone.finance.requestsMgmt.model.req.CreateTravelRequest;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTFormResponse;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTListResponse;
import capstone.is4103capstone.finance.requestsMgmt.service.TrainingService;
import capstone.is4103capstone.finance.requestsMgmt.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TravelTrainController {

    @Autowired
    TravelService travelService;
    @Autowired
    TrainingService trainingService;

    @PostMapping("/travel")
    public ResponseEntity<TTFormResponse> createTrainingRequest(@RequestBody CreateTrainingRequest req){
        try{
            return ResponseEntity.badRequest().body(trainingService.createTrainingPlan(req));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new TTFormResponse(ex.getMessage(),true));
        }

    }

    @PostMapping("/train")
    public ResponseEntity<TTFormResponse> createTravelRequest(@RequestBody CreateTravelRequest req){
        try{
            return ResponseEntity.badRequest().body(travelService.createTravelPlan(req));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new TTFormResponse(ex.getMessage(),true));
        }

    }

    @GetMapping("/travel/view-my")
    public ResponseEntity<TTListResponse> getTrainingPlansByUser(){
        String username="";//get from
        try{
            return ResponseEntity.badRequest().body(trainingService.retrieveTrainingPlansByUser(username));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new TTListResponse(ex.getMessage(),true));
        }

    }

    @GetMapping("/train/view-my")
    public ResponseEntity<TTListResponse> getTravelPlansByUser(){
        String username = "";
        try{
            return ResponseEntity.badRequest().body(travelService.retrieveTravelPlansByUser(username));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new TTListResponse(ex.getMessage(),true));
        }
    }

    @GetMapping("/travel/{id}")
    public ResponseEntity<TTFormResponse> getTrainingPlanDetails(@PathVariable(name = "id") String planId){
        try{
            return ResponseEntity.badRequest().body(trainingService.getTrainingPlanDetails(planId));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new TTFormResponse(ex.getMessage(),true));
        }

    }
    @GetMapping("/train/{id}")
    public ResponseEntity<TTFormResponse> getTravelPlanDetails(@PathVariable(name = "id") String planId){
        try{
            return ResponseEntity.badRequest().body(travelService.getTravelPlanDetails(planId));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new TTFormResponse(ex.getMessage(),true));
        }
    }

}
