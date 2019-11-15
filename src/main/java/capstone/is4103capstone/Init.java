package capstone.is4103capstone;

import capstone.is4103capstone.admin.AdminInitialization;
import capstone.is4103capstone.finance.FinanceInit;
import capstone.is4103capstone.supplychain.SupplyChainInitialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class Init {
    @Autowired
    AdminInitialization adminInit;
    @Autowired
    FinanceInit finInit;
    @Autowired
    SupplyChainInitialization scmInit;


    @PostConstruct
    public void init(){
        adminInit.init();
        finInit.financeInit();
    }
}
