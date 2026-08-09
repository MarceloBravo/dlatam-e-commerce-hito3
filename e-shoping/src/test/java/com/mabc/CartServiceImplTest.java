
package com.mabc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mabc.dto.CartDTO;
import com.mabc.dto.CartItemDTO;
import com.mabc.dto.ProductDTO;
import com.mabc.dto.MarkDTO;
import com.mabc.dto.CategoryDTO;
import com.mabc.entities.Cart;
import com.mabc.entities.CartItem;
import com.mabc.entities.Product;
import com.mabc.entities.Mark;
import com.mabc.entities.Category;
import com.mabc.repositories.CartRepository;
import com.mabc.repositories.ProductRepository;
import com.mabc.services.cart.CartServiceImpl;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;


public class CartServiceImplTest {

    private CartServiceImpl service;
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private Product product;
    private Mark mark;
    private MarkDTO markDTO;
    private Category category;
    private CategoryDTO categoryDTO;
    private Cart cart;
    private List<CartItem> products = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        cartRepository = mock(CartRepository.class);
        productRepository = mock(ProductRepository.class);
        service = new CartServiceImpl(cartRepository, productRepository);
        
        Category category = new Category(1L, "Computación", true);
        this.mark = new Mark(1L, "Lenovo", true);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        this.product = new Product( 1L, this.mark, categories, "Notebook Lenovo", "Notebook Lenovo IdeaPad 310", 12, 1500, 650000, 800000);
        CartItem cartItem = new CartItem(1L, this.product, 1, 100.0);
        this.products.add(cartItem);
    }

    @Test
    @DisplayName("Debe crear un carrito vacio con un id una unidad mayor al Id del último carrito creado")
    public void testShouldCreateAnEmtyCartWithNextIdNumber(){
        // Arrange
        Cart cart = new Cart(1000L, this.products);
        when(cartRepository.getLastCart()).thenReturn(Optional.of(cart));
        
        // Act
        CartDTO newCartDTO = this.service.newCart();

        // Assert
        assertEquals(newCartDTO.getProducts().size(), 0);
        assertEquals(newCartDTO.getSubTotal(), 0.0);
        assertNotNull(newCartDTO.getCreationDate());
        assertEquals(newCartDTO.getId(), cart.getId() + 1 );
    }

    @Test
    @DisplayName("Debe crear un carrito vacio con  con un id igual a 1 al no existir carritos creados previamente")
    public void testShouldCreateAnEmtyCartWithFirstIdNumberEqualsTo1IfNotExistsPreviousCartCreated(){
        // Arrange
        when(cartRepository.getLastCart()).thenReturn(Optional.empty());
        
        // Act
        CartDTO newCartDTO = this.service.newCart();

        // Assert
        assertEquals(newCartDTO.getProducts().size(), 0);
        assertEquals(newCartDTO.getSubTotal(), 0.0);
        assertNotNull(newCartDTO.getCreationDate());
        assertEquals(newCartDTO.getId(), 1 );
    }

    @Test
    @DisplayName("Agrega un elemento al carrito debe actualizarse la cantidad de productos y el subTotal del carrito")
    public void testAddAnElementToCartAndUpdateCantityAndSubTotalOfCart(){
        // Arrange
        List<CartItemDTO> productsDTO = new ArrayList<>();
        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Computación");
        categoryDTO.setActive(true);
        categoriesDTO.add(categoryDTO);

        Category category = new Category();
        category.setId(1L);
        category.setName("Computación");
        category.setActive(true);
        categories.add(category);

        MarkDTO markDTO = new MarkDTO();
        markDTO.setId(1L);
        markDTO.setName("Lenovo");
        markDTO.setActive(true);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(2L);
        productDTO.setMark(markDTO);
        productDTO.setCategories(categoriesDTO);
        productDTO.setName("Notebook Lenovo");
        productDTO.setDescription("Notebook Lenovo IdeaPad 310");
        productDTO.setStock(12);
        productDTO.setWeight(1500);
        productDTO.setPriceCost(650000);
        productDTO.setPriceSale(800000);

        Product product = new Product();
        product.setId(2L);
        product.setMark(this.mark);
        product.setCategories(categories);
        product.setName("Notebook Lenovo");
        product.setDescription("Notebook Lenovo IdeaPad 310");
        product.setStock(12);
        product.setWeight(1500);
        product.setPriceCost(650000);
        product.setPriceSale(800000);

        Cart cart = new Cart(1L, this.products);

        CartItemDTO cartItemDTO = new CartItemDTO(1L, productDTO, 1, 100.0);
        productsDTO.add(cartItemDTO);

        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(1L);
        cartDTO.setProducts(productsDTO);

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(this.product);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // Capture original size before Act
        int originalSize = cart.getProducts().size();

        // Act
        CartDTO savedCartDTO = this.service.addItem(1L, productDTO);

        // Assert
        assertNotNull(savedCartDTO);
        assertEquals(originalSize + 1, savedCartDTO.getProducts().size());
        //assertEquals(savedCartDTO.getSubTotal(), cart.getSubTotal() + product.getPriceSale());
    }

    
    @Test
    @DisplayName("Intenta agregar un elemento al carrito pero el producto no existe en la base de datos")
    public void testAddAnElementToCartWheProductNotExistsReturnRuntimeException(){
        // Arrange
        List<CartItemDTO> productsDTO = new ArrayList<>();
        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Computación");
        categoryDTO.setActive(true);
        categoriesDTO.add(categoryDTO);

        Category category = new Category();
        category.setId(1L);
        category.setName("Computación");
        category.setActive(true);
        categories.add(category);

        MarkDTO markDTO = new MarkDTO();
        markDTO.setId(1L);
        markDTO.setName("Lenovo");
        markDTO.setActive(true);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(99999L);
        productDTO.setMark(markDTO);
        productDTO.setCategories(categoriesDTO);
        productDTO.setName("Notebook Lenovo");
        productDTO.setDescription("Notebook Lenovo IdeaPad 310");
        productDTO.setStock(12);
        productDTO.setWeight(1500);
        productDTO.setPriceCost(650000);
        productDTO.setPriceSale(800000);

        Product product = new Product();
        product.setId(99999L);
        product.setMark(this.mark);
        product.setCategories(categories);
        product.setName("Notebook Lenovo");
        product.setDescription("Notebook Lenovo IdeaPad 310");
        product.setStock(12);
        product.setWeight(1500);
        product.setPriceCost(650000);
        product.setPriceSale(800000);

        Cart cart = new Cart(1L, this.products);

        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(1L);
        cartItemDTO.setProduct(productDTO);
        cartItemDTO.setCant(1);
        cartItemDTO.setSubTotal(100.0);
        productsDTO.add(cartItemDTO);

        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(1L);
        cartDTO.setProducts(productsDTO);

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(this.product);

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

         // Act / Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> this.service.addItem(1L, productDTO));

        // Assert
        verify(this.productRepository, times(1)).findById(product.getId());
        verify(this.cartRepository, times(0)).save(cart);
        verify(this.productRepository, times(0)).findById(1L);
    }
    

    @Test
    @DisplayName("Intenta agregar un elemento al carrito pero el carrito no existe en la base de datos")
    public void testAddAnElementToCartWhenCartNotExistsReturnRuntimeException(){
        // Arrange
        List<CartItemDTO> productsDTO = new ArrayList<>();
        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Computación");
        categoryDTO.setActive(true);
        categoriesDTO.add(categoryDTO);

        Category category = new Category();
        category.setId(1L);
        category.setName("Computación");
        category.setActive(true);
        categories.add(category);

        MarkDTO markDTO = new MarkDTO();
        markDTO.setId(1L);
        markDTO.setName("Lenovo");
        markDTO.setActive(true);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(99999L);
        productDTO.setMark(markDTO);
        productDTO.setCategories(categoriesDTO);
        productDTO.setName("Notebook Lenovo");
        productDTO.setDescription("Notebook Lenovo IdeaPad 310");
        productDTO.setStock(12);
        productDTO.setWeight(1500);
        productDTO.setPriceCost(650000);
        productDTO.setPriceSale(800000);

        Product product = new Product();
        product.setId(99999L);
        product.setMark(this.mark);
        product.setCategories(categories);
        product.setName("Notebook Lenovo");
        product.setDescription("Notebook Lenovo IdeaPad 310");
        product.setStock(12);
        product.setWeight(1500);
        product.setPriceCost(650000);
        product.setPriceSale(800000);

        Cart cart = new Cart(99999L, this.products);

        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(1L);
        cartItemDTO.setProduct(productDTO);
        cartItemDTO.setCant(1);
        cartItemDTO.setSubTotal(100.0);
        productsDTO.add(cartItemDTO);

        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(1L);
        cartDTO.setProducts(productsDTO);

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(this.product);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(cartRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

         // Act / Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> this.service.addItem(1L, productDTO));

        // Assert
        verify(this.productRepository, times(1)).findById(product.getId());
        verify(this.cartRepository, times(0)).save(cart);
    }
    

    @Test
    @DisplayName("Intenta agregar un elemento al carrito pero el pructo no tiene stock suficiente")
    public void testAddAnElementToCartButProductnotHaveSufficientStockThenReturnRuntimeException(){
        // Arrange
        List<CartItemDTO> productsDTO = new ArrayList<>();
        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Computación");
        categoryDTO.setActive(true);
        categoriesDTO.add(categoryDTO);

        Category category = new Category();
        category.setId(1L);
        category.setName("Computación");
        category.setActive(true);
        categories.add(category);

        MarkDTO markDTO = new MarkDTO();
        markDTO.setId(1L);
        markDTO.setName("Lenovo");
        markDTO.setActive(true);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(99999L);
        productDTO.setMark(markDTO);
        productDTO.setCategories(categoriesDTO);
        productDTO.setName("Notebook Lenovo");
        productDTO.setDescription("Notebook Lenovo IdeaPad 310");
        productDTO.setStock(12000);
        productDTO.setWeight(1500);
        productDTO.setPriceCost(650000);
        productDTO.setPriceSale(800000);

        Product product = new Product();
        product.setId(99999L);
        product.setMark(this.mark);
        product.setCategories(categories);
        product.setName("Notebook Lenovo");
        product.setDescription("Notebook Lenovo IdeaPad 310");
        product.setStock(12);
        product.setWeight(1500);
        product.setPriceCost(650000);
        product.setPriceSale(800000);

        Cart cart = new Cart(1L, this.products);

        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(1L);
        cartItemDTO.setProduct(productDTO);
        cartItemDTO.setCant(1);
        cartItemDTO.setSubTotal(100.0);
        productsDTO.add(cartItemDTO);

        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(1L);
        cartDTO.setProducts(productsDTO);

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(this.product);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));

         // Act / Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> this.service.addItem(1L, productDTO));

        // Assert
        verify(this.productRepository, times(1)).findById(product.getId());
        verify(this.cartRepository, times(0)).findById(cart.getId());
        verify(this.cartRepository, times(0)).save(cart);
    }
    

    @Test
    @DisplayName("Intenta agregar un elemento al carrito pero falla al grabar los datos del carrito y genera un error en tiempo de ejecución")
    public void testAddAnElementToCartButFailsWhenTrySaveCartThenReturnRuntimeException(){
        // Arrange
        List<CartItemDTO> productsDTO = new ArrayList<>();
        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Computación");
        categoryDTO.setActive(true);
        categoriesDTO.add(categoryDTO);

        Category category = new Category();
        category.setId(1L);
        category.setName("Computación");
        category.setActive(true);
        categories.add(category);

        MarkDTO markDTO = new MarkDTO();
        markDTO.setId(1L);
        markDTO.setName("Lenovo");
        markDTO.setActive(true);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(99999L);
        productDTO.setMark(markDTO);
        productDTO.setCategories(categoriesDTO);
        productDTO.setName("Notebook Lenovo");
        productDTO.setDescription("Notebook Lenovo IdeaPad 310");
        productDTO.setStock(1);
        productDTO.setWeight(1500);
        productDTO.setPriceCost(650000);
        productDTO.setPriceSale(800000);

        Product product = new Product();
        product.setId(99999L);
        product.setMark(this.mark);
        product.setCategories(categories);
        product.setName("Notebook Lenovo");
        product.setDescription("Notebook Lenovo IdeaPad 310");
        product.setStock(12);
        product.setWeight(1500);
        product.setPriceCost(650000);
        product.setPriceSale(800000);

        Cart cart = new Cart(1L, this.products);

        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(1L);
        cartItemDTO.setProduct(productDTO);
        cartItemDTO.setCant(1);
        cartItemDTO.setSubTotal(100.0);
        productsDTO.add(cartItemDTO);

        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(1L);
        cartDTO.setProducts(productsDTO);

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(this.product);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(null);

         // Act / Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> this.service.addItem(1L, productDTO));

        // Assert
        verify(this.productRepository, times(1)).findById(product.getId());
        verify(this.cartRepository, times(1)).findById(cart.getId());
        verify(this.cartRepository, times(1)).save(cart);
    }
}