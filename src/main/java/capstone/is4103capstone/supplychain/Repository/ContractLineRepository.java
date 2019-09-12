package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.supplyChain.ContractLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractLineRepository extends JpaRepository<ContractLine, String> {
}
