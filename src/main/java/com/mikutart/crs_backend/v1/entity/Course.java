package com.mikutart.crs_backend.v1.entity;

import com.mikutart.crs_backend.v1.entity.generic.AbstractEntity;
import com.mikutart.crs_backend.v1.interfaces.IEntity;
import com.mikutart.crs_backend.v1.repository.RegistrationRepository;
import com.mikutart.crs_backend.v1.repository.StaffRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "course")
@Getter
@Setter
public class Course extends AbstractEntity<Course> implements IEntity<Course> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "crn")
    private Integer crn;

    @Column(name = "name", length = 64)
    private String name;

    @Column(name = "is_open")
    private Boolean isOpen;

    @Column(name = "is_elective")
    private Boolean isElective;

    @Column(name = "credit")
    private Integer credit;

    @Column(name = "staff_number")
    private Integer staffNumber;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "location", length = 64)
    private String location;

    @Column(name = "weekday", length = 64)
    private String weekday;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "registered_count")
    private Integer registeredCount;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    public Staff getStaff(StaffRepository repository) {
        return repository.findByStaffNumber(staffNumber);
    }

    public List<Registration> getRegistrations(RegistrationRepository repository) {
        return repository.findAllByCourseCrn(crn);
    }

    @Override
    public Course newCopyWithNewId() {
        return new Course().update(this);
    }
}
