package capstone.is4103capstone.seat.repository;

import capstone.is4103capstone.entities.seat.SeatMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SeatMapRepository extends JpaRepository<SeatMap, String> {
    @Override
    <S extends SeatMap> S save(S s);

    @Override
    Optional<SeatMap> findById(String String);
}
