package capstone.is4103capstone.module01.service;

import capstone.is4103capstone.module01.model.DemoObject;

//Some CRUD functions
public interface DemoService {
    DemoObject getObjectById(Long id);

    void insertObject(DemoObject demoObject);

    String getTestString(long id);
}
