package br.dev.diego.dscatalog.controllers.dto;

import br.dev.diego.dscatalog.entities.Category;
import br.dev.diego.dscatalog.entities.Product;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imgUrl;
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
