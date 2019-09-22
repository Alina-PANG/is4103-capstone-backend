package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.supplyChain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContractRepository extends JpaRepository<Contract, String> {
    //need to check deleted = false in service.
    public Contract findContractByCode(String code);
}
