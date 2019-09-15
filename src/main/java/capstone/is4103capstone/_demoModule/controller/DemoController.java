package capstone.is4103capstone._demoModule.controller;

import capstone.is4103capstone._demoModule.repository.DemoEntityRepository;
import capstone.is4103capstone._demoModule.service.DemoService;
import capstone.is4103capstone.configuration.DemoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    DemoEntityRepository demoEntityRepository;

    @GetMapping("/entity-template")
    public void tryEntityTemplate(){
        DemoEntity demoEntity = new DemoEntity();
        demoEntity.setDescription("This is a description");
        demoEntity.setCreatedBy("yingshi2502");
        demoEntity.setLastModifiedBy("yingshi2502");
        demoEntityRepository.save(demoEntity);
    }

    @GetMapping("/retrieve-demo")
    public void tryRetrieveUUID(){
        List<DemoEntity> entities = demoEntityRepository.findAll();
        System.out.println(entities.size());
        System.out.println(entities.get(0).getDescription());
        System.out.println(entities.get(0).getId());
        System.out.println(entities.get(0).getId().toString());

    }
}
