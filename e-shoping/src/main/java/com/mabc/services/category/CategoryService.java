package com.mabc.services.category;

import com.mabc.dto.CategoryDTO;

/**
 * Interfaz que define las operaciones disponibles para la gestión de categorías.
 * Proporciona métodos para guardar, consultar y eliminar categorías de productos.
 */
public interface CategoryService {
    
    /**
     * Guarda una categoría en el sistema. Si la categoría ya existe (mediante su ID),
     * se actualizan sus datos; de lo contrario, se crea una nueva categoría.
     *
     * @param categoryDTO Objeto DTO que contiene los datos de la categoría a guardar
     * @return El objeto CategoryDTO con los datos guardados, incluyendo el ID generado
     */
    CategoryDTO saveCategory(CategoryDTO categoryDTO);

    /**
     * Obtiene una categoría por su identificador único.
     *
     * @param id Identificador único de la categoría a buscar
     * @return El objeto CategoryDTO correspondiente al ID proporcionado, o null si no se encuentra
     */
    CategoryDTO getCategoryById(Long id);

    /**
     * Elimina una categoría del sistema por su identificador único.
     *
     * @param id Identificador único de la categoría a eliminar
     * @throws IllegalArgumentException Si la categoría con el ID especificado no existe
     */
    void deleteCategory(Long id);
}