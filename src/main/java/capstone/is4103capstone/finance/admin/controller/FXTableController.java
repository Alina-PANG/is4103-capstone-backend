package capstone.is4103capstone.finance.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/FXTable")
public class FXTableController {
    private static final Logger logger = LoggerFactory.getLogger(FXTableController.class);
}
