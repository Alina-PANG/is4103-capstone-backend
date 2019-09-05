package capstone.is4103capstone._demoModule.controller;

import capstone.is4103capstone._demoModule.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    DemoService demoService;



    @GetMapping("/{id}")
    public String getDemoObjectById(@PathVariable("id") long id) {
        return demoService.getTestString(id);
    }




}
