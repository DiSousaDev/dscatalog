package br.dev.diego.dscatalog.controllers.dto;

import br.dev.diego.dscatalog.entities.Category;
import br.dev.diego.dscatalog.entities.Product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductDto {

    private Long id;

    @Size(min =  3, max = 60, message = "O campo nome deve ter entre {min} e {max} caracteres.")
    @NotBlank(message = "Campo obrigatório.")
    private String name;

    @NotBlank(message = "Campo obrigatório.")
    private String description;

    @Positive(message = "O Preço deve ser positivo.")
    private Double price;
    private String imgUrl;

    @PastOrPresent(message = "A data do produto não pode ser futura.")
    private Instant date;
    private List<CategoryDto> categories = new ArrayList<>();

    public ProductDto() {

    }

    public ProductDto(Long id, String name, String description, Double price, String imgUrl, Instant date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
    }

    public ProductDto(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
        date = entity.getDate();
    }

    public ProductDto(Product entity, Set<Category> categories) {
        this(entity);
        this.categories = categories.stream().map(category -> new CategoryDto(category.getId(),
                category.getName())).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Instant getDate() {
        return date;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }
}
