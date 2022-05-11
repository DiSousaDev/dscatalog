package br.dev.diego.dscatalog.controllers;

import br.dev.diego.dscatalog.controllers.dto.ProductDto;
import br.dev.diego.dscatalog.controllers.dto.ProductInsertDto;
import br.dev.diego.dscatalog.controllers.dto.ProductUpdateDto;
import br.dev.diego.dscatalog.mother.ProductMother;
import br.dev.diego.dscatalog.services.ProductService;
import br.dev.diego.dscatalog.services.exceptions.DataNotFoundException;
import br.dev.diego.dscatalog.services.exceptions.DatabaseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    private ProductDto productDto;
    private ProductUpdateDto productUpdateDto;
    private ProductInsertDto productInsertDto;
    private PageImpl<ProductDto> page;
    private long existingId;
    private long nonExistingId;
    private long productWithEntity;

    @BeforeEach
    void setUp() {

        productDto = ProductMother.getProductDto();
        productUpdateDto = ProductMother.getProductUpdatedDto();
        productInsertDto = ProductMother.getProductInsertDto();
        page = new PageImpl<>(List.of(productDto));
        existingId = 1L;
        nonExistingId = 1000L;
        productWithEntity = 2L;

        when(service.findAllPaged(any(Pageable.class))).thenReturn(page);
        when(service.findById(existingId)).thenReturn(productDto);
        when(service.findById(nonExistingId)).thenThrow(DataNotFoundException.class);

        when(service.save(any())).thenReturn(productDto);

        when(service.update(eq(existingId),any())).thenReturn(productDto);
        when(service.update(eq(nonExistingId), any())).thenThrow(DataNotFoundException.class);

        doNothing().when(service).deleteById(existingId);
        doThrow(DataNotFoundException.class).when(service).deleteById(nonExistingId);
        doThrow(DatabaseException.class).when(service).deleteById(productWithEntity);

    }

    @Test
    void saveShouldReturnProductDtoCreated() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productInsertDto);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/products")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    @Test
    void updateShouldReturnProductDtoWhenIdExists() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(productUpdateDto);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    @Test
    void updateShouldReturnNotFoundWhenIdDoesntExists() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(productUpdateDto);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", nonExistingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    void findAllShouldReturnPage() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/products")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    void findByIdShouldReturnProductDtoWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    @Test
    void findByIdShouldReturnNotFoundWhenIdDoesntExists() throws Exception {

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());

    }

    @Test
    void deleteShouldReturnNoContentWhenIdExists() throws Exception {

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent());

    }

    @Test
    void deleteShouldReturnNotFoundWhenIdDoesntExists() throws Exception {

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());

    }

    @Test
    void deleteShouldReturnBadRequestWhenIdHasEntityAssociated() throws Exception {

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", productWithEntity)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());

    }
}