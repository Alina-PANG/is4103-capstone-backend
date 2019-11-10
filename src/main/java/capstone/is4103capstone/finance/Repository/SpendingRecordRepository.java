package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.SpendingRecord;
import capstone.is4103capstone.finance.dashboard.model.dbModel.SpendingRecordAggreDBModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpendingRecordRepository extends JpaRepository<SpendingRecord,String> {

    @Query(value = "select \n" +
            "sp.service_name,  s.code as service_code, SUM(sp.spending_amt_ingbp) as total_spending_per_serv,\n" +
            "c.object_name as country_name, c.code as country_code, c.hierachy_path as country_hp\n" +
            "from spending_record sp join service s join purchase_order po join country c\n" +
            "on sp.service_id = s.id and sp.po_id = po.id and c.id=po.country_id\n" +
            "where sp.is_deleted=0 and po.is_deleted=0 and sp.is_deleted=0 and ?2 <= sp.last_modified_date_time and sp.last_modified_date_time <= ?3 \n" +
            "and c.hierachy_path like CONCAT('%',?1,'%')\n"+
            "group by s.code\n" ,nativeQuery = true)
    public List<SpendingRecordAggreDBModel> retrieveSpendingRecordByCountryAndYear(String countryOrRegionCode, String yearStartDate, String yearEndDate);


}
