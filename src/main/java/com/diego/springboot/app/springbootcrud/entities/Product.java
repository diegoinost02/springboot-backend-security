package com.diego.springboot.app.springbootcrud.entities;

import com.diego.springboot.app.springbootcrud.Validation.IsExistDb;
import com.diego.springboot.app.springbootcrud.Validation.IsRequired;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.NotEmpty; 
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //NotEmpty es para strings
    //NotBlanck además valida que no sean espacios en blanco
    //NotNull es para demás objetos
    //en las validaciones agregamos el parametro message en caso de personalizar los mensajes de error mediante messages.propierties

    // @NotEmpty(message = "{NotEmpty.product.name}")
    @IsRequired(message = "{IsRequired.product.name}") //Validacion personalizada
    @Size(min = 3, max = 20)
    private String name;

    @NotNull(message = "{NotNull.product.price}")
    @Min(value = 500, message = "{Min.product.price}")
    private Integer price;
    
    // @NotBlank(message = "{NotBlank.product.description}")
    @IsRequired //Validacion personalizada
    private String description;

    @IsRequired
    @IsExistDb
    private String sku;

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
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }
    
}
