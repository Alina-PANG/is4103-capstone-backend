package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.supplyChain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, String> {
    //need to check deleted = false in service.
    public Contract findContractByCode(String code);

    @Query(value = "SELECT * FROM contract cont WHERE cont.is_deleted=false AND cont.vendor_id=?1",nativeQuery = true)
    List<Contract> findContractsByVendorId(String vendorId);
}
