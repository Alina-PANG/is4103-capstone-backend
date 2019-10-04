package capstone.is4103capstone.supplychain.controller;

import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.req.AddBusinessUnitReq;
import capstone.is4103capstone.supplychain.model.req.CreateVendorReq;
import capstone.is4103capstone.supplychain.service.VendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor")
@CrossOrigin(origins = "http://localhost:3000")
public class VendorController {
    private static final Logger logger = LoggerFactory.getLogger(VendorController.class);

    @Autowired
    private VendorService vendorService;

    @PostMapping("/create-vendor")
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

    @PostMapping("/update-vendor/{id}")
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

    @PostMapping("/add-business-unit/{id}")
    public ResponseEntity<GeneralRes> addBusinessUnit(@RequestBody AddBusinessUnitReq addBusinessUnitReq, @PathVariable("id") String id){
        if (Authentication.authenticateUser(addBusinessUnitReq.getUsername())) {
            return ResponseEntity
                    .ok()
                    .body(vendorService.addBusinessUnit(addBusinessUnitReq, id));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }

    @GetMapping("/get-vendor/{id}")
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

    @GetMapping("/get-all-vendors")
    public ResponseEntity<GeneralRes> getAllVendors(@RequestParam(name = "username", required = true) String username){
        if (Authentication.authenticateUser(username)) {
            return ResponseEntity
                    .ok()
                    .body(vendorService.getAllVendors());
        }else{
            return ResponseEntity
                    .ok()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }
}