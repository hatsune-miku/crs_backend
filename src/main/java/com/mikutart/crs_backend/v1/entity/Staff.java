package com.mikutart.crs_backend.v1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mikutart.crs_backend.util.MD5Util;
import com.mikutart.crs_backend.v1.entity.generic.AbstractEntity;
import com.mikutart.crs_backend.v1.interfaces.IEntity;
import com.mikutart.crs_backend.v1.repository.CourseRepository;
import com.mikutart.crs_backend.v1.repository.MajorRepository;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "staff")
@Getter
@Setter
public class Staff extends AbstractEntity<Staff> implements IEntity<Staff> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "staff_number")
    private Integer staffNumber;

    @Column(name = "name", length = 64)
    private String name;

    @Column(name = "email", length = 64)
    private String email;

    @Column(name = "major_name", nullable = false)
    private String majorName;

    @Column(name = "password_md5", nullable = false)
    private String passwordMd5;

    @JsonIgnore
    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

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

    public List<Course> getCourses(CourseRepository repository) {
        return repository.findAllByStaffNumber(staffNumber);
    }

    @Override
    public Staff newCopyWithNewId() {
        return new Staff().update(this);
    }
}
