package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.Merchandise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MerchandiseRepository extends JpaRepository<Merchandise, String> {
    public Merchandise findMerchandiseByCode(String merchandiseCode);

    @Query(value = "SELECT * FROM merchandise m WHERE m.is_deleted=false AND m.budget_sub2_id=?1",nativeQuery = true)
    public List<Merchandise> findMerchandiseByBudgetSub2Id(String sub2Id);

    @Query(value = "SELECT * FROM merchandise m WHERE m.is_deleted=false AND m.vendor_id=?1", nativeQuery = true)
    public List<Merchandise> findMerchandiseByVendorId(String vendorId);
}
