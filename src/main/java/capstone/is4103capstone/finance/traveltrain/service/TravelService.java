package capstone.is4103capstone.finance.traveltrain.service;

import capstone.is4103capstone.finance.traveltrain.model.req.CreateTravelRequest;
import capstone.is4103capstone.finance.traveltrain.model.res.TTFormResponse;
import capstone.is4103capstone.finance.traveltrain.model.res.TTListResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

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
