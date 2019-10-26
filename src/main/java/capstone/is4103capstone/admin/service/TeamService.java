package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.dto.TeamDto;
import capstone.is4103capstone.admin.repository.TeamRepository;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.general.model.GeneralEntityModel;
import capstone.is4103capstone.util.exception.TeamNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            throw new TeamNotFoundException("Team with ID " + teamId + " does not exist!");
        }

        return optionalTeam.get();
    }

    public JSONObject getTeamsManagedByUser(String username) {
        JSONObject res = new JSONObject();
        //now return every team:
        try {
            List<Team> teams = findTeamsByUser(username);
            JSONArray teamList = new JSONArray();
            for (Team t : teams) {
                teamList.put(new JSONObject(new GeneralEntityModel(t)));
            }

            res.put("teamList", teamList);
            res.put("message", "Successfully retrieved teams under user [" + username + "]");
            res.put("hasError", false);
        } catch (Exception e) {
            res.put("message", e.getMessage());
            res.put("hasError", true);
        }
        return res;
    }

    //now retrieve everthing
    private List<Team> findTeamsByUser(String username) {
        List<Team> teams = teamRepository.findAll();
        return teams;
    }

    // get methods
    public List<TeamDto> getAllTeams() throws Exception {
        List<Team> teams = teamRepository.findAll();
        if (teams.isEmpty()) throw new Exception("There are no teams in the database!");
        List<TeamDto> teamDtos = entityToDto(teams, true);
        if (teamDtos.isEmpty()) throw new Exception("There are no active (non-deleted) teams in the database!");
        return teamDtos;
    }

    public List<TeamDto> getTeamsByCountry(String countryUuid) throws Exception {
        List<Team> teams = teamRepository.findTeamsByCountryId(countryUuid);
        if (teams.isEmpty()) throw new Exception("No teams found for country with UUID " + countryUuid);
        List<TeamDto> teamDtos = entityToDto(teams, true);
        if (teamDtos.isEmpty())
            throw new Exception("No active (non-deleted) teams found for country with UUID " + countryUuid);
        return teamDtos;
    }

    public Team getTeamEntityByUuid(String teamUuid) throws Exception {
        Optional<Team> team = teamRepository.findById(teamUuid);
        if (team.isPresent()) {
            return team.get();
        } else {
            throw new Exception("No team found with UUID " + teamUuid);
        }
    }

    public Team getTeamEntityByTeamLeadUuid(String teamLeadUuid) throws Exception {
        Optional<Team> team = teamRepository.findByTeamLeadUuid(teamLeadUuid);
        if (team.isPresent()) {
            return team.get();
        } else {
            throw new Exception("No team found under the management by employee " + teamLeadUuid);
        }
    }

    // DTO to Entity Conversion Methods
    public TeamDto entityToDto(Team input) {
        TeamDto teamDto = new TeamDto();
        teamDto.setId(Optional.of(input.getId()));
        teamDto.setCode(Optional.of(input.getCode()));
        teamDto.setObjectName(Optional.of(input.getObjectName()));
        teamDto.setCompanyFunction(Optional.of(input.getBusinessUnit().getFunction().getId()));
        teamDto.setCountry(Optional.of(input.getBusinessUnit().getFunction().getCountry().getId()));
        teamDto.setBusinessUnitUuid(Optional.of(input.getBusinessUnit().getId()));
        return teamDto;
    }

    public List<TeamDto> entityToDto(List<Team> input, boolean suppressDeleted) {
        List<TeamDto> teamDtos = new ArrayList<>();
        for (Team team : input) {
            if (suppressDeleted) {
                if (!team.getDeleted()) teamDtos.add(entityToDto(team));
            } else {
                teamDtos.add(entityToDto(team));
            }
        }
        return teamDtos;
    }

    public Team dtoToEntity(TeamDto input) {
        Team team = new Team();
        input.getId().ifPresent(team::setId);
        input.getCode().ifPresent(team::setCode);
        input.getObjectName().ifPresent(team::setObjectName);
        return team;
    }

    public List<Team> dtoToEntity(List<TeamDto> input) {
        List<Team> teams = new ArrayList<>();
        for (TeamDto teamDto : input) {
            teams.add(dtoToEntity(teamDto));
        }
        return teams;
    }


}
