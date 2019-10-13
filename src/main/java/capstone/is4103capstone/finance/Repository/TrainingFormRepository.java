package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.TrainingForm;
import capstone.is4103capstone.entities.finance.TravelForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingFormRepository extends JpaRepository<TrainingForm,String> {
}
