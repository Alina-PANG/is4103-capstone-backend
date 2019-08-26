package capstone.is4103capstone.module01.service;

import capstone.is4103capstone.module01.model.DemoRole;
import capstone.is4103capstone.module01.model.DemoUser;
import capstone.is4103capstone.module01.repository.UserRepository;

import java.util.List;

public interface UserService {
    DemoUser findByUsername(String username);
    void save(DemoUser user);

    List<DemoRole> getAllRoles();
}
