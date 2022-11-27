package com.mikutart.crs_backend.v1.entity;

import com.mikutart.crs_backend.v1.entity.generic.AbstractEntity;
import com.mikutart.crs_backend.v1.interfaces.IEntity;
import com.mikutart.crs_backend.v1.repository.CourseRepository;
import com.mikutart.crs_backend.v1.repository.GradeRepository;
import com.mikutart.crs_backend.v1.repository.StudentRepository;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "registration")
@Getter
@Setter
public class Registration extends AbstractEntity<Registration> implements IEntity<Registration> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "course_crn", nullable = false)
    private Integer courseCrn;

    @Column(name = "student_number", nullable = false)
    private Integer studentNumber;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    public Student getStudent(StudentRepository repository) {
        return repository.findByStudentNumber(studentNumber);
    }

    public Course getCourse(CourseRepository repository) {
        return repository.findByCrn(courseCrn);
    }

    public Grade getGrade(GradeRepository repository) {
        return repository.findByRegistrationId(id);
    }

    @Override
    public Registration newCopyWithNewId() {
        return new Registration().update(this);
    }
}
