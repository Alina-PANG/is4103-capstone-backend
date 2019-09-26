package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.repository.CostCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CostCenterService {


    @Autowired
    CostCenterRepository costCenterRepository;



}
