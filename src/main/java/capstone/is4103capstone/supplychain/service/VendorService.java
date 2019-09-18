package capstone.is4103capstone.supplychain.service;

import capstone.is4103capstone.supplychain.Repository.VendorRepository;
import capstone.is4103capstone.supplychain.model.req.CreateVendorReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorService {
    @Autowired
    private VendorRepository vendorRepository;

    public VendorService() {
    }

    public String createNewVendor(CreateVendorReq createVendorReq){

        return "test";
    }
}
