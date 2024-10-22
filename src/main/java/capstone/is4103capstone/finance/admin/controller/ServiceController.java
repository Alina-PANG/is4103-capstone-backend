package capstone.is4103capstone.finance.admin.controller;

import capstone.is4103capstone.finance.admin.model.req.CreateServiceRequest;
import capstone.is4103capstone.finance.admin.model.res.ServiceListRes;
import capstone.is4103capstone.finance.admin.service.ServiceServ;
import capstone.is4103capstone.general.AuthenticationTools;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fin-item")
@CrossOrigin(origins = "http://localhost:3000")
public class ServiceController {
    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @Autowired
    ServiceServ serviceService;

    @PostMapping("/create")
    public @ResponseBody
    ResponseEntity<GeneralRes> createservice(@RequestBody CreateServiceRequest req) {
        try{
            return ResponseEntity.ok().body(serviceService.createService(req));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(new GeneralRes(ex.getMessage(),true));
        }
    }

    @GetMapping("/view")
    public ResponseEntity<ServiceListRes> viewserviceUnderSub2(@RequestParam(name="username", required=true) String username, @RequestParam(name="sub2Code", required=true) String sub2Code){
        if(AuthenticationTools.authenticateUser(username)){
            return ResponseEntity
                    .ok()
                    .body(serviceService.viewserviceItemsBySub2(sub2Code));
        }

        else
            return ResponseEntity
                    .badRequest()
                    .body(new ServiceListRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));

    }

    @GetMapping
    public ResponseEntity<ServiceListRes> viewAllServices(){
        try{
            return ResponseEntity
                    .ok()
                    .body(serviceService.retrieveAllService());
        }catch (Exception ex){
            return ResponseEntity
                    .badRequest()
                    .body(new ServiceListRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }


    }


}
