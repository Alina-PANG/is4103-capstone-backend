package capstone.is4103capstone.supplychain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, String> {
    public List<Vendor> findVendorByCode(String vendorCode);
}
