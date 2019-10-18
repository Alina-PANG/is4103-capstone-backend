package capstone.is4103capstone.supplychain.controller;

import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.req.ApproveChildContractReq;
import capstone.is4103capstone.supplychain.model.req.CreateChildContractReq;
import capstone.is4103capstone.supplychain.service.ChildContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/child-contract")
@CrossOrigin(origins = "http://localhost:3000")
public class ChildContractController {
    private static final Logger logger = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    private ChildContractService childContractService;

    @PostMapping("/create-child-contract")
    public ResponseEntity<GeneralRes> createChildContract(@RequestBody CreateChildContractReq createChildContractReq) {
        if (Authentication.authenticateUser(createChildContractReq.getModifierUsername())) {
            return ResponseEntity
                    .ok()
                    .body(childContractService.createChildContract(createChildContractReq));
        } else {
            return ResponseEntity
                    .ok()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }

    @GetMapping("/get-child-contract/{id}")
    public ResponseEntity<GeneralRes> getChildContract(@PathVariable("id") String id, @RequestParam(name = "username", required = true) String username){
        if (Authentication.authenticateUser(username)) {
            return ResponseEntity
                    .ok()
                    .body(childContractService.getChildContract(id));
        }else{
            return ResponseEntity
                    .ok()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }

    @PostMapping("/update-child-contract/{id}")
    public ResponseEntity<GeneralRes> updateChildContract(@RequestBody CreateChildContractReq updateChildContractReq, @PathVariable("id") String id) {
        if (Authentication.authenticateUser(updateChildContractReq.getModifierUsername())) {
            return ResponseEntity
                    .ok()
                    .body(childContractService.updateChildContract(updateChildContractReq, id));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }

    @GetMapping("/get-child-contracts-by-master-contract/{masterContractId}")
    public ResponseEntity<GeneralRes> getChildContractsByMasterContract(@PathVariable("masterContractId") String masterContractId, @RequestParam(name = "username", required = true) String username){
        if (Authentication.authenticateUser(username)) {
            return ResponseEntity
                    .ok()
                    .body(childContractService.getChildContractsByMasterContractId(masterContractId));
        }else{
            return ResponseEntity
                    .ok()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }
}