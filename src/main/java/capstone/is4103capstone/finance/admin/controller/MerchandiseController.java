package capstone.is4103capstone.finance.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fin-item")
@CrossOrigin(origins = "http://localhost:3000")
public class MerchandiseController {
    private static final Logger logger = LoggerFactory.getLogger(MerchandiseController.class);

}
