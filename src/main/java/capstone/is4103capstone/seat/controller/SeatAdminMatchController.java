package capstone.is4103capstone.seat.controller;

import capstone.is4103capstone.seat.model.SeatAdminMatchGroupModel;
import capstone.is4103capstone.seat.service.SeatAdminMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seatManagement/access")
@CrossOrigin
public class SeatAdminMatchController {

    @Autowired
    private SeatAdminMatchService seatAdminMatchService;

    // ---------------------------------- GET: Retrieve ----------------------------------

    @GetMapping("/hierarchy")
    public ResponseEntity retrieveAccessibleHierarchies() {
        SeatAdminMatchGroupModel seatAdminMatchGroupModel = seatAdminMatchService.retrieveAccessibleHierarchyLevels();
        return ResponseEntity.ok(seatAdminMatchGroupModel);
    }

    @GetMapping("/office")
    public ResponseEntity retrieveAccessibleOffices() {
        SeatAdminMatchGroupModel seatAdminMatchGroupModel = seatAdminMatchService.retrieveAccessibleOffices();
        return ResponseEntity.ok(seatAdminMatchGroupModel);
    }
}
