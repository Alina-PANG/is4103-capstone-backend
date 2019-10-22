package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.supplyChain.Outsourcing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OutsourcingRepository extends JpaRepository<Outsourcing, String> {
    @Query(value = "SELECT * From outsourcing e WHERE e.is_deleted=false AND e.id=?1", nativeQuery = true)
    Optional<Outsourcing> findUndeletedOutsourcingById(String id);
}
