package com.generation.NossoPomar.model;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "The Name attribute is Mandatory")
	@Size(min = 5, max = 100, message = "Minimum size: 5, maximum: 100")
	private String name;

	@NotNull(message = "The Date attribute is Mandatory")
	private LocalDate validity;

	@NotBlank(message = "O atributo Foto é Obrigatório")
	@Size(min = 2, max = 10000, message = "Tamanho minimo: 10, maximo: 10000")
	private String photo;

	@NotBlank(message = "O atributo Descrição é Obrigatório")
	@Size(min = 5, max = 1000, message = "Tamanho minimo: 5, maximo: 1000")
	private String description;

	@NotNull(message = "O atributo Quantidade é Obrigatório")
	private int quantity;

	@NotNull(message = "O atributo Preço é Obrigatório")
	private Double price;
	
	@ManyToOne
	@JsonIgnoreProperties("product")
	private User user;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getValidity() {
		return validity;
	}

	public void setValidity(LocalDate validity) {
		this.validity = validity;
	}

	public String getFoto() {
		return photo;
	}

	public void setFoto(String photo) {
		this.photo = photo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@ManyToOne
	@JsonIgnoreProperties("product")
	private Category category;
}
