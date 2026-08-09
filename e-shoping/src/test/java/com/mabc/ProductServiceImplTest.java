package com.mabc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mabc.dto.ProductDTO;
import com.mabc.dto.CategoryDTO;
import com.mabc.dto.MarkDTO;
import com.mabc.entities.Product;
import com.mabc.entities.Category;
import com.mabc.entities.Mark;
import com.mabc.repositories.ProductRepository;
import com.mabc.repositories.CategoryRepository;
import com.mabc.repositories.MarkRepository;
import com.mabc.services.product.ProductServiceImpl;
import com.mabc.services.category.CategoryServiceImpl;
import com.mabc.services.mark.MarkServiceImpl;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class ProductServiceImplTest{
    
    private ProductServiceImpl productService;
    private CategoryServiceImpl categoryService;
    private MarkServiceImpl markService;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private MarkRepository markRepository;

    @BeforeEach
    public void setUp(){
        this.categoryRepository =  mock(CategoryRepository.class);
        this.categoryService = new CategoryServiceImpl(categoryRepository);
        
        this.markRepository =  mock(MarkRepository.class);
        this.markService = new MarkServiceImpl(markRepository);

        this.productRepository =  mock(ProductRepository.class);
        this.productService = new ProductServiceImpl(productRepository, categoryRepository, markRepository);
    }

    @Test
    @DisplayName("Registra un nuevo producto con todos sus atributos válidos y retorna el producto registrado")
    public void testSaveANewProduct(){
        // Arrange
        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Computación", true);
        categoriesDTO.add(categoryDTO);
        Category category = new Category(1L, "Computación", true);
        categories.add(category);

        MarkDTO markDTO = new MarkDTO(1L, "Lenovo", true);
        Mark mark = new Mark(1L, "Lenovo", true);
        
        ProductDTO productDTO = new ProductDTO(
            null, 
            markDTO,
            categoriesDTO, 
            "Notebook Lenovo", 
            "Notebook Lenovo IdeaPad 310", 
            12, 
            1500, 
            650000,
            800000
        );

        Product product = new Product(
            1L, 
            mark,
            categories, 
            "Notebook Lenovo", 
            "Notebook Lenovo IdeaPad 310", 
            12, 
            1500, 
            650000,
            800000
        );

        List<Long> categoryIds = productDTO.getCategories().stream().map(CategoryDTO::getId).toList();
        when(this.categoryRepository.findAll()).thenReturn(List.of(category));
        when(this.markRepository.findById(anyLong())).thenReturn(Optional.of(mark));        
        when(this.productRepository.save(any(Product.class))).thenReturn(product);
        
        // Act
        ProductDTO result = this.productService.saveProduct(productDTO);

        // Assert
        assertNotNull(result);
        verify(this.categoryRepository, times(1)).findAll();
        verify(this.markRepository, times(1)).findById(productDTO.getMark().getId());
        verify(this.productRepository, times(1)).save(any(Product.class));
    }
    
    @Test
    @DisplayName("Intenta Registrar un nuevo producto con una marca que no existe y lanza una excepción")
    public void testSaveANewProductButMarkNotExistsAndThrowsException(){
        // Arrange
        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Computación", true);
        categoriesDTO.add(categoryDTO);
        Category category = new Category(1L, "Computación", true);
        categories.add(category);

        MarkDTO markDTO = new MarkDTO(1L, "Lenovo", true);
        Mark mark = new Mark(1L, "Asus", true);
        
        ProductDTO productDTO = new ProductDTO(
            null, 
            markDTO,
            categoriesDTO, 
            "Notebook Lenovo", 
            "Notebook Lenovo IdeaPad 310", 
            12, 
            1500, 
            650000,
            800000
        );

        Product product = new Product(
            1L, 
            mark,
            categories, 
            "Notebook Lenovo", 
            "Notebook Lenovo IdeaPad 310", 
            12, 
            1500, 
            650000,
            800000
        );

        List<Long> categoryIds = productDTO.getCategories().stream().map(CategoryDTO::getId).toList();
        when(this.categoryRepository.findAll()).thenReturn(List.of(category));
        when(this.markRepository.findById(productDTO.getMark().getId())).thenReturn(Optional.empty());
        
        // Act / Asert
        assertThrows(IllegalArgumentException.class, () -> this.productService.saveProduct(productDTO));

        verify(this.categoryRepository, times(1)).findAll();
        verify(this.markRepository, times(1)).findById(productDTO.getMark().getId());
        verify(this.productRepository, times(0)).save(any(Product.class));
    }
    
    @Test
    @DisplayName("Intenta Registrar un nuevo producto con una categoría que no existe y lanza una excepción")
    public void testSaveANewProductButCategoryNotExistsAndThrowsException(){
        // Arrange
        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Computación", true);
        categoriesDTO.add(categoryDTO);
        Category category = new Category(1L, "Computación", true);
        categories.add(category);

        MarkDTO markDTO = new MarkDTO(1L, "Lenovo", true);
        Mark mark = new Mark(1L, "Asus", true);
        
        ProductDTO productDTO = new ProductDTO(
            null, 
            markDTO,
            categoriesDTO, 
            "Notebook Lenovo", 
            "Notebook Lenovo IdeaPad 310", 
            12, 
            1500, 
            650000,
            800000
        );

        Product product = new Product(
            1L, 
            mark,
            categories, 
            "Notebook Lenovo", 
            "Notebook Lenovo IdeaPad 310", 
            12, 
            1500, 
            650000,
            800000
        );

        List<Long> categoryIds = productDTO.getCategories().stream().map(CategoryDTO::getId).toList();
        when(this.categoryRepository.findAll()).thenReturn(new ArrayList<>());
        when(this.markRepository.findById(productDTO.getMark().getId())).thenReturn(Optional.of(mark));
        
        // Act / Asert
        assertThrows(IllegalArgumentException.class, () -> this.productService.saveProduct(productDTO));

        verify(this.categoryRepository, times(1)).findAll();
        verify(this.markRepository, times(1)).findById(productDTO.getMark().getId());
        verify(this.productRepository, times(0)).save(any(Product.class));
    }

    @Test
    @DisplayName("Intenta registrar un producto con una lista de categorías con elemtos nulos y lanza una excepción controlada")
    public void testSaveANewProductButCategoriesContainNullElementsAndThrowsException(){
        // Arrange
        //CategoryDTO categoryDTO = new CategoryDTO(1L, "Computación", true);
        MarkDTO markDTO = new MarkDTO(1L, "Lenovo", true);
        Mark mark = new Mark(1L, "Lenovo", true);

        ProductDTO productDTO = new ProductDTO(
            null,
            markDTO,
            null,
            "Notebook Lenovo",
            "Notebook Lenovo IdeaPad 310",
            12,
            1500,
            650000,
            800000
        );
        //productDTO.setCategories(null);

        when(this.markRepository.findById(productDTO.getMark().getId())).thenReturn(Optional.of(mark));

        // Act / Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> this.productService.saveProduct(productDTO));
        assertEquals("Category not exist", exception.getMessage());

        verify(this.categoryRepository, times(0)).findAll();
        verify(this.markRepository, times(1)).findById(productDTO.getMark().getId());
        verify(this.productRepository, times(0)).save(any(Product.class));
    }

    @Test
    @DisplayName("Intenta registrar un producto con categorías nulas y lanza una excepción controlada")
    public void testSaveANewProductButCategoriesAreNullAndThrowsException(){
        // Arrange
        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Computación", true);
        categoriesDTO.add(categoryDTO);
        MarkDTO markDTO = new MarkDTO(1L, "Lenovo", true);
        Mark mark = new Mark(1L, "Lenovo", true);

        ProductDTO productDTO = new ProductDTO(
            null,
            markDTO,
            categoriesDTO,
            "Notebook Lenovo",
            "Notebook Lenovo IdeaPad 310",
            12,
            1500,
            650000,
            800000
        );
        productDTO.setCategories(null);

        when(this.markRepository.findById(productDTO.getMark().getId())).thenReturn(Optional.of(mark));

        // Act / Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> this.productService.saveProduct(productDTO));
        assertEquals("Category not exist", exception.getMessage());

        verify(this.categoryRepository, never()).findAll();
        verify(this.markRepository, times(1)).findById(productDTO.getMark().getId());
        verify(this.productRepository, times(0)).save(any(Product.class));
    }

    @Test
    @DisplayName("Intenta registrar un producto con una lista de categorías vacia y lanza una excepción controlada")
    public void testSaveANewProductButCategoriesAreEmptyListAndThrowsException(){
        // Arrange
        MarkDTO markDTO = new MarkDTO(1L, "Lenovo", true);
        Mark mark = new Mark(1L, "Lenovo", true);

        ProductDTO productDTO = new ProductDTO(
            null,
            markDTO,
            null,
            "Notebook Lenovo",
            "Notebook Lenovo IdeaPad 310",
            12,
            1500,
            650000,
            800000
        );
        productDTO.setCategories(List.of());

        when(this.markRepository.findById(productDTO.getMark().getId())).thenReturn(Optional.of(mark));

        // Act / Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> this.productService.saveProduct(productDTO));
        assertEquals("Category not exist", exception.getMessage());

        verify(this.categoryRepository, never()).findAll();
        verify(this.markRepository, times(1)).findById(productDTO.getMark().getId());
        verify(this.productRepository, times(0)).save(any(Product.class));
    }

    @Test
    @DisplayName("Actualiza un producto con todos sus atributos válidos y retorna el producto actualizado")
    public void testUpdateProduct(){
        // Arrange
        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        CategoryDTO catDTO = new CategoryDTO();
        catDTO.setId(1L);
        catDTO.setName("Computación");
        catDTO.setActive(true);
        categoriesDTO.add(catDTO);

        Category cat = new Category();
        cat.setId(1L);
        cat.setName("Computación");
        cat.setActive(true);
        categories.add(cat);

        MarkDTO markDTO = new MarkDTO(1L, "Lenovo", true);
        Mark mark = new Mark(1L, "Lenovo", true);
        
        ProductDTO productDTO = new ProductDTO(
                1L, 
            markDTO,
            categoriesDTO, 
            "Notebook Lenovo", 
            "Notebook Lenovo IdeaPad 310", 
            12, 
            1500, 
            650000,
            800000
        );

        Product product = new Product(
            1L, 
            mark,
            categories, 
            "Notebook Asus", 
            "Notebook Asus Vivobook", 
            12, 
            1500, 
            650000,
            800000
        );

        List<Long> categoryIds = productDTO.getCategories().stream().map(CategoryDTO::getId).toList();
        when(this.categoryRepository.findAll()).thenReturn(categories);
        when(this.markRepository.findById(anyLong())).thenReturn(Optional.of(mark));        
        when(this.productRepository.save(any(Product.class))).thenReturn(product);
        
        // Act
        ProductDTO result = this.productService.saveProduct(productDTO);

        // Assert
        assertNotNull(result);
        assertNotEquals(productDTO.getName(), result.getName());
        assertNotEquals(productDTO.getDescription(), result.getDescription());
        verify(this.categoryRepository, times(1)).findAll();
        verify(this.markRepository, times(1)).findById(productDTO.getMark().getId());
        verify(this.productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Obtiene un producto por su ID y retorna el producto correspondiente")
    public void testGetProductById(){
        // Arrange
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Computación", true));
        Mark mark = new Mark(1L, "Lenovo", true);
        
        Product product = new Product(
            1L, 
            mark,
            categories, 
            "Notebook Lenovo", 
            "Notebook Lenovo IdeaPad 310", 
            12, 
            1500, 
            650000,
            800000
        );

        when(this.productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        
        // Act
        ProductDTO result = this.productService.getProductById(product.getId());

        // Assert
        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getDescription(), result.getDescription());
    }

    @Test
    @DisplayName("Busca un producto inexistente por su ID y retorna nulo")
    public void testGetProductByIdAndReturnsNull(){
        // Arrange
        when(this.productRepository.findById(anyLong())).thenReturn(Optional.empty());
        
        // Act
        ProductDTO result = this.productService.getProductById(999L);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Elimina un producto por su ID y verifica que se haya llamado al repositorio")
    public void testDeleteProductById(){
        // Arrange
        Long productId = 1L;
        when(this.productRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(this.productRepository).deleteById(productId);
        
        // Act
        this.productService.deleteProduct(productId);

        // Assert
        verify(this.productRepository, times(1)).deleteById(productId);
    }   


    @Test
    @DisplayName("Intenta eliminar un producto inexistente por su Id y Retorna una excepción")
    public void testToDeleteANonExistentProductAndThrowsException(){
        // Arrange
        Long productId = 999L;
        when(this.productRepository.existsById(anyLong())).thenReturn(false);
        doNothing().when(this.productRepository).deleteById(productId);
        
        // Act / Assert
        assertThrows(IllegalArgumentException.class, () -> this.productService.deleteProduct(productId));

        verify(this.productRepository, times(0)).deleteById(productId);
    }

}