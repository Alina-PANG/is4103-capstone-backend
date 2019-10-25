package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.dto.SessionKeyDto;
import capstone.is4103capstone.admin.dto.WebAppPermissionMapDto;
import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.admin.repository.SessionKeyRepo;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.SessionKey;
import capstone.is4103capstone.general.StandardStatusMessages;
import capstone.is4103capstone.util.exception.DbObjectNotFoundException;
import capstone.is4103capstone.util.exception.SessionKeyNotValidException;
import capstone.is4103capstone.util.exception.UserAuthenticationFailedException;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserAuthenticationService {

    // set the global session key expiry time in seconds
    public static int SESSION_KEY_EXPIRY = 3600;
    @Autowired
    EmployeeRepository er;
    @Autowired
    SessionKeyRepo sessionKeyRepo;

    public SessionKeyDto createNewSession(String username, String password) throws Exception {
        if (checkPassword(username, password)) {
            // generate new sessionKey
            SessionKey sk = new SessionKey();
            // set the linked user
            Employee employee = er.findEmployeeByUserName(username);
            if (Objects.isNull(employee))
                throw new Exception("Invalid Username or Password. Please check your input and try again.");
            sk.setLinkedUser(employee);
            // set the expiry date of the cookie
            sk.setLastAuthenticated(new Date());
            // set the session key (UUID)
            sk.setSessionKey(UUID.randomUUID().toString());
            // save to DB
            sessionKeyRepo.save(sk);
            // write the DTO object
            SessionKeyDto sessionKeyDto = new SessionKeyDto();
            sessionKeyDto.setSessionKey(Optional.ofNullable(sk.getSessionKey()));
            sessionKeyDto.setLinkedUserName(Optional.ofNullable(sk.getLinkedUser().getUserName()));
            sessionKeyDto.setLinkedUserSid(Optional.ofNullable(sk.getLinkedUser().getSecurityId()));
            sessionKeyDto.setLinkedUserUuid(Optional.ofNullable(sk.getLinkedUser().getId()));
            sessionKeyDto.setLinkedUserFullName(Optional.ofNullable(sk.getLinkedUser().getFullName()));
            sessionKeyDto.setWebAppPermissions(Optional.ofNullable(WebAppPermissionMapDto.fromEntity(sk.getLinkedUser().getWebAppPermissionMap())));
            return sessionKeyDto;
        } else {
            throw new Exception("Invalid Username or Password. Please check your input and try again.");
        }
    }

    public Employee addNewUser(String userName, String password, String firstName, String lastName) {
        Employee newUser = new Employee();
        // set parent parameters
        newUser.setUserName(userName);

        // set child parameters
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setObjectName(userName);
        newUser.setPassword(password);

        // save the new user
        return er.save(newUser);
    }

    //lookupEmployee maybe null;
    public boolean checkPassword(String userName, String inputPassword) throws DbObjectNotFoundException {
        Employee lookupEmployee = er.findEmployeeByUserName(userName);
        if (Objects.isNull(lookupEmployee)) throw new DbObjectNotFoundException();

        // init the Argon2 hasher
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(lookupEmployee.getPassword(), inputPassword)) {
            return true;
        } else {
            return false;
        }
    }

    public Employee getUserFromSessionKey(String sessionKey) throws SessionKeyNotValidException {
        return sessionKeyRepo.findSessionKeyBySessionKey(sessionKey).getLinkedUser();
    }

    public boolean checkSessionKeyValidity(String sessionKey) throws SessionKeyNotValidException {
        SessionKey currentSessionKey = sessionKeyRepo.findSessionKeyBySessionKey(sessionKey);
        if (Objects.isNull(currentSessionKey))
            throw new SessionKeyNotValidException("Key not found or session expired.");

        // check if the key is still valid
        Date dateNow = new Date();
        if ((dateNow.getTime() - currentSessionKey.getLastAuthenticated().getTime()) / 1000 <= SESSION_KEY_EXPIRY) {
            // update key last used timing
            currentSessionKey.setLastAuthenticated(dateNow);
            return true;
        } else {
            // delete session from db
            sessionKeyRepo.delete(currentSessionKey);
            throw new SessionKeyNotValidException("Key not found or session expired.");
        }
    }

    public boolean changePassword(String userName, String oldPassword, String newPassword) {
        try {
            // first verify the user password
            if (checkPassword(userName, oldPassword)) {
                // change the password
                er.findEmployeeByUserName(userName).setPassword(newPassword);
                // invalidate all session keys!
                invalidateAllSessionKeys(userName);
                return true;
            } else {
                throw new UserAuthenticationFailedException(StandardStatusMessages.INVALID_USERNAME_PASSWORD);
            }
        } catch (DbObjectNotFoundException ex) {
            throw new UserAuthenticationFailedException(StandardStatusMessages.INVALID_USERNAME_PASSWORD);
        }
    }

    @Transactional
    // This function invalidates all session keys for the user
    public void invalidateAllSessionKeys(String userName) {
        sessionKeyRepo.deleteSessionKeysByLinkedUser_UserName(userName);
    }

    public boolean invalidateSessionKey(String sessionKey) {
        // does key exist
        SessionKey skey = sessionKeyRepo.findSessionKeyBySessionKey(sessionKey);
        if (Objects.isNull(skey)) throw new DbObjectNotFoundException(StandardStatusMessages.INVALID_SESSION_KEY);
        sessionKeyRepo.delete(skey);
        return true;
    }

    @Transactional
    public WebAppPermissionMapDto updateWebAppPermissions(String userUuid, WebAppPermissionMapDto webAppPermissionMapDto) throws Exception {
        String userUuidForCurrentOperation = userUuid;
        // check if the dto contains useruuid property. if yes, then use that instead.
        if (webAppPermissionMapDto.getUserUuid().isPresent()) userUuidForCurrentOperation = webAppPermissionMapDto.getUserUuid().get();
        Optional<Employee> employee = er.findById(userUuidForCurrentOperation);

        if (employee.isPresent()) {
            employee.get().setWebAppPermissionMap(webAppPermissionMapDto.thisToEntity());
            return WebAppPermissionMapDto.fromEntity(employee.get().getWebAppPermissionMap());
        } else {
            throw new Exception(StandardStatusMessages.NO_SEARCH_RESULTS_FOUND);
        }
    }

}
