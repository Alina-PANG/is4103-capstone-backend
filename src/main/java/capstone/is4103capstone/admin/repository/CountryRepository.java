package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, String> {

    @Override
    Optional<Country> findById(String s);

    Country findByObjectName(String objectName);

    Country findCountryByCode(String countryCode);

    List<Country> findCountriesByRegionId(String regionId);
}
