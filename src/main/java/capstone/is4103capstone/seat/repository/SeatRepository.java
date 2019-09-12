package capstone.is4103capstone.seat.repository;

import capstone.is4103capstone.entities.seat.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, UUID> {
    @Override
    <S extends Seat> S save(S s);
}
