package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.CompanyFunction;
import capstone.is4103capstone.entities.finance.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, String> {
    public Service findServiceByCode(String serviceCode);

    @Query(value = "SELECT * FROM service m WHERE m.is_deleted=false AND m.budget_sub2_id=?1",nativeQuery = true)
    public List<Service> findServiceByBudgetSub2Id(String sub2Id);

    @Query(value = "SELECT * FROM service m WHERE m.is_deleted=false AND m.vendor_id=?1", nativeQuery = true)
    public List<Service> findServiceByVendorId(String vendorId);

    @Query(value = "SELECT COUNT(*) FROM service m WHERE m.is_deleted=false AND m.budget_sub2_id=?1 AND m.vendor_id=?2 AND UPPER(m.object_name)=UPPER(?3)",nativeQuery = true)
    public Integer countServicesByVendor(String sub2Id, String vendorId, String name);

    @Query(value = "SELECT * From service s WHERE s.is_deleted=false AND s.id=?1", nativeQuery = true)
    Optional<Service> findUndeletedServiceById(String id);

}
