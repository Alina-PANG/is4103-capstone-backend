package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.admin.repository.SessionKeyRepo;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.SessionKey;
import capstone.is4103capstone.util.exception.DbObjectNotFoundException;
import capstone.is4103capstone.util.exception.SessionKeyNotValidException;
import capstone.is4103capstone.util.exception.UserAuthenticationFailedException;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class UserAuthenticationService {

    // set the global session key expiry time in seconds
    public static int SESSION_KEY_EXPIRY = 3600;
    @Autowired
    EmployeeRepository er;
    @Autowired
    SessionKeyRepo sk;

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
        return sk.findSessionKeyBySessionKey(sessionKey).getLinkedUser();
    }

    public boolean checkSessionKeyValidity(String sessionKey) throws SessionKeyNotValidException {
        SessionKey currentSessionKey = sk.findSessionKeyBySessionKey(sessionKey);
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
            sk.delete(currentSessionKey);
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
                throw new UserAuthenticationFailedException("Invalid Username or Password.");
            }
        } catch (DbObjectNotFoundException ex) {
            throw new UserAuthenticationFailedException("Invalid Username or Password.");
        }
    }

    // This function invalidates all session keys for the user
    public void invalidateAllSessionKeys(String userName) {
        sk.deleteSessionKeysByLinkedUser_UserName(userName);
    }

    public boolean invalidateSessionKey(String sessionKey) {
        // does key exist
        SessionKey skey = sk.findSessionKeyBySessionKey(sessionKey);
        if (Objects.isNull(skey)) throw new DbObjectNotFoundException("Session key not found!");
        sk.delete(skey);
        return true;
    }

}
