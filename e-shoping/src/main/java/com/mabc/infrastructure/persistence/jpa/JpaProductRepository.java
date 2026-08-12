package com.mabc.infrastructure.persistence.jpa;

import com.mabc.domain.entity.Product;
import com.mabc.domain.repository.ProductRepository;
import com.mabc.infrastructure.persistence.entity.ProductEntity;
import com.mabc.infrastructure.persistence.spring.ProductJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaProductRepository implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    public JpaProductRepository(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id).map(ProductEntityMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll().stream().map(ProductEntityMapper::toDomain).toList();
    }

    @Override
    public Product save(Product product) {
        ProductEntity saved = productJpaRepository.save(ProductEntityMapper.toEntity(product));
        return ProductEntityMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        productJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return productJpaRepository.existsById(id);
    }
}
