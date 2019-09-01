package capstone.is4103capstone.demoModule.service;

import capstone.is4103capstone.demoModule.entity.DemoRole;
import capstone.is4103capstone.demoModule.entity.DemoUser;
import capstone.is4103capstone.demoModule.repository.RoleRepository;
import capstone.is4103capstone.demoModule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public DemoUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(DemoUser user) {
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public List<DemoRole> getAllRoles() {
        return roleRepository.findAll();
    }
}
