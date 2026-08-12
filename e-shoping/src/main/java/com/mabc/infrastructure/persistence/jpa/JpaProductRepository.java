package com.mabc.infrastructure.persistence.jpa;

import com.mabc.domain.entity.Product;
import com.mabc.domain.repository.ProductRepository;
import com.mabc.infrastructure.persistence.entity.ProductEntity;
import com.mabc.infrastructure.persistence.spring.ProductJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de {@link ProductRepository} basada en JPA/Spring Data.
 *
 * <p>Convierte entre las entidades de dominio {@link Product} y las
 * entidades de persistencia {@link ProductEntity} delegando el acceso a la
 * base de datos en {@link ProductJpaRepository}.
 */
@Repository
public class JpaProductRepository implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    /**
     * Crea el repositorio JPA de productos.
     *
     * @param productJpaRepository repositorio Spring Data de entidades de producto.
     */
    public JpaProductRepository(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id).map(ProductEntityMapper::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll().stream().map(ProductEntityMapper::toDomain).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product save(Product product) {
        ProductEntity saved = productJpaRepository.save(ProductEntityMapper.toEntity(product));
        return ProductEntityMapper.toDomain(saved);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(Long id) {
        productJpaRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsById(Long id) {
        return productJpaRepository.existsById(id);
    }
}
