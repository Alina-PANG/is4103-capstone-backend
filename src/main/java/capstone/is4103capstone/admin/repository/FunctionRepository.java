package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.CompanyFunction;
import capstone.is4103capstone.entities.seat.SeatMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FunctionRepository extends JpaRepository<CompanyFunction,String> {

    @Override
    @Query(value = "SELECT * FROM company_function c WHERE c.id = ?1 AND c.is_deleted=false", nativeQuery = true)
    Optional<CompanyFunction> findById(String id);

}
