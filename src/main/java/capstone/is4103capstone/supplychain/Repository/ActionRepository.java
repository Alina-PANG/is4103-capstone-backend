package capstone.is4103capstone.supplychain.Repository;

import capstone.is4103capstone.entities.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, String> {
}
