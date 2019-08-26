package capstone.is4103capstone.module01.repository;

import capstone.is4103capstone.module01.model.DemoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<DemoUser,Long> {

    DemoUser findByUsername(String username);
}
