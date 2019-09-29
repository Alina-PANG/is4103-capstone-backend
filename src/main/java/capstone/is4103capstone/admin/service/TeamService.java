package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.repository.TeamRepository;
import capstone.is4103capstone.entities.CompanyFunction;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.util.exception.CompanyFunctionNotFoundException;
import capstone.is4103capstone.util.exception.TeamNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.general.model.GeneralEntityModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public Team retrieveTeamById(String teamId) throws TeamNotFoundException {
        if (teamId == null || teamId.trim().length() == 0) {
            throw new TeamNotFoundException("Invalid company ID ID given!");
        }
        teamId = teamId.trim();
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (!optionalTeam.isPresent()) {
            throw new TeamNotFoundException("Employee with ID " + teamId + " does not exist!");
        }

        return optionalTeam.get();
    TeamRepository teamRepository;

    public JSONObject getTeamsManagedByUser(String username){
        JSONObject res = new JSONObject();
        //now return every team:
        try{
            List<Team> teams = findTeamsByUser(username);
            JSONArray teamList = new JSONArray();
            for (Team t: teams){
                teamList.put(new JSONObject(new GeneralEntityModel(t)));
            }

            res.put("teamList",teamList);
            res.put("message","Successfully retrieved teams under user ["+username+"]");
            res.put("hasError",false);
        }catch (Exception e){
            res.put("message",e.getMessage());
            res.put("hasError",true);
        }
        return res;
    }

    //now retrieve everthing
    private List<Team> findTeamsByUser(String username){
        List<Team> teams = teamRepository.findAll();
        return teams;
    }
}
