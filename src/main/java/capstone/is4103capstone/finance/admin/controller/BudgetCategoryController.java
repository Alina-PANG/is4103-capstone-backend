package capstone.is4103capstone.finance.admin.controller;

import capstone.is4103capstone.finance.admin.model.req.CreateBudgetCategoryRequest;
import capstone.is4103capstone.finance.admin.model.req.UpdateCategoryReq;

import capstone.is4103capstone.finance.admin.model.res.BudgetCategoryRes;
import capstone.is4103capstone.finance.admin.service.CategoryService;
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
@RequestMapping("/api/fin/category")
@CrossOrigin(origins = "http://localhost:3000")
public class BudgetCategoryController {
    private static final Logger logger = LoggerFactory.getLogger(BudgetCategoryController.class);

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<BudgetCategoryRes> createCategory(@RequestBody CreateBudgetCategoryRequest categoryRequest) {
        if(Authentication.authenticateUser(categoryRequest.getUsername()))
            return ResponseEntity
                    .ok()
                    .body(categoryService.createCategory(categoryRequest));
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
                    .body(categoryService.updateBudgetName(updateCategoryReq));
        else
            return ResponseEntity
                    .badRequest()
                    .body(new BudgetCategoryRes(DefaultData.AUTHENTICATION_ERROR_MSG, true,null));
    }

    @GetMapping("/view-all-levels")
    public @ResponseBody ResponseEntity<Object> viewAllLevelsByCountry(@RequestParam(name = "country",required = true) String countryCode,@RequestParam(name="username", required=true) String username){
        if(Authentication.authenticateUser(username))
            return new ResponseEntity<Object>(categoryService.viewAllLevelsByCountry(countryCode).toString(), HttpStatus.OK);

        else
            return new ResponseEntity<Object>(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG,true), HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/view-by-country")
    public @ResponseBody ResponseEntity<Object> viewCategoriesByCountry(@RequestParam(name="username", required=true) String username, @RequestParam(name="countryCode", required=true) String countryCode){
        if(Authentication.authenticateUser(username))
            return new ResponseEntity<Object>(categoryService.viewCategoriesUnderCountry(countryCode).toString(), HttpStatus.OK);

        else
            return new ResponseEntity<Object>(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG,true), HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/delete")
    public @ResponseBody ResponseEntity<Object> deleteCategory(@RequestParam(name="username", required=true) String username, @RequestParam(name="categoryCode", required=true) String categoryCode){
        if(Authentication.authenticateUser(username))
            return new ResponseEntity<Object>(categoryService.removeBudgetCategory(categoryCode,username).toString(), HttpStatus.OK);

        else
            return new ResponseEntity<Object>(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG,true), HttpStatus.BAD_REQUEST);
    }



}
