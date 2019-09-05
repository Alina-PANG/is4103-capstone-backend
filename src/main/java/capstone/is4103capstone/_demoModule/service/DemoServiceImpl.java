package capstone.is4103capstone._demoModule.service;

import capstone.is4103capstone._demoModule.entity.DemoObject;
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
