package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.Country;
import capstone.is4103capstone.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, String> {


    Country findByObjectName(String objectName);

    Country findCountryByCode(String countryCode);

    List<Country> findCountriesByRegionId(String regionId);

    Country findCountryById(String uuid);

    @Query(value = "SELECT * From country c WHERE c.is_deleted=false AND c.id=?1", nativeQuery = true)
    Optional<Country> findUndeletedCountryById(String id);
}
