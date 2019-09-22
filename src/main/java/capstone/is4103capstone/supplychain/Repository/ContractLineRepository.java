package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.supplyChain.ContractLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContractLineRepository extends JpaRepository<ContractLine, String> {

    @Query(value = "SELECT * FROM contract_line cl WHERE cl.id_deleted=false AND cl.merchandise_code=?1 AND cl.contract=?2",nativeQuery = true)
    Optional<ContractLine> findContractLineByMerchandiseCodeAndContractId(String merchandiseCode, String contractId);
}
