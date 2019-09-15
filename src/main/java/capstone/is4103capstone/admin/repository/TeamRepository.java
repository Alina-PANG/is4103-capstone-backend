package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team,String> {
    public Team findTeamByCode(String teamCode);
}
