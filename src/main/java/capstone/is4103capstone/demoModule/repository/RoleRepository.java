package capstone.is4103capstone.demoModule.repository;

import capstone.is4103capstone.demoModule.entity.DemoRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<DemoRole,Long> {
}
