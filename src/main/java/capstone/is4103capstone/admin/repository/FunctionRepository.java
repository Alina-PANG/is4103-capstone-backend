package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.CompanyFunction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunctionRepository extends JpaRepository<CompanyFunction, String> {

}
