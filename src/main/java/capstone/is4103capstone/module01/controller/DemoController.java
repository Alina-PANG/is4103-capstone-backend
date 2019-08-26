package capstone.is4103capstone.module01.controller;

import capstone.is4103capstone.module01.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    DemoService demoService;

    @GetMapping
    public String defaultDemoPage() {
        return "This is default page for DemoService";
    }

    @GetMapping("/{id}")
    public String getDemoObjectById(@PathVariable("id") long id) {
        return demoService.getTestString(id);
    }




}
