package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.RentalEntity;

public interface RentalRepository extends JpaRepository<RentalEntity, Long>  {

}
