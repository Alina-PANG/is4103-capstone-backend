package capstone.is4103capstone.module01.repository;

import capstone.is4103capstone.module01.model.DemoRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<DemoRole,Long> {
}
