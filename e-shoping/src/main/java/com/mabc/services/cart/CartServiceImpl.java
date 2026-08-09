package com.mabc.services.cart;

import com.mabc.dto.CategoryDTO;
import com.mabc.dto.CartDTO;
import com.mabc.dto.MarkDTO;
import com.mabc.dto.CartItemDTO;
import com.mabc.dto.ProductDTO;
import com.mabc.entities.Cart;
import com.mabc.entities.CartItem;
import com.mabc.entities.Category;
import com.mabc.entities.Mark;
import com.mabc.entities.Product;
import com.mabc.repositories.CartRepository;
import com.mabc.repositories.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;


/**
 * Implementación de la interfaz {@link CartService} que proporciona la lógica
 * de negocio para la gestión del carrito de compras. Esta clase maneja la
 * creación de carritos, agregado de productos, validación de stock y cálculo
 * de subtotales.
 */
public class CartServiceImpl implements CartService{

    /** Repositorio para el acceso a datos de carritos de compras */
    private final CartRepository cartRepository;
    
    /** Repositorio para el acceso a datos de productos */
    private final ProductRepository productRepository;

    /**
     * Constructor de la clase CartServiceImpl.
     * Inicializa los repositorios necesarios para el acceso a datos de carritos y productos.
     *
     * @param cartRepository Repositorio de carritos de compras
     * @param productRepository Repositorio de productos
     */
    public CartServiceImpl(
            CartRepository cartRepository, 
            ProductRepository productRepository
    ){
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    /**
     * Crea un nuevo carrito de compras vacío con un ID secuencial
     * basado en el último carrito registrado en el sistema.
     *
     * @return Un nuevo CartDTO con el ID asignado
     */
    @Override
    public CartDTO newCart(){
        CartDTO cartDTO = new CartDTO();
        Cart lastCart = this.cartRepository.getLastCart().orElse(null);
        Long id = lastCart != null ? lastCart.getId() + 1 : 1;
        cartDTO.setId(id);
        return cartDTO;       
    }

    /**
     * Agrega un producto al carrito de compras. Valida la existencia y stock
     * del producto, lo agrega al carrito y recalcula el subtotal.
     *
     * @param id Identificador del carrito
     * @param productDTO Objeto DTO con los datos del producto a agregar
     * @return El carrito actualizado con el nuevo producto y subtotal
     * @throws RuntimeException Si el carrito no existe, el producto no es válido
     *         o no hay stock suficiente
     */
    @Override
    public CartDTO addItem(Long id, ProductDTO productDTO){
        Integer stock  = this.validateProduct(productDTO);

        Cart cart = this.cartRepository.findById(id).orElse(null);
        if(cart == null){
            throw new RuntimeException("Cart not valid or not exists");
        }
        cart = addItemToCart(cart, productDTO);
        Double subTotal = this.calcSubTotal(cart);
        cart.setSubTotal(subTotal);

        Cart savedCart = this.cartRepository.save(cart);
        if(savedCart == null){
            throw new RuntimeException("Something went wrong while recording the shopping cart");
        }
        CartDTO cartDTO = cartToDTO(savedCart);
        cartDTO.setSubTotal(subTotal);

        return cartDTO;
    }

    /**
     * Valida que el producto proporcionado exista en la base de datos
     * y que tenga stock disponible.
     *
     * @param productDTO Objeto DTO con los datos del producto a validar
     * @return El stock disponible del producto
     * @throws RuntimeException Si el producto no existe o no tiene stock suficiente
     */
    private Integer validateProduct(ProductDTO productDTO){
        Product product = productRepository.findById(productDTO.getId()).orElse(null);
        if(product == null){
            throw new RuntimeException("Product ''" + productDTO.getName() + "'' not valid or not exists");
        }
        this.validateStock(productDTO, product);
        return product.getStock();
    }

    /**
     * Valida que la cantidad solicitada del producto no exceda el stock disponible.
     *
     * @param productDTO Objeto DTO con los datos del producto solicitado
     * @param product Entidad del producto con el stock actual
     * @throws RuntimeException Si la cantidad solicitada supera el stock disponible
     */
    private void validateStock(ProductDTO productDTO, Product product){
        if(productDTO.getStock() > product.getStock()){
            throw new RuntimeException("Insufficient stock for " + product.getName());
        }
    }

    /**
     * Calcula el subtotal del carrito sumando el precio de venta por la cantidad
     * de cada producto en el carrito.
     *
     * @param cart Carrito de compras cuyo subtotal se desea calcular
     * @return El subtotal calculado como la suma de (stock * precioVenta) de cada item
     */
    private Double calcSubTotal(Cart cart){
        return cart.getProducts().stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getStock() * cartItem.getProduct().getPriceSale())
                .sum();
    }

    /**
     * Agrega un producto al carrito de compras. Convierte los datos del DTO a entidades
     * y crea un nuevo item de carrito con el producto y su cantidad.
     *
     * @param cart Carrito de compras al que se agregará el producto
     * @param productDTO Objeto DTO con los datos del producto a agregar
     * @return El carrito actualizado con el nuevo producto agregado
     */
    private Cart addItemToCart(Cart cart, ProductDTO productDTO){
        List<CartItem> cartItem = cart.getProducts();
        List<Category> categories = new ArrayList<>();
        productDTO.getCategories().forEach(c -> 
            categories.add(new Category(c.getId(), c.getName(), c.getActive()))
        );

        Mark mark = new Mark(productDTO.getMark().getId(), productDTO.getMark().getName(), productDTO.getMark().getActive());
        Product prod = new Product(productDTO.getId(), mark, categories, productDTO.getName(), productDTO.getDescription(), productDTO.getStock(), productDTO.getWeight(), productDTO.getPriceCost(), productDTO.getPriceSale());
        CartItem item = new CartItem();
        item.setProduct(prod);
        item.setCant(prod.getStock());
        item.setSubTotal(prod.getPriceSale() * prod.getStock());
        cartItem.add(item);
        cart.setProducts(cartItem);
        return cart;
    }

    /**
     * Convierte una entidad Cart a su correspondiente DTO.
     * Mapea todos los items del carrito, incluyendo sus productos, marcas,
     * categorías y subtotales individuales.
     *
     * @param cart Entidad Cart a convertir
     * @return Objeto CartDTO con todos los datos del carrito y sus items
     */
    private CartDTO cartToDTO(Cart cart){
        List<CartItemDTO> cartItemsDTO = new ArrayList<>();
        List<CartItem> cartItem = cart.getProducts();
        cartItem.forEach(item -> {
            List<CategoryDTO> categories = new ArrayList<>();
            item.getProduct().getCategories().forEach(category -> {
                categories.add(new CategoryDTO(category.getId(), category.getName(), category.getActive()));
            });
            Product prod = item.getProduct();
            MarkDTO markDTO = new MarkDTO(prod.getMark().getId(), prod.getMark().getName(), prod.getMark().getActive());
            ProductDTO prodDTO = new ProductDTO(prod.getId(), markDTO, categories, prod.getName(), prod.getDescription(), prod.getStock(), prod.getWeight(), prod.getPriceCost(), prod.getPriceSale());
            CartItemDTO itemDTO = new CartItemDTO();
            itemDTO.setProduct(prodDTO); 
            itemDTO.setCant(prodDTO.getStock()); 
            itemDTO.setSubTotal(prodDTO.getStock() * prodDTO.getPriceSale());
            cartItemsDTO.add(itemDTO);
        });
        CartDTO cartDTO = new CartDTO(cart.getId(), cartItemsDTO);
        return cartDTO;
    }

}