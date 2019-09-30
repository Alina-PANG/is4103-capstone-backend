package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, String> {
    Team findTeamByCode(String teamCode);
}
