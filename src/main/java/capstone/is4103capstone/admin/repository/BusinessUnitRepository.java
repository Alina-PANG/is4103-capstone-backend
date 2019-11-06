package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.BusinessUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, String> {

    @Query(value = "SELECT * FROM business_unit b WHERE b.id = ?1 AND b.is_deleted=false", nativeQuery = true)
    Optional<BusinessUnit> findByIdNonDeleted(String id);

    @Query(value = "SELECT * FROM business_unit b WHERE b.is_deleted=false", nativeQuery = true)
    List<BusinessUnit> findAllUndeleted();

    @Query(value = "SELECT * " +
            "FROM business_unit t1 " +
            "JOIN company_function t2 ON t2.id = t1.function_id " +
            "WHERE t2.country_id = ?1", nativeQuery = true)
    List<BusinessUnit> findBusinessUnitsByCountryUuid(String countryUuid);

    @Query(value = "SELECT * FROM business_unit b WHERE b.is_deleted=false AND b.function_id=?1", nativeQuery = true)
    List<BusinessUnit> findOnesUnderCompanyFunction(String functionId);

    @Query(value = "SELECT * FROM business_unit b WHERE b.is_deleted=false AND b.function_id=?1 AND b.id IN " +
            "(SELECT u.id FROM business_unit u, team t WHERE t.business_unit_id=u.id AND " +
            "u.is_deleted=false AND t.is_deleted=false AND " +
            "t.office_id=?2)", nativeQuery = true)
    List<BusinessUnit> findOnesUnderCompanyFunctionAndOffice(String functionId,String officeId);
}
