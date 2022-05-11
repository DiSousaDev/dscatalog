package br.dev.diego.dscatalog.controllers.dto;

import java.time.Instant;

public class ProductInsertDto extends ProductDto {

    public ProductInsertDto() {
    }

    public ProductInsertDto(Long id, String name, String description, Double price, String imgUrl, Instant date) {
        super(id, name, description, price, imgUrl, date);
    }
}
