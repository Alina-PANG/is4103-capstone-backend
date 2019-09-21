package capstone.is4103capstone.finance.admin.controller;

import capstone.is4103capstone.finance.admin.model.req.CreateFXRequest;
import capstone.is4103capstone.finance.admin.model.req.FXRecordQueryReq;
import capstone.is4103capstone.finance.admin.model.res.ViewFXRecordRes;
import capstone.is4103capstone.finance.admin.service.FXTableService;
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
@RequestMapping("/api/fx-table")
public class FXTableController {
    private static final Logger logger = LoggerFactory.getLogger(FXTableController.class);

    @Autowired
    FXTableService fxService;

    @PostMapping("/create")
    public @ResponseBody
    ResponseEntity<Object> createFXRecord(@RequestBody CreateFXRequest req) {

        if(Authentication.authenticateUser(req.getUsername())){
            return new ResponseEntity<Object>(fxService.createFXRecord(req).toString(), HttpStatus.OK);
        }

        else
            return new ResponseEntity<Object>(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG,true), HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/view")
    public ResponseEntity<ViewFXRecordRes> viewFxRecords(@RequestBody FXRecordQueryReq req){
        if(Authentication.authenticateUser(req.getUsername())){
            return ResponseEntity
                    .ok()
                    .body(fxService.viewFXRecordRes(req));
        }

        else
            return ResponseEntity
                    .badRequest()
                    .body(new ViewFXRecordRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));

    }

}
