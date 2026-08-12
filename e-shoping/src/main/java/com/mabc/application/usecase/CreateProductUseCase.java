package com.mabc.application.usecase;

import com.mabc.domain.entity.Category;
import com.mabc.domain.entity.Mark;
import com.mabc.domain.entity.Product;
import com.mabc.domain.repository.CategoryRepository;
import com.mabc.domain.repository.MarkRepository;
import com.mabc.domain.repository.ProductRepository;
import com.mabc.domain.valueobject.Description;
import com.mabc.domain.valueobject.Name;
import com.mabc.domain.valueobject.Price;
import com.mabc.domain.valueobject.Stock;
import com.mabc.domain.valueobject.Weight;

import java.util.List;

public class CreateProductUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final MarkRepository markRepository;

    public CreateProductUseCase(
        ProductRepository productRepository, 
        CategoryRepository categoryRepository,
        MarkRepository markRepository
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.markRepository = markRepository;
    }

    public Product execute(
        Long id, 
        Long markId, 
        List<Long> categoryIds, 
        String name, 
        String description,
        int stock, 
        double weight, 
        double priceCost, 
        double priceSale
    ) {
        Mark mark = markRepository.findById(markId)
                .orElseThrow(() -> new IllegalArgumentException("La marca no existe."));

        List<Category> categories = categoryRepository.findAllByIds(categoryIds);
        if (categories.isEmpty() || categories.size() != categoryIds.size()) {
            throw new IllegalArgumentException("Alguna categoría no existe.");
        }

        Product product;
        if (id == null) {
            product = new Product(nextId(), mark, categories, new Name(name), new Description(description),
                    new Stock(stock), new Weight(weight), new Price(priceCost), new Price(priceSale));
        } else {
            product = productRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("El producto no existe."));
            product.rename(new Name(name));
            product.updateDescription(new Description(description));
            product.restock(new Stock(stock));
            product.updatePrices(new Price(priceCost), new Price(priceSale));
        }

        return productRepository.save(product);
    }

    private Long nextId() {
        return productRepository.findAll().stream()
                .mapToLong(Product::getId)
                .max()
                .orElse(0L) + 1;
    }
}
