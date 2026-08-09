package com.mabc.application.usecase;

import com.mabc.domain.entity.Cart;
import com.mabc.domain.repository.CartRepository;

public class CreateCartUseCase {

    private final CartRepository cartRepository;

    public CreateCartUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart execute() {
        Long nextId = cartRepository.findLast()
                .map(cart -> cart.getId() + 1)
                .orElse(1L);
        Cart cart = new Cart(nextId);
        return cartRepository.save(cart);
    }
}
