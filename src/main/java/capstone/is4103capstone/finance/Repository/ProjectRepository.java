package capstone.is4103capstone.finance.Repository;

import capstone.is4103capstone.entities.finance.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,String> {


    @Query(value = "SELECT * FROM project WHERE is_deleted=0",nativeQuery = true)
    public List<Project> getAllProjects();
    @Query(value = "SELECT * FROM project WHERE is_deleted=0 AND (project_owner_id=?1 OR supervisor_id=?1)",nativeQuery = true)
    public List<Project> getProjectsByProjectOwnerId(String id);

    public Project getProjectByCode(String code);
}
