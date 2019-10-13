package capstone.is4103capstone.finance.requestsMgmt.service;

import capstone.is4103capstone.finance.requestsMgmt.model.req.CreateTravelRequest;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTFormResponse;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTListResponse;
import org.springframework.stereotype.Service;

@Service
public class TravelService {


    public TTFormResponse createTravelPlan(CreateTravelRequest req){

        return new TTFormResponse();
    }

    public TTListResponse retrieveTravelPlansByUser(String username){

        return new TTListResponse();
    }

    public TTFormResponse getTravelPlanDetails(String planId){
        return new TTFormResponse();
    }
}
