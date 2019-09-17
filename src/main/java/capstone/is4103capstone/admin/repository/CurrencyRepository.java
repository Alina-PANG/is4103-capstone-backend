package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency,String> {

}
