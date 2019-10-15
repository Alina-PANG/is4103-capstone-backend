package capstone.is4103capstone.supplychain.service;

import capstone.is4103capstone.supplychain.Repository.ChildContractRepository;
import capstone.is4103capstone.supplychain.Repository.ContractRepository;
import org.apache.poi.poifs.property.Child;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildContractService {
    private static final Logger logger = LoggerFactory.getLogger(VendorService.class);

    @Autowired
    ContractRepository contractRepository;
    @Autowired
    ChildContractRepository childContractRepository;
}
