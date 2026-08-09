package com.mabc.services.category;

import com.mabc.dto.CategoryDTO;
import com.mabc.entities.Category;
import com.mabc.repositories.CategoryRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;


/**
 * Implementación de la interfaz {@link CategoryService} que proporciona la lógica
 * de negocio para la gestión de categorías de productos. Esta clase maneja las
 * operaciones CRUD de categorías, incluyendo validaciones de datos.
 */
public class CategoryServiceImpl implements CategoryService {

    /** Repositorio para el acceso a datos de categorías */
    private final CategoryRepository repository;
    
    /**
     * Constructor de la clase CategoryServiceImpl.
     * Inicializa el repositorio necesario para el acceso a datos de categorías.
     *
     * @param repository Repositorio de categorías
     */
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    /**
     * Guarda o actualiza una categoría en el sistema. Valida que los datos
     * obligatorios estén presentes antes de realizar la operación.
     *
     * @param categoryDTO Objeto DTO con los datos de la categoría
     * @return La categoría guardada con el ID asignado o actualizado
     * @throws IllegalArgumentException Si los datos de la categoría no son válidos
     */
    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        this.validaDatos(categoryDTO);

        Category category;
        if (categoryDTO.getId() == null) {
            category = new Category();
        } else {
            category = this.repository.findById(categoryDTO.getId()).orElse(new Category());
        }

        category.setName(categoryDTO.getName());
        category.setActive(categoryDTO.getActive());

        Category savedCategory = this.repository.save(category);
        categoryDTO.setId(savedCategory.getId());

        return categoryDTO;
    }

    /**
     * Busca una categoría por su identificador único en la base de datos.
     *
     * @param id Identificador de la categoría a buscar
     * @return La categoría encontrada convertida a DTO, o null si no existe
     */
    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = this.repository.findById(id).orElse(null);
        if (category == null) {
            return null;
        }
        return new CategoryDTO(category.getId(), category.getName(), category.getActive());
    }

    /**
     * Elimina una categoría del sistema. Verifica que la categoría exista antes de eliminarla.
     *
     * @param id Identificador de la categoría a eliminar
     * @throws IllegalArgumentException Si la categoría con el ID especificado no existe
     */
    @Override
    public void deleteCategory(Long id) {
        if(!this.repository.existsById(id)){
            throw new IllegalArgumentException("Category not exist");
        }
        this.repository.deleteById(id);
    }

    /**
     * Valida que los datos obligatorios de la categoría estén presentes y sean correctos.
     * Verifica que el nombre no sea nulo ni esté vacío, y que el estado activo esté definido.
     *
     * @param categoryDTO Objeto DTO con los datos de la categoría a validar
     * @return true si los datos son válidos
     * @throws IllegalArgumentException Si los datos no son válidos o están incompletos
     */
    private Boolean validaDatos(CategoryDTO categoryDTO) throws IllegalArgumentException {
        Boolean isValid = true;       
        isValid = categoryDTO.getName() != null && !categoryDTO.getName().isEmpty();
        isValid = categoryDTO.getActive() != null;

        if (!isValid) {
            throw new IllegalArgumentException("Invalid category data");
        }
        return true;
    }
}