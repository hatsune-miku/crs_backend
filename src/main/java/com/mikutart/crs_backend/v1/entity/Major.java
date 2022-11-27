package com.mikutart.crs_backend.v1.entity;

import com.mikutart.crs_backend.v1.entity.generic.AbstractEntity;
import com.mikutart.crs_backend.v1.interfaces.IEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "major")
@Getter
@Setter
public class Major extends AbstractEntity<Major> implements IEntity<Major> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 64)
    private String name;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Override
    public Major newCopyWithNewId() {
        return new Major().update(this);
    }
}
