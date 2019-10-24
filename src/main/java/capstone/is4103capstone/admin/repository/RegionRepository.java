package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, String> {

    Region getRegionByObjectName(String objectName);

    Region findRegionById(String id);

    @Query(value = "SELECT * From region r WHERE r.is_deleted=false AND r.id=?1", nativeQuery = true)
    Optional<Region> findUndeletedRegionById(String id);
}
