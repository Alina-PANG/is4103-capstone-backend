package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.model.res.GetCostCenterListRes;
import capstone.is4103capstone.admin.repository.TeamRepository;
import capstone.is4103capstone.admin.service.TeamService;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.DefaultData;
import capstone.is4103capstone.general.model.GeneralRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team")
@CrossOrigin(origins = "http://localhost:3000")
public class TeamController {

    @Autowired
    TeamService teamService;

    @GetMapping("/view-my")
    public @ResponseBody ResponseEntity<Object> retrieveTeamByUser(@RequestParam(name = "username") String username){
        if(Authentication.authenticateUser(username))
            return new ResponseEntity<Object>(teamService.getTeamsManagedByUser(username).toString(), HttpStatus.OK);
        else
            return new ResponseEntity<Object>(new GeneralRes(DefaultData.AUTHENTICATION_ERROR_MSG,true), HttpStatus.BAD_REQUEST);

    }
}
