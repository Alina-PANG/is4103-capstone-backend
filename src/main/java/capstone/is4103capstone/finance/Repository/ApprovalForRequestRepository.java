package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.ApprovalForRequest;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ApprovalForRequestRepository extends JpaRepository<ApprovalForRequest,String> {

    @Query(value = "SELECT * FROM approval_for_request a WHERE a.requested_item_id = ?1 and a.is_deleted=false AND a.approval_status=0 AND a.approver_id=?2", nativeQuery = true)
    Optional<ApprovalForRequest> findPendingTicketByEntityIdAndApproverId(String entityId, String approverId);

    @Query(value = "SELECT * FROM approval_for_request a WHERE a.requested_item_id=?1 and a.is_deleted=false ORDER BY a.last_modified_date_time",nativeQuery = true)
    List<ApprovalForRequest> findTicketsByRequestedItemId(String id);

    @Query(value = "SELECT * FROM approval_for_request a WHERE a.id = ?1 and a.is_deleted=false", nativeQuery = true)
    Optional<ApprovalForRequest> findUndeletedTicketById(String ticketId);

    @Query(value = "SELECT * FROM approval_for_request a WHERE a.approver_id=?1 and a.is_deleted=false and a.approval_status=0",nativeQuery = true)
    List<ApprovalForRequest> findPendingTicketsByApproverId(String employeeId);
}
