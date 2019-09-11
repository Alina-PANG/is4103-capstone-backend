package capstone.is4103capstone._demoModule.service;

import capstone.is4103capstone._demoModule.repository.DemoEntityRepository;
import capstone.is4103capstone.entities.DemoEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//Some CRUD functions
public class DemoService {

    @Autowired
    DemoEntityRepository demoEntityRepository;

    public List<DemoEntity> findAllDemoEntities(){
        return demoEntityRepository.findAll();
    }
}
