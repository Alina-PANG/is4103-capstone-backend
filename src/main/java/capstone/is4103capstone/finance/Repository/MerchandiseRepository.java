package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.Merchandise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MerchandiseRepository extends JpaRepository<Merchandise, String> {
    public List<Merchandise> findMerchandiseByCode(String merchandiseCode);
}
