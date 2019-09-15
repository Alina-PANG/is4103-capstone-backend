package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.SecurityGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityGroupRepository extends JpaRepository<SecurityGroup, String> {
}
