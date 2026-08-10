package com.mabc;

import com.mabc.application.usecase.AddItemToCartUseCase;
import com.mabc.application.usecase.CreateCartUseCase;
import com.mabc.application.usecase.CreateProductUseCase;
import com.mabc.application.usecase.SaveCategoryUseCase;
import com.mabc.application.usecase.SaveMarkUseCase;
import com.mabc.domain.entity.Cart;
import com.mabc.domain.entity.Category;
import com.mabc.domain.entity.Mark;
import com.mabc.domain.entity.Product;
import com.mabc.infrastructure.persistence.inmemory.InMemoryRepositories.InMemoryCartRepository;
import com.mabc.infrastructure.persistence.inmemory.InMemoryRepositories.InMemoryCategoryRepository;
import com.mabc.infrastructure.persistence.inmemory.InMemoryRepositories.InMemoryMarkRepository;
import com.mabc.infrastructure.persistence.inmemory.InMemoryRepositories.InMemoryProductRepository;

import java.util.List;

public class App {

    public static void main(String[] args) {
        InMemoryMarkRepository markRepository = new InMemoryMarkRepository();
        InMemoryCategoryRepository categoryRepository = new InMemoryCategoryRepository();
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        InMemoryCartRepository cartRepository = new InMemoryCartRepository();

        SaveMarkUseCase saveMarkUseCase = new SaveMarkUseCase(markRepository);
        SaveCategoryUseCase saveCategoryUseCase = new SaveCategoryUseCase(categoryRepository);
        CreateProductUseCase createProductUseCase =
                new CreateProductUseCase(productRepository, categoryRepository, markRepository);
        CreateCartUseCase createCartUseCase = new CreateCartUseCase(cartRepository);
        AddItemToCartUseCase addItemToCartUseCase =
                new AddItemToCartUseCase(cartRepository, productRepository);

        Mark mark = saveMarkUseCase.execute(null, "Lenovo", true);
        Category category = saveCategoryUseCase.execute(null, "Computacion", true);
        Product product = createProductUseCase.execute(
                null, mark.getId(), List.of(category.getId()),
                "Notebook Lenovo", "Notebook Lenovo IdeaPad 310", 12, 1500, 650000, 800000);
        Cart cart = createCartUseCase.execute();
        cart = addItemToCartUseCase.execute(cart.getId(), product.getId(), 1);

        System.out.println("Carrito " + cart.getId() + " creado con "
                + cart.getItems().size() + " item(s).");
        System.out.println("Subtotal: $" + cart.getSubTotal());
    }
}
