package com.generation.NossoPomar.controller;

import com.generation.NossoPomar.model.Category;
import com.generation.NossoPomar.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;



import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("Teste para obter todas as categorias")
    void testGetAllCategories() throws Exception {
        Category category1 = new Category();
        Category category2 = new Category();
        categoryRepository.save(category1);
        categoryRepository.save(category2);

        mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Fruits")))
                .andExpect(jsonPath("$[1].name", is("Vegetables")))
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para obter categoria por ID existente")
    void testGetCategoryById() throws Exception {
        Category category = new Category();
        categoryRepository.save(category);

        mockMvc.perform(MockMvcRequestBuilders.get("/categories/{id}", category.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Fruits")))
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para obter categoria por ID inexistente")
    void testGetCategoryByNonExistingId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/{id}", 999L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para buscar categoria por nome")
    void testGetCategoryByName() throws Exception {
        Category category = new Category();
        categoryRepository.save(category);

        mockMvc.perform(MockMvcRequestBuilders.get("/categories/category/{name}", "frutas")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Fruits")))
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para criar uma nova categoria")
    void testCreateCategory() throws Exception {
        String newCategory = "{\"name\":\"Verduras\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCategory))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Vegetables")))
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para atualizar uma categoria existente")
    void testUpdateCategory() throws Exception {
        Category category = new Category();
        categoryRepository.save(category);
        String updatedCategory = "{\"id\":" + category.getId() + ", \"name\":\"Vegetables\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedCategory))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Vegetables")))
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para tentar atualizar uma categoria inexistente")
    void testUpdateNonExistingCategory() throws Exception {
        String updatedCategory = "{\"id\":999, \"nome\":\"Categoria Inexistente\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedCategory))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para deletar uma categoria existente")
    void testDeleteCategory() throws Exception {
        Category category = new Category();
        categoryRepository.save(category);

        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/{id}", category.getId()))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para tentar deletar uma categoria inexistente")
    void testDeleteNonExistingCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/{id}", 999L))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
