package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.FXRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FXRecordRepository extends JpaRepository<FXRecord,String> {
}
