package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OfficeRepository extends JpaRepository<Office,String> {

    @Query(value = "SELECT * FROM Office o WHERE o.objectName = ?1 AND o.country.objectName = ?2 AND o.isDeleted = false", nativeQuery = true)
    Optional<Office> findByOfficeNameAndCountryName(String officeName, String countryName);
}
