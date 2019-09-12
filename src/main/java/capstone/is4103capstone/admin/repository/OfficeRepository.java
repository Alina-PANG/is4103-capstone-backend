package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfficeRepository extends JpaRepository<Office,String> {
    @Override
    <S extends Office> S save(S s);

    @Override
    Optional<Office> findById(String s);
}
