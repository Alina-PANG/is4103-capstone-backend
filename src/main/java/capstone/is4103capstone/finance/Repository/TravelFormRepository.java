package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.TravelForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TravelFormRepository  extends JpaRepository<TravelForm,String> {

    List<TravelForm> getTravelPlanByRequester_Id(String id);
    List<TravelForm> getTravelFormByApprover_Id(String id);
    Optional<TravelForm> getTravelFormByCode(String code);

}
