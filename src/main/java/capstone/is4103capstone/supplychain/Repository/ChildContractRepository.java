package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.supplyChain.ChildContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChildContractRepository extends JpaRepository<ChildContract, String> {
    @Query(value = "SELECT * FROM contract_line cl WHERE cl.is_deleted=false AND cl.merchandise_code=?1 AND cl.contract=?2",nativeQuery = true)
    Optional<ChildContract> findContractLineByMerchandiseCodeAndContractId(String merchandiseCode, String contractId);
}
