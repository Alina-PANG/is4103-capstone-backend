package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.SpendingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpendingRecordRepository extends JpaRepository<SpendingRecord,String> {

}
