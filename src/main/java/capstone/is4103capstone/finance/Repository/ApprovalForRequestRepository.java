package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.ApprovalForRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalForRequestRepository extends JpaRepository<ApprovalForRequest,String> {

    List<ApprovalForRequest> findTicketsForEntity(String entityId);
}
