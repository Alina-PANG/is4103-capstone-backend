package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.supplyChain.ChildContract;
import capstone.is4103capstone.entities.supplyChain.Contract;
import org.apache.poi.poifs.property.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChildContractRepository extends JpaRepository<ChildContract, String> {
    @Query(value = "SELECT * FROM contract_line cl WHERE cl.is_deleted=false AND cl.merchandise_code=?1 AND cl.contract=?2",nativeQuery = true)
    Optional<ChildContract> findChildContractByMerchandiseCodeAndContractId(String merchandiseCode, String contractId);

    @Query(value = "SELECT * FROM child_contract child WHERE child.is_deleted=false AND child.masterContract_id=?1",nativeQuery = true)
    List<ChildContract> findChildContractsByMasterContractId(String masterContractId);
}
