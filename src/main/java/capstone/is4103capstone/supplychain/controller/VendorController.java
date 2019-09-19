package capstone.is4103capstone.supplychain.controller;

import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.req.CreateVendorReq;
import capstone.is4103capstone.supplychain.service.VendorService;
import org.hibernate.annotations.GeneratorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {
    private static final Logger logger = LoggerFactory.getLogger(VendorController.class);

    @Autowired
    private VendorService vendorService;

    @PostMapping("/createBudget")
    public ResponseEntity<GeneralRes> createVendor(@RequestBody CreateVendorReq createVendorReq) {
        if (Authentication.authenticateUser(createVendorReq.getUsername())) {
            return ResponseEntity
                    .ok()
                    .body(vendorService.createNewVendor(createVendorReq));
        } else {
            return ResponseEntity
                    .ok()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }

    @PostMapping("/updateVendor/{id}")
    public ResponseEntity<GeneralRes> updateVendor(@RequestBody CreateVendorReq updateVendorReq, @PathVariable("id") String id) {
        if (Authentication.authenticateUser(updateVendorReq.getUsername())) {
            return ResponseEntity
                    .ok()
                    .body(vendorService.updateVendor(updateVendorReq, id));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }

    @GetMapping("/getVendor/{id}")
    public ResponseEntity<GeneralRes> getVendor(@PathVariable("id") String id, @RequestParam(name = "username", required = true) String username) {
        if (Authentication.authenticateUser(username)) {
            return ResponseEntity
                    .ok()
                    .body(vendorService.getVendor(id));
        }else{
            return ResponseEntity
                    .ok()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }
}