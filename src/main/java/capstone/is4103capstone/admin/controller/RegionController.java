package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.controller.model.res.RegionRes;
import capstone.is4103capstone.admin.dto.RegionDto;
import capstone.is4103capstone.admin.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/api/ce/region")
public class RegionController {

    @Autowired
    RegionService rs;

    @PostMapping
    public ResponseEntity<RegionRes> createRegion(@RequestBody RegionDto input) {
        try {
            return ResponseEntity.ok().body(new RegionRes(null, false, (Optional.of(Arrays.asList(rs.createRegion(input))))));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new RegionRes(ex.getMessage(), true, Optional.empty()));
        }
    }


    @GetMapping
    public ResponseEntity<RegionRes> getAllRegions() {
        try {
            return ResponseEntity.ok().body(new RegionRes(null, false, Optional.of(rs.getAllRegions())));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new RegionRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<RegionRes> getSingleRegion(@PathVariable(name = "uuid", required = true) String input) {
        try {
            return ResponseEntity.ok().body(new RegionRes(null, false, Optional.of(Arrays.asList(rs.getRegionByUuid(input)))));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new RegionRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @PutMapping
    public ResponseEntity<RegionRes> updateRegion(@RequestBody RegionDto input) {
        try {
            return ResponseEntity.ok().body(new RegionRes(null, false, (Optional.of(Arrays.asList(rs.updateRegion(input))))));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new RegionRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<RegionRes> deleteRegion(@PathVariable(name = "uuid") String input) {
        try {
            rs.deleteRegion(input);
            return ResponseEntity.ok().body(new RegionRes("Country with UUID " + input + " has been successfully deleted!", false, Optional.empty()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new RegionRes(ex.getMessage(), true, Optional.empty()));
        }
    }
}
