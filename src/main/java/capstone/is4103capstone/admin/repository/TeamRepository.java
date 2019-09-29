package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.entities.seat.SeatMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,String> {
    Team findTeamByCode(String teamCode);

    @Override
    @Query(value = "SELECT * FROM team t WHERE t.id = ?1 AND t.is_deleted=false", nativeQuery = true)
    Optional<Team> findById(String id);
}
