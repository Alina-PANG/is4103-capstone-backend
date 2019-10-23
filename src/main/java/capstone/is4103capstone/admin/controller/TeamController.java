package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.model.res.TeamRes;
import capstone.is4103capstone.admin.service.TeamService;
import capstone.is4103capstone.general.AuthenticationTools;
import capstone.is4103capstone.general.service.WriteAuditTrail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/team")
@CrossOrigin
public class TeamController {

    @Autowired
    TeamService teamService;

    @GetMapping("/view-my")
    public @ResponseBody
    ResponseEntity<Object> retrieveTeamByUser() {
        WriteAuditTrail.createBasicAuditRecord();
        return new ResponseEntity<Object>(teamService.getTeamsManagedByUser(AuthenticationTools.getCurrentUser().getUserName()).toString(), HttpStatus.OK);
    }

    // GET methods
    @GetMapping
    public ResponseEntity<TeamRes> getAllTeams() {
        try {
            WriteAuditTrail.createBasicAuditRecord();
            return ResponseEntity.ok().body(new TeamRes(null, false, Optional.of(teamService.getAllTeams())));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new TeamRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @GetMapping("/byCountry/{countryUuid}")
    public ResponseEntity<TeamRes> getAllTeamsByCountryUuid(@PathVariable(name = "countryUuid") String countryUuid) {
        try {
            WriteAuditTrail.createBasicAuditRecord();
            return ResponseEntity.ok().body(new TeamRes(null, false, Optional.of(teamService.getTeamsByCountry(countryUuid))));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new TeamRes(ex.getMessage(), true, Optional.empty()));
        }
    }
}