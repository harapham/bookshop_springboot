package com.shop.library.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="categories", uniqueConstraints = @UniqueConstraint(columnNames = "name") )
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Long id;
	private String name;
	private boolean is_deleted;
	private boolean is_activated;

	private int sale;
	public Category(String name) {
		this.name = name;
		this.is_activated= true;
		this.is_deleted = false;
	}
	
//	public Category() {
//		this.is_activated= true;
//		this.is_deleted = false;
//	}
}
