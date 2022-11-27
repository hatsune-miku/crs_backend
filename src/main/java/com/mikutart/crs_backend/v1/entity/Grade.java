package com.mikutart.crs_backend.v1.entity;

import com.mikutart.crs_backend.v1.entity.generic.AbstractEntity;
import com.mikutart.crs_backend.v1.interfaces.IEntity;
import com.mikutart.crs_backend.v1.repository.RegistrationRepository;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "grade")
@Getter
@Setter
public class Grade extends AbstractEntity<Grade> implements IEntity<Grade> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "registration_id", nullable = false)
    private Integer registrationId;

    @Column(name = "score_earned")
    private Integer scoreEarned;

    @Column(name = "score_total")
    private Integer scoreTotal;

    @Column(name = "score_passing")
    private Integer scorePassing;

    @Column(name = "is_passed")
    private Integer isPassed;

    @Column(name = "note", length = 256)
    private String note;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    public Registration getRegistration(RegistrationRepository repository) {
        return repository.getReferenceById(registrationId);
    }

    @Override
    public Grade newCopyWithNewId() {
        return new Grade().update(this);
    }
}
