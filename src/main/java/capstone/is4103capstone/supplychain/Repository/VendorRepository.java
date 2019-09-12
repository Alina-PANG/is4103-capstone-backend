package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.supplyChain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, String> {
}
