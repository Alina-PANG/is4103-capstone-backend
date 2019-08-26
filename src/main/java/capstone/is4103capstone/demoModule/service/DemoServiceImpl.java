package capstone.is4103capstone.demoModule.service;

import capstone.is4103capstone.demoModule.model.DemoObject;
import org.springframework.stereotype.Service;

//Actual Implementation of the service functions
@Service
public class DemoServiceImpl implements DemoService {


    @Override
    public DemoObject getObjectById(Long id) {
        return null;
    }

    @Override
    public void insertObject(DemoObject demoObject) {
    }

    @Override
    public String getTestString(long id) {
        return "This is " + id;
    }
}
