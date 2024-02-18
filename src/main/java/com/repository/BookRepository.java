package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.BookEntity;
import com.entity.BookTypeEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long>  {

	@Query("SELECT b FROM BookEntity b WHERE b.bookType = :bookType")
	public List<BookEntity> findByBookType(@Param("bookType") BookTypeEntity bookType);
	
    @Query("SELECT b FROM BookEntity b WHERE LOWER(b.title) LIKE LOWER(concat('%', :title,'%'))")
    public List<BookEntity> findByTitleLike(String title);
    
    @Query("SELECT b FROM BookEntity b WHERE LOWER(b.description) LIKE LOWER(concat('%', :description,'%'))")
    public List<BookEntity> findByDescriptionLike(String description);
    
    @Query("SELECT b FROM BookEntity b WHERE b.quantityAvailable > 0 ")
    public List<BookEntity> findAllAvailable();
    
    @Query("SELECT b FROM BookEntity b WHERE b.quantityAvailable = 0 ")
    public List<BookEntity> findAllUnavailable();

}