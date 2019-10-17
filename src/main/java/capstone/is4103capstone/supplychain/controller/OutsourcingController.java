package capstone.is4103capstone.supplychain.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/outsourcing")
@CrossOrigin(origins = "http://localhost:3000")
public class OutsourcingController {
    private static final Logger logger = LoggerFactory.getLogger(OutsourcingController.class);
}
