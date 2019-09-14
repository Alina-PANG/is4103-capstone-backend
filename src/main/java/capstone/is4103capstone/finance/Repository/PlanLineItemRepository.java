package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.PlanLineItem;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface PlanLineItemRepository extends JpaRepository<PlanLineItem,String> {
}
