package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.admin.repository.SessionKeyRepo;
import capstone.is4103capstone.admin.service.UserService;
import capstone.is4103capstone.entities.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/ua")
public class UserAuthenticationController {

    @Autowired
    EmployeeRepository er;

    @Autowired
    SessionKeyRepo skr;

    @Autowired
    UserService us;

    @GetMapping("/login")
    public SessionKey createSessionKey(@RequestParam(name = "username") String userName, @RequestParam(name = "password") String password) {
        if (us.checkPassword(userName, password)) {
            // generate new sessionKey
            SessionKey sk = new SessionKey();
            // set the linked user
            sk.setLinkedUser(er.findEmployeeByUserName(userName));
            // set the expiry date of the cookie
            sk.setLastAuthenticated(new Date());
            // set the session key (UUID)
            sk.setSessionKey(UUID.randomUUID().toString());
            // save to DB
            skr.save(sk);
            return sk;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Username or Password");
        }
    }
}
