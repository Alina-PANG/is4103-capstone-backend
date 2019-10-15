package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.BusinessUnit;
import capstone.is4103capstone.entities.CompanyFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, String> {

    @Override
    @Query(value = "SELECT * FROM business_unit b WHERE b.id = ?1 AND b.is_deleted=false", nativeQuery = true)
    Optional<BusinessUnit> findById(String id);
}
