package capstone.is4103capstone.finance.requestsMgmt.service;

import capstone.is4103capstone.finance.requestsMgmt.model.req.CreateTrainingRequest;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTFormResponse;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTListResponse;
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
