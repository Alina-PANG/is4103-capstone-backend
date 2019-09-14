package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country,String> {
    Country findCountryByCode(String code);
}
