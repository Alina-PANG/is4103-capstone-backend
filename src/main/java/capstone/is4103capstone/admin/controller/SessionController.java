package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.model.res.GenericObjectRes;
import capstone.is4103capstone.admin.service.UserAuthenticationService;
import capstone.is4103capstone.general.StandardStatusMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/session")
public class SessionController {

    @Autowired
    UserAuthenticationService us;

    @GetMapping("/login")
    public ResponseEntity<GenericObjectRes> createSessionKey(@RequestParam(name = "username") String
                                                                     userName, @RequestParam(name = "password") String password) {
        try {
            return ResponseEntity.ok().body(new GenericObjectRes(StandardStatusMessages.OPERATION_COMPLETED_SUCCESSFULLY, false, Optional.of(Collections.singletonList(us.createNewSession(userName, password)))));
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
    public ResponseEntity logoutSession(@RequestHeader("X-Authorization") String sessionKey) {
        try {
            us.invalidateSessionKey(sessionKey);
            return ResponseEntity.ok().body(new GenericObjectRes(StandardStatusMessages.OPERATION_COMPLETED_SUCCESSFULLY, false, Optional.empty()));
        } catch (Exception ex) {
            return ResponseEntity.ok().body(new GenericObjectRes(ex.getMessage(), true, Optional.empty()));
        }
    }

}
