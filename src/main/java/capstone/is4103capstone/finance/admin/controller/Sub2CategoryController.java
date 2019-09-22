package capstone.is4103capstone.finance.admin.controller;

import capstone.is4103capstone.finance.admin.model.req.CreateSub1Sub2Request;
import capstone.is4103capstone.finance.admin.model.req.UpdateCategoryReq;
import capstone.is4103capstone.finance.admin.model.res.BudgetCategoryRes;
import capstone.is4103capstone.finance.admin.service.Sub1Service;
import capstone.is4103capstone.finance.admin.service.Sub2Service;
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
@RequestMapping("/api/fin/sub2")
@CrossOrigin(origins = "http://localhost:3000")
public class Sub2CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(BudgetCategoryController.class);

    @Autowired
    Sub2Service sub2Service;

    @PostMapping("/create")
    public ResponseEntity<BudgetCategoryRes> createSub2Category(@RequestBody CreateSub1Sub2Request req) {
        if(Authentication.authenticateUser(req.getUsername()))
            return ResponseEntity
                    .ok()
                    .body(sub2Service.createSub2(req));
        else
            return ResponseEntity
                    .badRequest()
                    .body(new BudgetCategoryRes(DefaultData.AUTHENTICATION_ERROR_MSG, true,null));
    }

    @PostMapping("/update")
    public ResponseEntity<BudgetCategoryRes> updateCategory(@RequestBody UpdateCategoryReq updateCategoryReq) {
        if(Authentication.authenticateUser(updateCategoryReq.getUsername()))
            return ResponseEntity
                    .ok()
                    .body(sub2Service.updateSub2Name(updateCategoryReq));
        else
            return ResponseEntity
                    .badRequest()
                    .body(new BudgetCategoryRes(DefaultData.AUTHENTICATION_ERROR_MSG, true,null));
    }

    @GetMapping("/view-by-sub1")
    public @ResponseBody ResponseEntity<Object> viewCategoriesByCountry(@RequestParam(name="username", required=true) String username, @RequestParam(name="sub1Code", required=true) String sub1Code){
        if(Authentication.authenticateUser(username))
            return new ResponseEntity<Object>(sub2Service.viewSub2sUnderSub1Code(sub1Code).toString(), HttpStatus.OK);

        else
            return new ResponseEntity<Object>(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG,true), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/delete")
    public @ResponseBody ResponseEntity<Object> deleteCategory(@RequestParam(name="username", required=true) String username, @RequestParam(name="sub2CodeToDelete", required=true) String sub2CodeToDelete){
        if(Authentication.authenticateUser(username))
            return new ResponseEntity<Object>(sub2Service.removeSub2Category(sub2CodeToDelete,username).toString(), HttpStatus.OK);

        else
            return new ResponseEntity<Object>(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG,true), HttpStatus.BAD_REQUEST);
    }
}
