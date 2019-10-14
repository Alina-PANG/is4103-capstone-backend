package capstone.is4103capstone.general.repository;

import capstone.is4103capstone.entities.finance.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository<T, String>   extends JpaRepository<T, String> {
}
