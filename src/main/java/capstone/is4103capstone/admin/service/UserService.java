package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.entities.Employee;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    EmployeeRepository er;

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
    public boolean checkPassword(String userName, String inputPassword) {
        Employee lookupEmployee = er.findEmployeeByUserName(userName);

        // init the Argon2 hasher
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(lookupEmployee.getPassword(), inputPassword)) {
            return true;
        } else {
            return false;
        }
    }

}
