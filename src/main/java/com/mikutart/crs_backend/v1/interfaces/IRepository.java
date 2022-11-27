package com.mikutart.crs_backend.v1.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IRepository<T extends IEntity<T> > extends JpaRepository<T, Integer> {

}
