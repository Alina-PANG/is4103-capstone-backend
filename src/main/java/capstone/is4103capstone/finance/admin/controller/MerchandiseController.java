package capstone.is4103capstone.finance.admin.controller;

import capstone.is4103capstone.finance.admin.model.req.CreateMerchandiseRequest;
import capstone.is4103capstone.finance.admin.model.res.MerchandiseListRes;
import capstone.is4103capstone.finance.admin.service.MerchandiseService;
import capstone.is4103capstone.general.Authentication;
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
public class MerchandiseController {
    private static final Logger logger = LoggerFactory.getLogger(MerchandiseController.class);

    @Autowired
    MerchandiseService merchandiseService;

    @PostMapping("/create")
    public @ResponseBody
    ResponseEntity<Object> createMerchandise(@RequestBody CreateMerchandiseRequest req) {

        if(Authentication.authenticateUser(req.getUsername())){
            return new ResponseEntity<Object>(merchandiseService.createMerchandise(req).toString(), HttpStatus.OK);
        }
        else
            return new ResponseEntity<Object>(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG,true), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/view")
    public ResponseEntity<MerchandiseListRes> viewMerchandiseUnderSub2(@RequestParam(name="username", required=true) String username,  @RequestParam(name="sub2Code", required=true) String sub2Code){
        if(Authentication.authenticateUser(username)){
            return ResponseEntity
                    .ok()
                    .body(merchandiseService.viewMerchandiseItemsBySub2(sub2Code));
        }

        else
            return ResponseEntity
                    .badRequest()
                    .body(new MerchandiseListRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));

    }

}
