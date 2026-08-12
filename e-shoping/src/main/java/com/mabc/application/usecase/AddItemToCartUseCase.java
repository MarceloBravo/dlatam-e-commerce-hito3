package com.mabc.application.usecase;

import com.mabc.domain.entity.Cart;
import com.mabc.domain.entity.Product;
import com.mabc.domain.repository.CartRepository;
import com.mabc.domain.repository.ProductRepository;
import com.mabc.domain.valueobject.Quantity;

/**
 * Caso de uso que agrega un producto al carrito de compras.
 *
 * <p>Valida que el carrito y el producto existan y delega en la entidad
 * {@link Cart} la lógica de agregar el ítem con la cantidad indicada.
 */
public class AddItemToCartUseCase {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    /**
     * Crea el caso de uso con los repositorios necesarios.
     *
     * @param cartRepository    repositorio de carritos de compras.
     * @param productRepository repositorio de productos.
     */
    public AddItemToCartUseCase(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    /**
     * Agrega un producto a un carrito con la cantidad indicada.
     *
     * @param cartId    identificador del carrito al que se agregará el ítem.
     * @param productId identificador del producto que se agregará.
     * @param quantity  cantidad de unidades del producto a agregar.
     * @return el carrito actualizado y persistido.
     * @throws IllegalStateException si el carrito o el producto no existen,
     *                               o si no hay stock suficiente.
     */
    public Cart execute(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalStateException("El carrito no existe o no es válido."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("El producto no existe o no es válido."));

        cart.addItem(product, new Quantity(quantity));
        return cartRepository.save(cart);
    }
}
