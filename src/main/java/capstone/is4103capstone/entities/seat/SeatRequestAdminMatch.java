package capstone.is4103capstone.entities.seat;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.util.enums.HierarchyTypeEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class SeatRequestAdminMatch extends DBEntityTemplate {

    @NotNull
    private HierarchyTypeEnum hierarchyType;
    @Column(unique = true)
    @NotNull
    private String hierarchyId;
    @OneToOne(fetch = FetchType.LAZY)
    private Employee seatAdmin;

    public SeatRequestAdminMatch() {
    }

    public SeatRequestAdminMatch(HierarchyTypeEnum hierarchyType, String hierarchyId, Employee seatAdmin) {
        this.hierarchyType = hierarchyType;
        this.hierarchyId = hierarchyId;
        this.seatAdmin = seatAdmin;
    }

    public HierarchyTypeEnum getHierarchyType() {
        return hierarchyType;
    }

    public void setHierarchyType(HierarchyTypeEnum hierarchyType) {
        this.hierarchyType = hierarchyType;
    }

    public String getHierarchyId() {
        return hierarchyId;
    }

    public void setHierarchyId(String hierarchyId) {
        this.hierarchyId = hierarchyId;
    }

    public Employee getSeatAdmin() {
        return seatAdmin;
    }

    public void setSeatAdmin(Employee seatAdmin) {
        this.seatAdmin = seatAdmin;
    }
}
