package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.TrainingForm;
import capstone.is4103capstone.entities.finance.TravelForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingFormRepository extends JpaRepository<TrainingForm,String> {
    public List<TrainingForm> getByRequester_Id(String id);
}
