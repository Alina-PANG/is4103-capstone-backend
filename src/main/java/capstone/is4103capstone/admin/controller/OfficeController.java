package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.dto.OfficeDto;
import capstone.is4103capstone.admin.service.OfficeService;
import capstone.is4103capstone.util.exception.DbObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/ce/office")
public class OfficeController {

    @Autowired
    OfficeService officeService;

    // CREATE (POST)
    @PostMapping
    public OfficeDto createNewOffice(@RequestBody OfficeDto input) {
        return officeService.createNewOffice(input);
    }

    // RETRIEVE (GET)
    @GetMapping
    public List<OfficeDto> getAllOffices() {
        return officeService.getAllOffices();
    }

    @GetMapping("/{uuid}")
    public OfficeDto getOffice(@PathVariable(name = "uuid") String input) {
        try {
            return officeService.getOfficeByUuid(input);
        } catch (DbObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    // UPDATE (PUT)
    @PutMapping
    public OfficeDto updateOffice(@RequestBody OfficeDto input) {
        return officeService.updateOffice(input);
    }

    // DELETE (DELETE)
    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteOffice(@PathVariable(name = "uuid") String input) {
        try {
            officeService.deleteOffice(input);
            return ResponseEntity.status(200).body("Office " + input + " deleted successfully!");
        } catch (DbObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

}
