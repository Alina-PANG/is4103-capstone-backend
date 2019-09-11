package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country,String> {
}
