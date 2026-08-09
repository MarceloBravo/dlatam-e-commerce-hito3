package com.mabc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mabc.dto.CategoryDTO;
import com.mabc.entities.Category;
import com.mabc.repositories.CategoryRepository;
import com.mabc.services.category.CategoryServiceImpl;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class CategoryServiceImplTest {
    private CategoryRepository categoryRepository;
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    @DisplayName("Graba una nueva categoría con datos válidos")
    public void testSaveCategoryWithValidData() {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO(null, "Electronics", true);
        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName("Electronics");
        savedCategory.setActive(true);

        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        // Act
        CategoryDTO result = categoryService.saveCategory(categoryDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(categoryDTO.getName(), result.getName());
        assertEquals(categoryDTO.getActive(), result.getActive());
    }

    @Test
    @DisplayName("Actualiza una nueva categoría con datos válidos")
    public void testUpdateCategoryWithValidData() {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Electronica");
        categoryDTO.setActive(true);

        Category existsCategory = new Category();
        existsCategory.setId(1L);
        existsCategory.setName("Electronica");
        existsCategory.setActive(true);

        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName("Electronica");
        savedCategory.setActive(true);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(existsCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        // Act
        CategoryDTO result = categoryService.saveCategory(categoryDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(categoryDTO.getName(), result.getName());
        assertEquals(categoryDTO.getActive(), result.getActive());
    }

    @Test
    @DisplayName("Crea una categoría nueva sin consultar por un id nulo")
    public void testSaveCategoryWithNullIdDoesNotQueryByNullId() {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO(null, "Electronics", true);
        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName("Electronics");
        savedCategory.setActive(true);

        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        // Act
        CategoryDTO result = categoryService.saveCategory(categoryDTO);

        // Assert
        assertNotNull(result);
        verify(categoryRepository, never()).findById(any());

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(captor.capture());
        assertNull(captor.getValue().getId());
        assertEquals("Electronics", captor.getValue().getName());
        assertTrue(captor.getValue().getActive());
    }

    @Test
    @DisplayName("Intenta grabar una nueva categoría con datos inválidos")
    public void testSaveCategoryWithInvalidData() {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO(null, "", null);

        // Act 
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> this.categoryService.saveCategory(categoryDTO));

        // Assert
        assertEquals("Invalid category data", exception.getMessage());
        verify(this.categoryRepository, never()).save(any(Category.class));
    }

    @Test
    @DisplayName("Intenta grabar una nueva categoría con todos los datos nulos")
    public void testSaveCategoryWithAllPropertiesNull() {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO(null, null, null);

        // Act 
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> this.categoryService.saveCategory(categoryDTO));

        // Assert
        assertEquals("Invalid category data", exception.getMessage());
        verify(this.categoryRepository, never()).save(any(Category.class));
    }


    @Test
    @DisplayName("Obtiene una categoría a partir de un ID")
    public void testGetCategoryById(){
        // Arrange
        Long searchedId = 1L;
        Category category = new Category();
        category.setId(searchedId);
        category.setName("Electrónica");
        category.setActive(true);
        when(this.categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        // Act
        CategoryDTO foundCategoryDTO = this.categoryService.getCategoryById(1L);

        // Asert
        assertNotNull(foundCategoryDTO);
        assertEquals(foundCategoryDTO.getId(), searchedId);

    }

    @Test
    @DisplayName("Obtiene un nulo a partir de un ID no existente")
    public void testGetNullByInvalidId(){
        // Arrange
        Long searchedId = 99999L;
        when(this.categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        CategoryDTO foundCategoryDTO = this.categoryService.getCategoryById(99999L);

        // Asert
        assertNull(foundCategoryDTO);

    }

    @Test
    @DisplayName("Elimina una categoría a partir de un ID existente")
    public void testDeleteCategoryById(){
        // Arrange
        Long searchedId = 1L;
        when(this.categoryRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(this.categoryRepository).deleteById(anyLong());

        // Act
        this.categoryService.deleteCategory(searchedId);

        // Asert
        verify(this.categoryRepository, times(1)).existsById(searchedId);
        verify(this.categoryRepository, times(1)).deleteById(searchedId);
    }

    @Test
    @DisplayName("Elimina una categoría a partir de un ID inexistente")
    public void testDeleteCategoryByIdWithInvalidId(){
        // Arrange
        Long searchedId = -1L;
        when(this.categoryRepository.existsById(anyLong())).thenReturn(false);

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> this.categoryService.deleteCategory(searchedId));
        

        // Asert
        verify(this.categoryRepository, times(1)).existsById(searchedId);
        verify(this.categoryRepository, times(0)).deleteById(searchedId);
    }
}