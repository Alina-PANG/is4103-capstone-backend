package capstone.is4103capstone.finance.requestsMgmt.controller;

import capstone.is4103capstone.finance.requestsMgmt.model.req.CreateBJFReq;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTFormResponse;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTListResponse;
import capstone.is4103capstone.finance.requestsMgmt.service.BJFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/bjf")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class BJFController {
    @Autowired
    BJFService bjfService;

    @PostMapping
    public ResponseEntity<TTFormResponse> createBJF(@RequestBody CreateBJFReq req){
        try{
            return ResponseEntity.ok().body(new TTFormResponse("Successfully created",false,bjfService.createBJF(req)));
        }catch (Exception ex){
            ex.printStackTrace();

            return ResponseEntity.badRequest().body(new TTFormResponse(ex.getMessage(),true));
        }
    }

    @GetMapping("/{bjfId}")
    public ResponseEntity<TTFormResponse> getBJFDetails(@PathVariable(name = "bjfId") String bjfId){
        try{
            return ResponseEntity.ok().body(new TTFormResponse("Successfully retrieved",false,bjfService.getBJFDetails(bjfId)));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new TTFormResponse(ex.getMessage(),true));
        }
    }

//    @GetMapping("/all")
//    public ResponseEntity<TTListResponse> getAllProjects(){
//        try{
//            return ResponseEntity.ok().body(
//                    new TTListResponse("Successfully retrieved",false, projectService.retrieveAllProjects()));
//        }catch (Exception ex){
//            ex.printStackTrace();
//            return ResponseEntity.badRequest().body(new TTListResponse(ex.getMessage(),true));
//        }
//    }

    @GetMapping("/view-my-requests/{userId}")
    public ResponseEntity<TTListResponse> getBJFSubmittedByUser(@PathVariable(name = "userId") String idOrUsername){
        try{
            return ResponseEntity.ok().body(
                    new TTListResponse("Successfully retrieved", bjfService.getBJFByRequester(idOrUsername)));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new TTListResponse(ex.getMessage(),true));
        }
    }

    @GetMapping("/view-my-approval/{userId}")
    public ResponseEntity<TTListResponse> getBJFWaitingForUserApprove(@PathVariable(name = "userId") String idOrUsername){
        try{
            return ResponseEntity.ok().body(
                    new TTListResponse("Successfully retrieved", bjfService.getBJFByApprover(idOrUsername)));
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new TTListResponse(ex.getMessage(),true));
        }
    }
}
