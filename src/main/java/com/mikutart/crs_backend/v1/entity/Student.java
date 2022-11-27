package com.mikutart.crs_backend.v1.entity;

import com.mikutart.crs_backend.util.MD5Util;
import com.mikutart.crs_backend.v1.entity.generic.AbstractEntity;
import com.mikutart.crs_backend.v1.interfaces.IEntity;
import com.mikutart.crs_backend.v1.repository.MajorRepository;
import com.mikutart.crs_backend.v1.repository.PlanRepository;
import com.mikutart.crs_backend.v1.repository.RegistrationRepository;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "student")
@Getter
@Setter
public class Student extends AbstractEntity<Student> implements IEntity<Student> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "student_number")
    private Integer studentNumber;

    @Column(name = "name", length = 64)
    private String name;

    @Column(name = "email", length = 64)
    private String email;

    @Column(name = "start_year")
    private Integer startYear;

    @Column(name = "major_name", nullable = false)
    private String majorName;

    @Column(name = "plan_name", nullable = false)
    private String planName;

    @Column(name = "password_md5", nullable = false)
    private String passwordMd5;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    public boolean isPasswordValid(String password) {
        return MD5Util.md5(password)
            .toLowerCase(Locale.ROOT)
            .equals(passwordMd5.toLowerCase(Locale.ROOT));
    }

    public Major getMajor(MajorRepository repository) {
        return repository.findByName(majorName);
    }

    public Plan getPlan(PlanRepository repository) {
        return repository.findByName(planName);
    }

    public List<Registration> getRegistrations(RegistrationRepository repository) {
        return repository.findAllByStudentNumber(studentNumber);
    }

    @Override
    public Student newCopyWithNewId() {
        return new Student().update(this);
    }
}
