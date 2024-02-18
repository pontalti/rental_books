package com.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
@Table(name="BOOK_TYPE",
		indexes = {
		        @Index(name = "INDEX_TYPE", columnList = "TYPE")
		    })
//@DynamicInsert
//@DynamicUpdate
public class BookTypeEntity{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Size(max = 50)
	@Column(name = "TYPE")
	private String type;
	
	@Size(max = 1000)
	@Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy="bookType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookEntity> books;

}
