package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.dto.RegionDto;
import capstone.is4103capstone.admin.service.RegionService;
import capstone.is4103capstone.util.exception.DbObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/ce/region")
public class RegionController {

    @Autowired
    RegionService rs;

    @PostMapping
    public RegionDto createRegion(@RequestBody RegionDto input) {
        return rs.createRegion(input);
    }

    @GetMapping("/{uuid}")
    public RegionDto getRegion(@PathVariable(name = "uuid", required = true) String input) {
        try {
            return rs.getRegionByUuid(input);
        } catch (DbObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Region with UUID " + input + " was not found.");
        }
    }

    @GetMapping
    public List<RegionDto> getAllRegions() {
        return rs.getAllRegions();
    }

    @PutMapping
    public RegionDto updateRegion(@RequestBody RegionDto input) {
        return rs.updateRegion(input);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteRegion(@PathVariable(name = "uuid") String uuid) {
        rs.deleteRegion(uuid);
        return ResponseEntity.status(HttpStatus.OK).body("Region with UUID " + uuid + " was successfully deleted.");
    }
}
