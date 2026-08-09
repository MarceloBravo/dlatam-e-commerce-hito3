package com.mabc.application.usecase;

import com.mabc.domain.entity.Cart;
import com.mabc.domain.entity.Product;
import com.mabc.domain.repository.CartRepository;
import com.mabc.domain.repository.ProductRepository;
import com.mabc.domain.valueobject.Quantity;

public class AddItemToCartUseCase {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public AddItemToCartUseCase(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart execute(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalStateException("El carrito no existe o no es válido."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("El producto no existe o no es válido."));

        cart.addItem(product, new Quantity(quantity));
        return cartRepository.save(cart);
    }
}
