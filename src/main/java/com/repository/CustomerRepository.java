package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.CustomerEntity;
import com.enums.RentalStatus;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>  {

	@Query("SELECT c FROM CustomerEntity c WHERE c.name LIKE %:name%")
	public List<CustomerEntity> findByName(@Param("name") String name);
	
    @Query("SELECT DISTINCT c FROM CustomerEntity c INNER JOIN c.rentalEntities rb WHERE rb.status = :status")
    public List<CustomerEntity> findAllCustomersbyRentalStatus(@Param("status") RentalStatus status);
	
}
