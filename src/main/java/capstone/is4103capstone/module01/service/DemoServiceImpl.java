package capstone.is4103capstone.module01.service;

import capstone.is4103capstone.module01.domain.DemoObject;
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
