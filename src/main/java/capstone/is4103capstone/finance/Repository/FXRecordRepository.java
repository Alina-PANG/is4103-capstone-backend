package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.FXRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface FXRecordRepository extends JpaRepository<FXRecord,String> {

    @Query(value = "SELECT * FROM fx_record fx WHERE fx.is_deleted=false AND fx.effective_date <= :endDate AND fx.effective_date >= :startDate AND fx.base_currency_abbr LIKE %:baseCurr% AND fx.price_currency_abbr LIKE %:priceCurr%",nativeQuery = true)
    List<FXRecord> findAllWithConstraints(@Param("baseCurr") String baseCurr, @Param("priceCurr") String priceCurr, @Param("startDate") Date startDate,@Param("endDate") Date endDate);



}
