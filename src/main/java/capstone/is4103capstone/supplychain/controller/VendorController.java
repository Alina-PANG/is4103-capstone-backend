package capstone.is4103capstone.supplychain.controller;

import capstone.is4103capstone.general.AuthenticationTools;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
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
    VendorService vendorService;

    @PostMapping("/create-vendor")
    public ResponseEntity<GeneralRes> createVendor(@RequestBody CreateVendorReq createVendorReq) {
        try {

            return ResponseEntity
                    .ok()
                    .body(vendorService.createNewVendor(createVendorReq));
        }
        catch (Exception ex){
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(ex.getMessage(), true));
        }
    }

    @PostMapping("/update-vendor/{id}")
    public ResponseEntity<GeneralRes> updateVendor(@RequestBody CreateVendorReq updateVendorReq, @PathVariable("id") String id) {
        if (AuthenticationTools.authenticateUser(updateVendorReq.getUsername())) {
            return ResponseEntity
                    .ok()
                    .body(vendorService.updateVendor(updateVendorReq, id));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }

    @GetMapping("/get-vendor/{id}")
    public ResponseEntity<GeneralRes> getVendor(@PathVariable("id") String id, @RequestParam(name = "username", required = true) String username) {
        if (AuthenticationTools.authenticateUser(username)) {
            return ResponseEntity
                    .ok()
                    .body(vendorService.getVendor(id));
        }else{
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }

    @GetMapping("/get-all-vendors")
    public ResponseEntity<GeneralRes> getAllVendors(@RequestParam(name = "username", required = true) String username){
        if (AuthenticationTools.authenticateUser(username)) {
            return ResponseEntity
                    .ok()
                    .body(vendorService.getAllVendors());
        }else{
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }
}