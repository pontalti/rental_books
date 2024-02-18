package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.BookTypeEntity;

public interface BookTypeRepository extends JpaRepository<BookTypeEntity, Long>  {

    @Query("FROM BookTypeEntity bt WHERE LOWER(bt.type) LIKE LOWER(concat('%', :type,'%'))")
    public List<BookTypeEntity> findByType(@Param("type") String type);
    
    @Query("FROM BookTypeEntity bt WHERE LOWER(bt.description) LIKE LOWER(concat('%', :description,'%'))")
    public List<BookTypeEntity> findByDescription(@Param("description") String description);

}
