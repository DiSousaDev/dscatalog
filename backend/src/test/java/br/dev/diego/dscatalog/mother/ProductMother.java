package br.dev.diego.dscatalog.mother;

import br.dev.diego.dscatalog.controllers.dto.ProductDto;
import br.dev.diego.dscatalog.controllers.dto.ProductInsertDto;
import br.dev.diego.dscatalog.controllers.dto.ProductUpdateDto;
import br.dev.diego.dscatalog.entities.Category;
import br.dev.diego.dscatalog.entities.Product;

import java.time.Instant;

public class ProductMother {

    public static Product getNewProduct() {
        Product product = new Product();
        product.setName("Smart TV");
        product.setDate(Instant.parse("2020-07-14T10:00:00Z"));
        product.setDescription("Good product");
        product.setImgUrl("https://img.com/1234.png");

        product.getCategories().add(new Category(2L, "Electronics"));

        return product;
    }

    public static Product getProduct() {
        Product product = getNewProduct();
        product.setId(1L);
        return product;
    }

    public static ProductDto getProductDto() {
        Product product = getNewProduct();
        product.setId(1L);
        return new ProductDto(product, product.getCategories());
    }

    public static ProductUpdateDto getProductUpdatedDto() {
        return new ProductUpdateDto(1L, "Prod Atualizado", "Desc Atualizada", 9.99, "Img atualizada", Instant.now());
    }

    public static ProductInsertDto getProductInsertDto () {
        return new ProductInsertDto(1L, "Prod Criado", "Desc Criada", 9.99, "Img Criada", Instant.now());
    }

}
