package capstone.is4103capstone._demoModule.repository;

import capstone.is4103capstone.entities.DemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DemoEntityRepository extends JpaRepository<DemoEntity, UUID> {

}
