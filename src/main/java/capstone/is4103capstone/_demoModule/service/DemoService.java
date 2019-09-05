package capstone.is4103capstone._demoModule.service;

import capstone.is4103capstone._demoModule.entity.DemoObject;

//Some CRUD functions
public interface DemoService {
    DemoObject getObjectById(Long id);

    void insertObject(DemoObject demoObject);

    String getTestString(long id);
}
