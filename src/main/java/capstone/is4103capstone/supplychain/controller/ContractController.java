package capstone.is4103capstone.supplychain.controller;

import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.req.CreateContractReq;
import capstone.is4103capstone.supplychain.service.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contract")
@CrossOrigin(origins = "http://localhost:3000")
public class ContractController {
    private static final Logger logger = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    private ContractService contractService;

    @PostMapping("/create-contract")
    public ResponseEntity<GeneralRes> createContract(@RequestBody CreateContractReq createContractReq) {
        if (Authentication.authenticateUser(createContractReq.getModifierUsername())) {
            return ResponseEntity
                    .ok()
                    .body(contractService.CreateContract(createContractReq));
        } else {
            return ResponseEntity
                    .ok()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }

    @GetMapping("/get-contract/{id}")
    public ResponseEntity<GeneralRes> getContract(@PathVariable("id") String id, @RequestParam(name = "username", required = true) String username){
        if (Authentication.authenticateUser(username)) {
            return ResponseEntity
                    .ok()
                    .body(contractService.getContract(id));
        }else{
            return ResponseEntity
                    .ok()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }

    @PostMapping("/update-contract/{id}")
    public ResponseEntity<GeneralRes> updateContract(@RequestBody CreateContractReq updateContractReq, @PathVariable("id") String id) {
        if (Authentication.authenticateUser(updateContractReq.getModifierUsername())) {
            return ResponseEntity
                    .ok()
                    .body(contractService.UpdateContract(updateContractReq, id));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }

    @GetMapping("/get-all-contracts")
    public  ResponseEntity<GeneralRes> getAllContract(@RequestParam(name = "username", required = true) String username){
        if (Authentication.authenticateUser(username)) {
            return ResponseEntity
                    .ok()
                    .body(contractService.getAllContracts());
        }else{
            return ResponseEntity
                    .ok()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }
}