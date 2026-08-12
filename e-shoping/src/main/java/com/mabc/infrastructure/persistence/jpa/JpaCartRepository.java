package com.mabc.infrastructure.persistence.jpa;

import com.mabc.domain.entity.Cart;
import com.mabc.domain.repository.CartRepository;
import com.mabc.domain.valueobject.Quantity;
import com.mabc.infrastructure.persistence.entity.CartEntity;
import com.mabc.infrastructure.persistence.entity.CartItemEntity;
import com.mabc.infrastructure.persistence.spring.CartJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de {@link CartRepository} basada en JPA/Spring Data.
 *
 * <p>Convierte entre las entidades de dominio {@link Cart} y las entidades
 * de persistencia {@link CartEntity} delegando el acceso a la base de datos
 * en {@link CartJpaRepository}.
 */
@Repository
public class JpaCartRepository implements CartRepository {

    private final CartJpaRepository cartJpaRepository;

    /**
     * Crea el repositorio JPA de carritos.
     *
     * @param cartJpaRepository repositorio Spring Data de entidades de carrito.
     */
    public JpaCartRepository(CartJpaRepository cartJpaRepository) {
        this.cartJpaRepository = cartJpaRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Cart> findById(Long id) {
        return cartJpaRepository.findById(id).map(this::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Cart> findLast() {
        return cartJpaRepository.findTopByOrderByIdDesc().map(this::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cart save(Cart cart) {
        CartEntity saved = cartJpaRepository.save(toEntity(cart));
        return toDomain(saved);
    }

    private Cart toDomain(CartEntity entity) {
        Cart cart = new Cart(entity.getId());
        if (entity.getItems() != null) {
            for (CartItemEntity itemEntity : entity.getItems()) {
                cart.addItem(
                        ProductEntityMapper.toDomain(itemEntity.getProduct()),
                        new Quantity(itemEntity.getCant()));
            }
        }
        return cart;
    }

    private CartEntity toEntity(Cart cart) {
        CartEntity entity = cartJpaRepository.findById(cart.getId()).orElseGet(CartEntity::new);
        entity.setId(cart.getId());
        entity.setCreationDate(cart.getCreationDate());
        entity.setSubTotal(cart.getSubTotal());

        List<CartItemEntity> items = cart.getItems().stream()
                .map(item -> {
                    CartItemEntity itemEntity = new CartItemEntity();
                    itemEntity.setId(item.getId());
                    itemEntity.setCart(entity);
                    itemEntity.setProduct(ProductEntityMapper.toEntity(item.getProduct()));
                    itemEntity.setCant(item.getQuantity().value());
                    itemEntity.setSubTotal(item.getSubTotal());
                    return itemEntity;
                })
                .toList();
        entity.setItems(items);
        return entity;
    }
}
