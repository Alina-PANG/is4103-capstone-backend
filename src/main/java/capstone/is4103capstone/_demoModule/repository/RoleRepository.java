package capstone.is4103capstone._demoModule.repository;

import capstone.is4103capstone._demoModule.entity.DemoRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<DemoRole,Long> {
}
