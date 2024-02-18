package com.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name="RENTAL_BOOK",
		indexes = {
		        @Index(name = "INDEX_BOOK_ID", columnList = "BOOK_ID"),
		        @Index(name = "INDEX_QUANTITY", columnList = "QUANTITY")
		    })
//@DynamicInsert
//@DynamicUpdate
public class RentalBookEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    @JoinColumn(name = "BOOK_ID")
    private BookEntity book;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RENTAL_ID")
	private RentalEntity rental;
    
    @Column(name = "QUANTITY")
    private int quantity;
}
