package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.CostCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CostCenterRepository extends JpaRepository<CostCenter, String> {

//    @Override
//    Optional<CostCenter> findById(String s);

    //    @Query(value = "SELECT * FROM costCenter s WHERE s.code=?1", nativeQuery = true)
    CostCenter findCostCenterByCode(String code);

    @Query(value = "SELECT * FROM cost_center cc WHERE cc.is_deleted=false",nativeQuery = true)
    List<CostCenter> findAllNotDeleted();

    @Query(value = "SELECT * FROM cost_center cc WHERE cc.is_deleted=false AND cc.team_id=?1",nativeQuery = true)
    List<CostCenter> findCostCentersByTeamId(String teamId);
}
