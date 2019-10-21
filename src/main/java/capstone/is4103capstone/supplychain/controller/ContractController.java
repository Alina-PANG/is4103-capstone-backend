package capstone.is4103capstone.supplychain.controller;

import capstone.is4103capstone.general.AuthenticationTools;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.req.ApproveContractReq;
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
    ContractService contractService;

    @PostMapping("/create-contract")
    public ResponseEntity<GeneralRes> createContract(@RequestBody CreateContractReq createContractReq) {
        if (AuthenticationTools.authenticateUser(createContractReq.getModifierUsername())) {
            return ResponseEntity
                    .ok()
                    .body(contractService.createContract(createContractReq));
        } else {
            return ResponseEntity
                    .ok()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }

    @GetMapping("/get-contract/{id}")
    public ResponseEntity<GeneralRes> getContract(@PathVariable("id") String id, @RequestParam(name = "username", required = true) String username){
        if (AuthenticationTools.authenticateUser(username)) {
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
        if (AuthenticationTools.authenticateUser(updateContractReq.getModifierUsername())) {
            return ResponseEntity
                    .ok()
                    .body(contractService.updateContract(updateContractReq, id));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }

    @GetMapping("/get-all-contracts")
    public  ResponseEntity<GeneralRes> getAllContract(@RequestParam(name = "username", required = true) String username){
        if (AuthenticationTools.authenticateUser(username)) {
            return ResponseEntity
                    .ok()
                    .body(contractService.getAllContracts());
        }else{
            return ResponseEntity
                    .ok()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }

    @GetMapping("/get-contracts-by-team/{teamId}")
    public ResponseEntity<GeneralRes> getContractsByTeamId(@PathVariable("teamId") String teamId, @RequestParam(name = "username", required = true) String username){
        if (AuthenticationTools.authenticateUser(username)) {
            return ResponseEntity
                    .ok()
                    .body(contractService.getContractsByTeamId(teamId));
        }else{
            return ResponseEntity
                    .ok()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }

    @GetMapping("/get-contracts-by-vendor/{vendorId}")
    public ResponseEntity<GeneralRes> getContractsByVendorId(@PathVariable("vendorId") String vendorId, @RequestParam(name = "username", required = true) String username){
        if (AuthenticationTools.authenticateUser(username)) {
            return ResponseEntity
                    .ok()
                    .body(contractService.getContractsByVendorId(vendorId));
        }else{
            return ResponseEntity
                    .ok()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }

    @PostMapping("/approve-contract")
    public ResponseEntity approveContract(@RequestBody ApproveContractReq approveContractReq){
        if(AuthenticationTools.authenticateUser(approveContractReq.getUsername()))
            return ResponseEntity
                    .ok()
                    .body(contractService.approveContract(approveContractReq));
        else
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
    }

    @PostMapping("/request-approve/{contractId}")
    public ResponseEntity requestApprove(@PathVariable("contractId") String contractId, @RequestParam(name = "username", required = true) String username){
        if (AuthenticationTools.authenticateUser(username)) {
            return ResponseEntity
                    .ok()
                    .body(contractService.createApprovalTicket(username, contractId, "Approver please review the contract and related child contracts."));
        }else{
            return ResponseEntity
                    .ok()
                    .body(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true));
        }
    }
}