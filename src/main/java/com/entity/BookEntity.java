package com.entity;

import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
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
@Table(name="BOOK",
		indexes = {
		        @Index(name = "INDEX_TITLE", columnList = "TITLE"),
		        @Index(name = "INDEX_DESCRIPTION", columnList = "DESCRIPTION"),
		        @Index(name = "INDEX_QUANTITY_OWNED", columnList = "QUANTITY_OWNED"),
		        @Index(name = "INDEX_QUANTITY_AVAILABLE", columnList = "QUANTITY_AVAILABLE")
		    })
@DynamicInsert
@DynamicUpdate
public class BookEntity{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Size(max = 4000)
	@Column(name = "IMAGE_URL")
	private String imageURL;

	@Size(max = 100)
	@Column(name = "TITLE")
    private String title;

	@Size(max = 4000)
	@Column(name = "DESCRIPTION")
    private String description;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="BOOK_TYPE_ID", nullable=false)
	private BookTypeEntity bookType;
	
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<RentalBookEntity> rentalBooks;
    
    @Column(name = "QUANTITY_OWNED")
    private int quantityOwned;
    
    @Column(name = "QUANTITY_AVAILABLE")
    private int quantityAvailable;

}
