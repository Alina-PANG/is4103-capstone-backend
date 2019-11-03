package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.TrainingForm;
import capstone.is4103capstone.entities.finance.TravelForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainingFormRepository extends JpaRepository<TrainingForm,String> {
    public List<TrainingForm> getByRequester_Id(String id);
    public List<TrainingForm> getByApprover_Id(String id);

    public Optional<TrainingForm> getByCode(String code);
}
