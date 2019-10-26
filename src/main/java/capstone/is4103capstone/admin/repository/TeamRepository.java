package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, String> {
    Team findTeamByCode(String teamCode);

    @Override
    @Query(value = "SELECT * FROM team t WHERE t.id = ?1 AND t.is_deleted=false", nativeQuery = true)
    Optional<Team> findById(String id);

    @Query(value = "SELECT * FROM team t1 " +
            "JOIN business_unit t2 ON t1.business_unit_id = t2.id " +
            "JOIN company_function t3 ON t2.function_id = t3.id " +
            "WHERE t3.country_id = ?1", nativeQuery = true)
    List<Team> findTeamsByCountryId(String countryUuid);

    @Query(value = "SELECT * FROM team t WHERE t.team_lead_id = ?1 AND t.is_deleted=false", nativeQuery = true)
    Optional<Team> findByTeamLeadUuid(String teamLeadUuid);
}
