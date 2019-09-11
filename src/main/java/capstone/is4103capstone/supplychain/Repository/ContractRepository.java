package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, String> {
}
