package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {

}
