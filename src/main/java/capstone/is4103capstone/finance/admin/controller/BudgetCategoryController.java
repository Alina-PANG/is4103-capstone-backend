package capstone.is4103capstone.finance.admin.controller;

import capstone.is4103capstone.finance.admin.model.req.CreateBudgetCategoryRequest;
import capstone.is4103capstone.finance.admin.model.res.BudgetCategoryRes;
import capstone.is4103capstone.finance.admin.service.CategoryService;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fin/category")
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







}
