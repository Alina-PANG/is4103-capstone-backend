package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.PlanLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanLineItemRepository extends JpaRepository<PlanLineItem, String> {
}
