package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.admin.repository.SessionKeyRepo;
import capstone.is4103capstone.admin.service.UserService;
import capstone.is4103capstone.entities.SessionKey;
import capstone.is4103capstone.util.exception.DbObjectNotFoundException;
import capstone.is4103capstone.util.exception.UserAuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/changePassword")
    @Transactional
    public boolean changePassword(@RequestParam(name = "username") String userName, @RequestParam(name = "oldpassword") String oldPassword, @RequestParam(name = "newpassword") String
            newPassword) {
        try {
            us.changePassword(userName, oldPassword, newPassword);
            return true;
        } catch (UserAuthenticationFailedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }
    }

    @GetMapping("/login")
    public SessionKey createSessionKey(@RequestParam(name = "username") String
                                               userName, @RequestParam(name = "password") String password) {
        try {
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
        } catch (DbObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Username or Password");
        }
    }

    @GetMapping("/invalidateAllSessions")
    public ResponseEntity invalidateAllSessions(@RequestAttribute(name = "username") String userName) {
        us.invalidateAllSessionKeys(userName);
        return new ResponseEntity(HttpStatus.OK);
    }
}
