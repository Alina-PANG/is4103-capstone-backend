package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.dto.AuditTrailActivityDto;
import capstone.is4103capstone.admin.repository.AuditTrailActivityRepo;
import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.entities.AuditTrailActivity;
import capstone.is4103capstone.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuditTrailActivityService {

    @Autowired
    AuditTrailActivityRepo auditTrailActivityRepo;

    @Autowired
    EmployeeRepository employeeRepository;

    private final int[] TRACE_TRACK_LEVELS = {5,4,3,2,1};
    private final String SPLIT_CHAR = ".";

    public AuditTrailActivity createNewRecordUsingUserUuid(String activityName, String userUuid) {
        AuditTrailActivity ata;
        // check if you want autodetection
        if (activityName.equalsIgnoreCase("autodetect")) {
            ata = new AuditTrailActivity(userUuid, composeTraceTrack());
        } else {
            ata = new AuditTrailActivity(userUuid, activityName);
        }
        return auditTrailActivityRepo.save(ata);
    }

    public AuditTrailActivity createNewRecordUsingUsername(String activityName, String userName) throws Exception {
        AuditTrailActivity ata;
        // Lookup user by userName
        Employee employee = employeeRepository.findEmployeeByUserName(userName);
        if (Objects.isNull(employee)) throw new Exception("User with username " + userName + " not found!");
        // check if you want autodetection
        if (activityName.equalsIgnoreCase("autodetect")) {
            ata = new AuditTrailActivity(employee.getId(), composeTraceTrack());
        } else {
            ata = new AuditTrailActivity(employee.getId(), activityName);
        }
        return auditTrailActivityRepo.save(ata);
    }

    private String composeTraceTrack(){//[TODO: not done]
        List<String> levels = new ArrayList<>();
        for (int i = 0; i < TRACE_TRACK_LEVELS.length; i++){
            levels.add(Thread.currentThread().getStackTrace()[i].getMethodName());
        }
        return String.join(SPLIT_CHAR,levels);
    }


    public List<AuditTrailActivity> getAllAuditTrailRecords() throws Exception {
        List<AuditTrailActivity> result = auditTrailActivityRepo.findAll();
        if (result.isEmpty()) throw new Exception("There are no audit trail events in the database.");
        return result;
    }

    public List<AuditTrailActivity> getAuditTrailRecordsByActivity(String activity) throws Exception {
        List<AuditTrailActivity> result = auditTrailActivityRepo.findAuditTrailActivitiesByActivity(activity);
        if (result.isEmpty())
            throw new Exception("No audit trail records for activity " + activity + ".");
        return result;
    }

    public List<AuditTrailActivity> getAuditTrailRecordsByUserUuid(String uuid) throws Exception {
        List<AuditTrailActivity> result = auditTrailActivityRepo.findAuditTrailActivitiesByUserUuid(uuid);
        if (result.isEmpty())
            throw new Exception("No audit trail records for user with UUID " + uuid + ".");
        return result;
    }

    public List<AuditTrailActivity> getAuditTrailRecordsByUsername(String userName) throws Exception {
        // find the user's UUID by username
        Employee employee = employeeRepository.findEmployeeByUserName(userName);
        if (Objects.isNull(employee)) throw new Exception("Employee " + userName + " not found!");
        List<AuditTrailActivity> result = auditTrailActivityRepo.findAuditTrailActivitiesByUserUuid(employee.getId());
        if (result.isEmpty())
            throw new Exception("No audit trail records for user with UUID " + userName + ".");
        return result;
    }

    // ===== Entity to DTO conversion methods =====
    public AuditTrailActivity dtoToEntity(AuditTrailActivityDto input) {
        AuditTrailActivity auditTrailActivity = new AuditTrailActivity();
        input.getId().ifPresent(auditTrailActivity::setId);
        input.getActivity().ifPresent(auditTrailActivity::setActivity);
        input.getUserUuid().ifPresent(auditTrailActivity::setUserUuid);
        input.getTimeStamp().ifPresent(auditTrailActivity::setTimeStamp);
        return auditTrailActivity;
    }

    public AuditTrailActivityDto entityToDto(AuditTrailActivity input) {
        AuditTrailActivityDto auditTrailActivityDto = new AuditTrailActivityDto();
        auditTrailActivityDto.setId(Optional.of(input.getId()));
        auditTrailActivityDto.setActivity(Optional.of(input.getActivity()));
        auditTrailActivityDto.setUserUuid(Optional.of(input.getUserUuid()));
        auditTrailActivityDto.setTimeStamp(Optional.of(input.getTimeStamp()));
        return auditTrailActivityDto;
    }

    public List<AuditTrailActivity> dtoToEntity(List<AuditTrailActivityDto> input) {
        List<AuditTrailActivity> auditTrailActivities = new ArrayList<>();
        for (AuditTrailActivityDto current : input) {
            auditTrailActivities.add(dtoToEntity(current));
        }
        return auditTrailActivities;
    }

    public List<AuditTrailActivityDto> entityToDto(List<AuditTrailActivity> input) {
        List<AuditTrailActivityDto> auditTrailActivities = new ArrayList<>();
        for (AuditTrailActivity current : input) {
            auditTrailActivities.add(entityToDto(current));
        }
        return auditTrailActivities;
    }
}
