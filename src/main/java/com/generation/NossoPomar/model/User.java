package com.generation.NossoPomar.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O atributo Nome é Obrigatório")
	@Size(min = 3, max = 100, message = "Tamanho minimo: 5, maximo: 100")
	private String name;

	@NotBlank(message = "O atributo Senha é Obrigatório")
	@Size(min = 8, message = "Tamanho minimo: 8")
	private String password;

	@NotBlank(message = "O campo tipo do usuário é Obrigatório")
	private String type;

	@NotBlank(message = "O atributo Email é Obrigatório")
	@Size(min = 10, max = 1000, message = "Tamanho minimo: 10, maximo: 1000")
	@Email
	private String email;

	@Size(min = 2, max = 10000, message = "Tamanho minimo: 2, maximo: 1000")
	private String photo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("user")
	private List<Product> product;

	public List<Product> getProduto() {
		return product;
	}

	public void setProduto(List<Product> product) {
		this.product = product;
	}

	public User(Long id, String name, String password, String type, String email, String photo) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.type = type;
		this.email = email;
		this.photo = photo;

	}

	public User() {
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public List<Product> getProduct() {
		return product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", type=" + type + ", email=" + email
				+ ", photo=" + photo + ", product=" + product + "]";
	}

}
