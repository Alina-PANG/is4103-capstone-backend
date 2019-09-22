package capstone.is4103capstone.finance.admin.controller;

import capstone.is4103capstone.finance.admin.model.req.CreateSub1Sub2Request;
import capstone.is4103capstone.finance.admin.model.req.UpdateCategoryReq;
import capstone.is4103capstone.finance.admin.model.res.BudgetCategoryRes;
import capstone.is4103capstone.finance.admin.service.Sub1Service;
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
@RequestMapping("/api/fin/sub1")
@CrossOrigin(origins = "http://localhost:3000")
public class Sub1CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(Sub1CategoryController.class);

    @Autowired
    Sub1Service sub1Service;

    @PostMapping("/create")
    public ResponseEntity<BudgetCategoryRes> createCategory(@RequestBody CreateSub1Sub2Request req) {
        if(Authentication.authenticateUser(req.getUsername()))
            return ResponseEntity
                    .ok()
                    .body(sub1Service.createSub1(req));
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
                    .body(sub1Service.updateSub1Name(updateCategoryReq));
        else
            return ResponseEntity
                    .badRequest()
                    .body(new BudgetCategoryRes(DefaultData.AUTHENTICATION_ERROR_MSG, true,null));
    }

    @GetMapping("/view-by-category")
    public @ResponseBody ResponseEntity<Object> viewCategoriesByCountry(@RequestParam(name="username", required=true) String username, @RequestParam(name="categoryCode", required=true) String categoryCode){
        if(Authentication.authenticateUser(username))
            return new ResponseEntity<Object>(sub1Service.viewSub1sUnderCategory(categoryCode).toString(), HttpStatus.OK);

        else
            return new ResponseEntity<Object>(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG,true), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/delete")
    public @ResponseBody ResponseEntity<Object> deleteCategory(@RequestParam(name="username", required=true) String username, @RequestParam(name="sub1CodeToDelete", required=true) String sub1CodeToDelete){
        if(Authentication.authenticateUser(username))
            return new ResponseEntity<Object>(sub1Service.removeSub1Category(sub1CodeToDelete,username).toString(), HttpStatus.OK);

        else
            return new ResponseEntity<Object>(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG,true), HttpStatus.BAD_REQUEST);
    }
}
