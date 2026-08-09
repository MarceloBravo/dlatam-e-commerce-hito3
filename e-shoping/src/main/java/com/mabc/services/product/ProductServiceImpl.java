package com.mabc.services.product;

import com.mabc.dto.ProductDTO;
import com.mabc.dto.MarkDTO;
import com.mabc.dto.CategoryDTO;
import com.mabc.entities.Category;
import com.mabc.entities.Mark;
import com.mabc.entities.Product;

import com.mabc.repositories.ProductRepository;
import com.mabc.repositories.CategoryRepository;
import com.mabc.repositories.MarkRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;


/**
 * Implementación de la interfaz {@link ProductService} que proporciona la lógica
 * de negocio para la gestión de productos. Esta clase maneja las operaciones CRUD
 * de productos, incluyendo validaciones de marca y categorías asociadas.
 */
public class ProductServiceImpl implements ProductService {
    
    /** Repositorio para el acceso a datos de productos */
    private final ProductRepository productRepository;
    
    /** Repositorio para el acceso a datos de categorías */
    private final CategoryRepository categoryRepository;
    
    /** Repositorio para el acceso a datos de marcas */
    private final MarkRepository markRepository;

    /**
     * Constructor de la clase ProductServiceImpl.
     * Inicializa las dependencias necesarias para el acceso a datos.
     *
     * @param productRepository Repositorio de productos
     * @param categoryRepository Repositorio de categorías
     * @param markRepository Repositorio de marcas
     */
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, MarkRepository markRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.markRepository = markRepository;
    }

    /**
     * Guarda o actualiza un producto en el sistema. Valida que la marca y las
     * categorías existan antes de realizar la operación.
     *
     * @param productDTO Objeto DTO con los datos del producto
     * @return El producto guardado convertido a DTO
     * @throws IllegalArgumentException Si la marca o alguna categoría no existe
     */
    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Optional<Mark> mark = markRepository.findById(productDTO.getMark().getId());
        Boolean isCategoryExists = this.isCategoryExists(productDTO.getCategories());

        if(!mark.isPresent()){
            throw new IllegalArgumentException("Mark not exist");
        }
        
        if(!isCategoryExists){
            throw new IllegalArgumentException("Category not exist");
        }

        Product product = this.productRepository.findById(productDTO.getId()).orElse(new Product());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPriceCost(productDTO.getPriceCost());
        product.setPriceSale(productDTO.getPriceSale());
        List<Category> categories = categoryRepository.findAllById(
            productDTO.getCategories().stream().map(CategoryDTO::getId).toList()
        );
        product.setCategories(categories);
        product.setMark(mark.get());

        Product savedProduct = productRepository.save(product);
        return productToDTO(savedProduct);
    }

    /**
     * Verifica si todas las categorías proporcionadas existen en la base de datos.
     *
     * @param categoryDTOs Lista de categorías a validar
     * @return true si todas las categorías existen, false si la lista es nula o vacía,
     *         o si alguna categoría no existe
     */
    private Boolean isCategoryExists(List<CategoryDTO> categoryDTOs) throws IllegalArgumentException {
        if (categoryDTOs == null || categoryDTOs.isEmpty()) {
            return false;
        }

        List<Category> categories = categoryRepository.findAll();
        return categories.stream().anyMatch(category -> categoryDTOs.stream().anyMatch(categoryDTO -> category.getId().equals(categoryDTO.getId())));

    }

    /**
     * Busca un producto por su identificador único en la base de datos.
     *
     * @param id Identificador del producto a buscar
     * @return El producto encontrado convertido a DTO, o null si no existe
     */
    @Override
    public ProductDTO getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return productToDTO(product.get());
        }
        return null;
    }

    /**
     * Elimina un producto del sistema. Verifica que el producto exista antes de eliminarlo.
     *
     * @param id Identificador del producto a eliminar
     * @throws IllegalArgumentException Si el producto con el ID especificado no existe
     */
    @Override
    public void deleteProduct(Long id) {
        if(!this.productRepository.existsById(id)){
            throw new IllegalArgumentException("Product not exist");
        }
        productRepository.deleteById(id);
    }   

    /**
     * Convierte una entidad Product a su correspondiente DTO.
     * Mapea todas las propiedades de la entidad, incluyendo la marca y las categorías asociadas.
     *
     * @param product Entidad Product a convertir
     * @return Objeto ProductDTO con los datos de la entidad
     */
    private ProductDTO productToDTO(Product product){
         List<CategoryDTO> categoriesDTO = new ArrayList<>();
        product.getCategories().forEach(cat -> {
            categoriesDTO.add(new CategoryDTO(cat.getId(), cat.getName(), cat.getActive()));
        });

        return new ProductDTO(
            product.getId(),
            new MarkDTO(product.getMark().getId(), product.getMark().getName(), product.getMark().getActive()),
            categoriesDTO,
            product.getName(),
            product.getDescription(),
            product.getStock(),
            product.getWeight(),
            product.getPriceCost(),
            product.getPriceSale()
        );        
    }
}