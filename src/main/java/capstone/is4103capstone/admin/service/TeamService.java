package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.repository.TeamRepository;
import capstone.is4103capstone.entities.CompanyFunction;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.util.exception.CompanyFunctionNotFoundException;
import capstone.is4103capstone.util.exception.TeamNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            throw new TeamNotFoundException("Employee with ID " + teamId + " does not exist!");
        }

        return optionalTeam.get();
    }
}
