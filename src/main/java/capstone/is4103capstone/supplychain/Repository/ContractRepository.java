package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.supplyChain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, String> {
    //need to check deleted = false in service.
    public Contract findContractByCode(String code);

    @Query(value = "SELECT * FROM contract cont WHERE cont.is_deleted=false AND cont.vendor_id=?1",nativeQuery = true)
    List<Contract> findContractsByVendorId(String vendorId);

    @Query(value = "SELECT * FROM contract cont WHERE cont.is_deleted=false AND cont.team_id=?1",nativeQuery = true)
    List<Contract> findContractsByTeamId(String teamId);

    @Query(value = "SELECT COUNT(*) FROM contract cont WHERE cont.is_deleted=false AND cont.contract_status=?1",nativeQuery = true)
    BigDecimal findNumberOfContractByStatus(String status);

    @Query(value = "SELECT * FROM contract cont WHERE cont.is_deleted=false AND cont.contract_status=?1",nativeQuery = true)
    List<Contract> findContractsByStatus(String status);

    @Query(value = "SELECT * FROM contract cont WHERE cont.is_deleted=false AND (cont.contract_status = '3' OR cont.contract_status = '4') AND cont.end_date <= ?1",nativeQuery = true)
    List<Contract> findExpireContracts(Date date);

    @Query(value = "SELECT * FROM contract cont WHERE cont.is_deleted=false AND (cont.contract_status = '3' OR cont.contract_status = '4' OR cont.contract_status = '5') AND cont.renewal_start_date <= ?1",nativeQuery = true)
    List<Contract> findRenewContracts(Date date);
}
