package capstone.is4103capstone.demoModule.service;

import capstone.is4103capstone.demoModule.model.DemoObject;

//Some CRUD functions
public interface DemoService {
    DemoObject getObjectById(Long id);

    void insertObject(DemoObject demoObject);

    String getTestString(long id);
}
