package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OfficeRepository extends JpaRepository<Office, String> {
    @Override
    <S extends Office> S save(S s);

    @Query(value = "SELECT * FROM Office o, Country c WHERE o.country_id = c.id AND o.object_name = ?1 AND c.object_name = ?2 AND o.is_deleted = false AND c.is_deleted = false", nativeQuery = true)
    Optional<Office> findByOfficeNameAndCountryName(String officeName, String countryName);

    @Query(value = "SELECT * FROM Office o WHERE o.object_name = ?1 AND o.is_deleted = false", nativeQuery = true)
    Optional<Office> findByName(String officeName);

    @Query(value = "SELECT * FROM office o WHERE o.id = ?1 AND o.is_deleted=false", nativeQuery = true)
    Optional<Office> findUndeletedOfficeById(String id);

    @Query(value = "SELECT * FROM office o WHERE o.is_deleted=false", nativeQuery = true)
    List<Office> findAllUndeleted();

}
