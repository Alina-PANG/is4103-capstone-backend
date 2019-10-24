package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.supplyChain.ChildContract;
import capstone.is4103capstone.supplychain.model.ContractAndChildAggre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChildContractRepository extends JpaRepository<ChildContract, String> {
    @Query(value = "SELECT * FROM contract_line cl WHERE cl.is_deleted=false AND cl.merchandise_code=?1 AND cl.contract=?2",nativeQuery = true)
    Optional<ChildContract> findChildContractByMerchandiseCodeAndContractId(String merchandiseCode, String contractId);

    @Query(value = "SELECT * FROM child_contract child WHERE child.is_deleted=false AND child.master_contract=?1",nativeQuery = true)
    List<ChildContract> findChildContractsByMasterContractId(String masterContractId);

    @Query(value = "SELECT * FROM child_contract child WHERE child.is_deleted=false AND child.service_code=?1",nativeQuery = true)
    List<ChildContract> findChildContractsByServiceCode(String serviceCode);

    @Query(value = "select ch.id as child_contract_id, c.id as contract_id, ch.code as child_contract_code, c.code as contract_code, " +
            "ch.contract_value as child_contract_value, c.total_contract_value as total_contract_value, " +
            "c.end_date as contract_end_date, c.start_date as contract_start_date " +
            "from child_contract ch join contract c where c.id=ch.master_contract and c.is_deleted=0 and ch.is_deleted=0 " +
            "and ch.service_code=?1 and c.vendor_id=?2",nativeQuery = true)
    List<ContractAndChildAggre> getContractInformationByServiceAndVendor(String serviceCode, String vendorId);



}
