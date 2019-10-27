package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.dto.WebAppPermissionMapDto;
import capstone.is4103capstone.admin.model.res.GenericObjectRes;
import capstone.is4103capstone.admin.service.UserAuthenticationService;
import capstone.is4103capstone.general.AuthenticationTools;
import capstone.is4103capstone.general.StandardStatusMessages;
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

    @PostMapping("/updateWebAppPermissions")
    public ResponseEntity<GenericObjectRes> updateWebAppPermissions(@RequestBody WebAppPermissionMapDto input) {
        try {
            return ResponseEntity.ok().body(new GenericObjectRes(StandardStatusMessages.OPERATION_COMPLETED_SUCCESSFULLY, false,
                    Optional.of(Collections.singletonList(us.updateWebAppPermissions(AuthenticationTools.getCurrentUser().getId(), input)))));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new GenericObjectRes(ex.getMessage(), true, Optional.empty()));
        }
    }

}
