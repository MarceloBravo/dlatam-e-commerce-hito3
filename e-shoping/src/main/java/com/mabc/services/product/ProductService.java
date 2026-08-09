package com.mabc.services.product;

import com.mabc.dto.ProductDTO;
import com.mabc.entities.Category;
import com.mabc.entities.Mark;
import com.mabc.entities.Product;

/**
 * Interfaz que define las operaciones disponibles para la gestión de productos.
 * Proporciona métodos para guardar, consultar y eliminar productos en el sistema.
 */
public interface ProductService {
    
    /**
     * Guarda un producto en el sistema. Si el producto ya existe (mediante su ID),
     * se actualizan sus datos; de lo contrario, se crea un nuevo producto.
     *
     * @param productDTO Objeto DTO que contiene los datos del producto a guardar
     * @return El objeto ProductDTO con los datos guardados, incluyendo el ID generado
     */
    ProductDTO saveProduct(ProductDTO productDTO);

    /**
     * Obtiene un producto por su identificador único.
     *
     * @param id Identificador único del producto a buscar
     * @return El objeto ProductDTO correspondiente al ID proporcionado, o null si no se encuentra
     */
    ProductDTO getProductById(Long id);

    /**
     * Elimina un producto del sistema por su identificador único.
     *
     * @param id Identificador único del producto a eliminar
     * @throws IllegalArgumentException Si el producto con el ID especificado no existe
     */
    void deleteProduct(Long id);
}