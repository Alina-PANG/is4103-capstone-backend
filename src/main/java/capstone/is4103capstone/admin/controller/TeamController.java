package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.model.res.TeamRes;
import capstone.is4103capstone.admin.service.TeamService;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
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
    ResponseEntity<Object> retrieveTeamByUser(@RequestParam(name = "username", required = false) String username) {
        if (Authentication.authenticateUser(username))
            return new ResponseEntity<Object>(teamService.getTeamsManagedByUser(username).toString(), HttpStatus.OK);
        else
            return new ResponseEntity<Object>(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG, true), HttpStatus.BAD_REQUEST);
    }

    // GET methods
    @GetMapping
    public ResponseEntity<TeamRes> getAllTeams(@RequestParam(name = "username", required = false) String username) {
        try {
            if (Authentication.authenticateUser(username)) {
                return ResponseEntity.ok().body(new TeamRes(null, false, Optional.of(teamService.getAllTeams())));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new TeamRes("You do not have permissions to perform this action!", true, Optional.empty()));
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new TeamRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @GetMapping("/byCountry/{countryUuid}")
    public ResponseEntity<TeamRes> getAllTeamsByCountryUuid(@RequestParam(name = "username", required = false) String username, @PathVariable(name = "countryUuid") String countryUuid) {
        try {
            if (Authentication.authenticateUser(username)) {
                return ResponseEntity.ok().body(new TeamRes(null, false, Optional.of(teamService.getTeamsByCountry(countryUuid))));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new TeamRes("You do not have permissions to perform this action!", true, Optional.empty()));
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new TeamRes(ex.getMessage(), true, Optional.empty()));
        }
    }
}
