package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.CompanyFunction;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.seat.SeatMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FunctionRepository extends JpaRepository<CompanyFunction, String> {

    @Override
    @Query(value = "SELECT * FROM company_function c WHERE c.id = ?1 AND c.is_deleted=false", nativeQuery = true)
    Optional<CompanyFunction> findById(String id);

    @Query(value = "SELECT * FROM company_function c WHERE c.is_deleted=false", nativeQuery = true)
    List<CompanyFunction> findAllUndeleted();

    @Query(value = "SELECT * FROM company_function c WHERE c.code = ?1 AND c.is_deleted=false", nativeQuery = true)
    Optional<CompanyFunction> findByCode(String code);
}
