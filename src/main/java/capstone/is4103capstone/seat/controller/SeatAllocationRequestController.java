package capstone.is4103capstone.seat.controller;

import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.entities.seat.SeatAllocationRequest;
import capstone.is4103capstone.general.model.ApprovalTicketModel;
import capstone.is4103capstone.seat.model.seatAllocationRequest.CreateSeatAllocationRequestModel;
import capstone.is4103capstone.seat.model.seatAllocationRequest.ApproveSeatAllocationRequestModel;
import capstone.is4103capstone.seat.model.seatAllocationRequest.SeatAllocationRequestGroupModel;
import capstone.is4103capstone.seat.model.seatAllocationRequest.SeatAllocationRequestModel;
import capstone.is4103capstone.seat.model.seatMap.SeatMapModelForAllocationWithHighlight;
import capstone.is4103capstone.seat.service.EntityModelConversionService;
import capstone.is4103capstone.seat.service.SeatAllocationRequestService;
import capstone.is4103capstone.seat.service.SeatAllocationService;
import capstone.is4103capstone.seat.service.SeatMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/seatAllocation/request")
@CrossOrigin
public class SeatAllocationRequestController {

    private static final Logger logger = LoggerFactory.getLogger(SeatAllocationRequestController.class);

    @Autowired
    private SeatAllocationService seatAllocationService;
    @Autowired
    private SeatMapService seatMapService;
    @Autowired
    private SeatAllocationRequestService seatAllocationRequestService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EntityModelConversionService entityModelConversionService;

    // ---------------------------------- POST: Create ----------------------------------

    @PostMapping("/new")
    public ResponseEntity createNewSeatAllocationRequest(@RequestBody CreateSeatAllocationRequestModel createSeatAllocationRequestModel) {
        seatAllocationRequestService.createNewSeatAllocationRequest(createSeatAllocationRequestModel);
        return ResponseEntity.ok("Create seat allocation request successfully!");
    }



    // ---------------------------------- GET: Retrieve ----------------------------------

    // Return the seat maps that have seats which are available for the seat allocation request. The seat map will highlight
    //  the seats which fulfill the requests. The reviewer of the request can then choose a seat to allocate through the processing
    //  functions.
    @GetMapping("/availability")
    public ResponseEntity retrieveAvailableSeatMapsForAllocationByHierarchy(@RequestParam(name="hierarchy", required=true) String hierarchy,
                                                         @RequestParam(name="hierarchyId", required=true) String hierarchyId,
                                                         @RequestParam(name="requestId", required=true) String requestId) {

        SeatAllocationRequest seatAllocationRequest = seatAllocationRequestService.retrieveSeatAllocationRequestById(requestId);
        List<SeatMapModelForAllocationWithHighlight> seatMapModels = seatMapService.retrieveAvailableSeatMapsForAllocationByHierarchy(
                hierarchy, hierarchyId, seatAllocationRequest);
        for (SeatMapModelForAllocationWithHighlight seatMapModel :
                seatMapModels) {
            Collections.sort(seatMapModel.getSeatModelsForAllocationViaSeatMap());
        }
        return ResponseEntity.ok(seatMapModels);
    }

//    @GetMapping
//    public ResponseEntity retrieveSeatAllocationRequestById(@RequestParam(name="requestId", required=true) String requestId) {
//        SeatAllocationRequest seatAllocationRequest = seatAllocationRequestService.retrieveSeatAllocationRequestById(requestId);
//        SeatAllocationRequestModel seatAllocationRequestModel = entityModelConversionService.convertSeatAllocationRequestEntityToModel(seatAllocationRequest);
//        return ResponseEntity.ok(seatAllocationRequestModel);
//    }

    // Return all the approval tickets assigned to a particular employee, including ones that have already been processed and the pending ones
    // The return type will be seat allocation request including the required approval tickets
    //  The employee has access to lower-level approval tickets under the same seat allocation request
    //  The employee has NO access to higher-level approval tickets under the same seat allocation request
    @GetMapping("/assigned")
    public ResponseEntity retrieveSeatAllocationRequestsWithApprovalTicketsByReviewerId(@RequestParam(name="reviewerId", required=true) String reviewerId) {
        List<SeatAllocationRequest> seatAllocationRequests = seatAllocationRequestService.retrieveSeatAllocationRequestsWithApprovalTicketsByReviewerId(reviewerId);
        SeatAllocationRequestGroupModel seatAllocationRequestGroupModel = new SeatAllocationRequestGroupModel();
        List<SeatAllocationRequestModel> seatAllocationRequestModels = new ArrayList<>();
        for (SeatAllocationRequest requestEntity :
                seatAllocationRequests) {
            seatAllocationRequestModels.add(entityModelConversionService.convertSeatAllocationRequestEntityToModel(requestEntity));
        }
        seatAllocationRequestGroupModel.setSeatAllocationRequestModels(seatAllocationRequestModels);
        return ResponseEntity.ok(seatAllocationRequestGroupModel);
    }

    @GetMapping("/owned")
    public ResponseEntity retrieveSeatAllocationRequestsWithApprovalTicketsByRequesterId(@RequestParam(name="requesterId", required=true) String requesterId) {
        List<SeatAllocationRequest> seatAllocationRequests = seatAllocationRequestService.retrieveSeatAllocationRequestsWithApprovalTicketsByRequesterId(requesterId);
        SeatAllocationRequestGroupModel seatAllocationRequestGroupModel = new SeatAllocationRequestGroupModel();
        List<SeatAllocationRequestModel> seatAllocationRequestModels = new ArrayList<>();
        for (SeatAllocationRequest requestEntity :
                seatAllocationRequests) {
            seatAllocationRequestModels.add(entityModelConversionService.convertSeatAllocationRequestEntityToModel(requestEntity));
        }
        seatAllocationRequestGroupModel.setSeatAllocationRequestModels(seatAllocationRequestModels);
        return ResponseEntity.ok(seatAllocationRequestGroupModel);
    }



    // ---------------------------------- PUT: Update ----------------------------------

    @PutMapping("/escalate")
    public ResponseEntity escalateNewSeatAllocationRequest(@RequestBody ApprovalTicketModel approvalTicketModel) {
        seatAllocationRequestService.escalateSeatAllocationRequest(approvalTicketModel);
        return ResponseEntity.ok("Escalated seat allocation request successfully!");
    }

    // The approval ticket model passed in should already exists, i.e., a user should see the approval ticket pending and then
    //  decide to either approve, reject or escalate.
    @PutMapping("/approve")
    public ResponseEntity approveSeatAllocationRequest(@RequestBody ApproveSeatAllocationRequestModel approveSeatAllocationRequestModel) {
        seatAllocationRequestService.approveSeatAllocationRequest(approveSeatAllocationRequestModel);
        return ResponseEntity.ok("Approved allocation request successfully!");
    }

    @PutMapping("/reject")
    public ResponseEntity rejectSeatAllocationRequest(@RequestBody ApprovalTicketModel approvalTicketModel) {
        seatAllocationRequestService.rejectSeatAllocationRequest(approvalTicketModel);
        return ResponseEntity.ok("Rejected allocation request successfully!");
    }


    // ---------------------------------- DELETE: Delete ----------------------------------

    @DeleteMapping
    public ResponseEntity deletePendingSeatAllocationRequest(@RequestParam(name="requestId", required=true) String requestId) {
        seatAllocationRequestService.deletePendingSeatAllocationRequest(requestId);
        return ResponseEntity.ok("Deleted allocation request successfully!");
    }
}
