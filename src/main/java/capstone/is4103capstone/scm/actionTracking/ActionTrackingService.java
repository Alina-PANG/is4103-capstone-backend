package capstone.is4103capstone.scm.actionTracking;

import capstone.is4103capstone.scm.actionTracking.model.req.CreateActionTrackingReq;
import capstone.is4103capstone.scm.actionTracking.model.res.CreateActionTrackingRes;
import org.springframework.stereotype.Service;

@Service
public class ActionTrackingService {
    public CreateActionTrackingRes createActionTracking(CreateActionTrackingReq createActionTrackingReq){
        return new CreateActionTrackingRes();
    }
}
