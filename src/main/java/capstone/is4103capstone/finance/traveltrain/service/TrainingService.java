package capstone.is4103capstone.finance.traveltrain.service;

import capstone.is4103capstone.finance.traveltrain.model.req.CreateTrainingRequest;
import capstone.is4103capstone.finance.traveltrain.model.req.CreateTravelRequest;
import capstone.is4103capstone.finance.traveltrain.model.res.TTFormResponse;
import capstone.is4103capstone.finance.traveltrain.model.res.TTListResponse;
import org.springframework.stereotype.Service;

@Service
public class TrainingService {

    public TTFormResponse createTrainingPlan(CreateTrainingRequest req){

        return new TTFormResponse();
    }

    public TTListResponse retrieveTrainingPlansByUser(String username){

        return new TTListResponse();
    }

    public TTFormResponse getTrainingPlanDetails(String planId){
        return new TTFormResponse();
    }

}
