package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.model.res.GenericObjectRes;
import capstone.is4103capstone.admin.service.UserAuthenticationService;
import capstone.is4103capstone.util.exception.DbObjectNotFoundException;
import capstone.is4103capstone.util.exception.UserAuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/ua")
public class UserAuthenticationController {

    @Autowired
    UserAuthenticationService us;

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
    public ResponseEntity<GenericObjectRes> createSessionKey(@RequestParam(name = "username") String
                                                                     userName, @RequestParam(name = "password") String password) {
        try {
            return ResponseEntity.ok().body(new GenericObjectRes(null, false, Optional.of(Collections.singletonList(us.createNewSession(userName, password)))));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new GenericObjectRes(ex.getMessage(), true, Optional.empty()));
        }
    }

    @GetMapping("/invalidateAllSessions")
    public ResponseEntity invalidateAllSessions(@RequestParam(name = "username") String userName) {
        us.invalidateAllSessionKeys(userName);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity logoutSession(@RequestParam(name = "session") String sessionKey) {
        try {
            us.invalidateSessionKey(sessionKey);
            return new ResponseEntity(HttpStatus.OK);
        } catch (DbObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Session key " + sessionKey + " is not valid.");
        }
    }
}
