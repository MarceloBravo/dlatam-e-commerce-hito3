package com.mabc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mabc.entities.Cart;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> getLastCart();

    Cart save(Cart cart);

}