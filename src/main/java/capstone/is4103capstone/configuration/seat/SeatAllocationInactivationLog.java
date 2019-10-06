package capstone.is4103capstone.entities.seat;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@Entity
public class SeatAllocationInactivationLog {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
            name = "uuid",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(unique = true, updatable = false, nullable = false,length = 36)
    @Length(min=36, max=36)
    private String id;
    private String allocation_id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date inactivate_time;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_time;
    @Temporal(TemporalType.TIMESTAMP)
    private Date completion_time;
    private boolean isDone = false;
    private boolean isCancelled = false;

    public SeatAllocationInactivationLog () {
        created_time = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAllocation_id() {
        return allocation_id;
    }

    public void setAllocation_id(String allocation_id) {
        this.allocation_id = allocation_id;
    }

    public Date getInactivate_time() {
        return inactivate_time;
    }

    public void setInactivate_time(Date inactivate_time) {
        this.inactivate_time = inactivate_time;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public Date getCompletion_time() {
        return completion_time;
    }

    public void setCompletion_time(Date completion_time) {
        this.completion_time = completion_time;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}
