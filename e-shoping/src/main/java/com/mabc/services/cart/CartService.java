package com.mabc.services.cart;

import com.mabc.dto.CartDTO;
import com.mabc.dto.ProductDTO;
import java.util.List;

/**
 * Interfaz que define las operaciones disponibles para la gestión del carrito de compras.
 * Proporciona métodos para crear un nuevo carrito y agregar productos al mismo.
 */
public interface CartService {

    /**
     * Crea un nuevo carrito de compras vacío.
     * Asigna un identificador único al carrito basado en el último carrito registrado.
     *
     * @return Un objeto CartDTO representando el nuevo carrito creado
     */
    public CartDTO newCart();

    /**
     * Agrega un producto al carrito de compras especificado.
     * Valida la existencia del producto y la disponibilidad de stock antes de agregarlo.
     *
     * @param id Identificador del carrito al que se agregará el producto
     * @param productDTO Objeto DTO con los datos del producto a agregar
     * @return El carrito actualizado con el producto agregado y el subtotal calculado
     * @throws RuntimeException Si el carrito no existe, el producto no es válido o no hay stock suficiente
     */
    public CartDTO addItem(Long id, ProductDTO productDTO);

}