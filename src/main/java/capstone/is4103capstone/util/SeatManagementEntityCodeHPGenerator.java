package capstone.is4103capstone.util;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.ApprovalForRequest;
import capstone.is4103capstone.entities.seat.SeatAllocationRequest;
import capstone.is4103capstone.util.exception.RepositoryEntityMismatchException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public class SeatManagementEntityCodeHPGenerator {

    // Code
    // SAR-Team-seqNo	SAR-SG-Tech-InfraTech-EndUserCom-01
    private final String SEAT_ALLOCATION_REQUEST_CODE_TEMPLATE = "SAR-%1$s-%2$s";
    // RT+requesterUsername + seqNo.	RT-yingshi2502-1
    private final String APPROVAL_FOR_REQUEST_CODE_TEMPLATE = "RT-%1$s-%2$s";



    public String generateCode(JpaRepository repo, DBEntityTemplate entity, String additionalInfo) throws Exception {
        return process(repo,entity,additionalInfo);
    }

    public String generateCode(JpaRepository repo, DBEntityTemplate entity) throws Exception {
        return process(repo,entity,"");
    }


    private String process(JpaRepository repo, DBEntityTemplate entity,String additionalInfo) throws Exception {

        Optional<DBEntityTemplate> optionalEntity = repo.findById(entity.getId());
        String code = "";

        if (optionalEntity.isPresent()){

            entity = optionalEntity.get();
            if (entity.getSeqNo() == null){
                entity.setSeqNo(new Long(repo.findAll().size()));
            }
            String seqNoStr = String.valueOf(entity.getSeqNo());
            String entityClassName = entity.getClass().getSimpleName();

            try {
                switch (entityClassName) {
                    case "ApprovalForRequest":
                        code = String.format(APPROVAL_FOR_REQUEST_CODE_TEMPLATE, entity.getCreatedBy() == null ?
                                ((ApprovalForRequest)entity).getRequester().getUserName():entity.getCreatedBy(), seqNoStr);
                        break;
                    case "SeatAllocationRequest":
                        SeatAllocationRequest seatAllocationRequest = (SeatAllocationRequest)entity;
                        code = String.format(SEAT_ALLOCATION_REQUEST_CODE_TEMPLATE, seatAllocationRequest.getTeam().getCode(), entity.getSeqNo());
                        break;
                    default:
                        System.out.println("Entity class name is not found in seat management subsystem.");
                }
            }catch (Exception e){
                throw new Exception("Entity class have null name.");
            }

            code = code.toUpperCase();
            entity.setCode(code);
            repo.saveAndFlush(entity);
            return code;
        }else {
            throw new RepositoryEntityMismatchException("Repository class given does not match the entity type.");
        }
    }
}
