package capstone.is4103capstone.demoModule.service;

import capstone.is4103capstone.demoModule.model.DemoRole;
import capstone.is4103capstone.demoModule.model.DemoUser;

import java.util.List;

public interface UserService {
    DemoUser findByUsername(String username);
    void save(DemoUser user);

    List<DemoRole> getAllRoles();
}
