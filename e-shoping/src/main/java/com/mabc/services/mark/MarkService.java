package com.mabc.services.mark;

import com.mabc.entities.Mark;
import com.mabc.dto.MarkDTO;

/**
 * Interfaz que define las operaciones disponibles para la gestión de marcas.
 * Proporciona métodos para guardar, consultar y eliminar marcas de productos.
 */
public interface MarkService{
    
    /**
     * Guarda una marca en el sistema. Si la marca ya existe (mediante su ID),
     * se actualizan sus datos; de lo contrario, se crea una nueva marca.
     *
     * @param markDTO Objeto DTO que contiene los datos de la marca a guardar
     * @return El objeto MarkDTO con los datos guardados, incluyendo el ID generado
     */
    MarkDTO saveMark(MarkDTO markDTO);

    /**
     * Obtiene una marca por su identificador único.
     *
     * @param id Identificador único de la marca a buscar
     * @return El objeto MarkDTO correspondiente al ID proporcionado, o null si no se encuentra
     */
    MarkDTO getMarkById(Long id);

    /**
     * Elimina una marca del sistema por su identificador único.
     *
     * @param id Identificador único de la marca a eliminar
     * @throws IllegalArgumentException Si la marca con el ID especificado no existe
     */
    void deleteMark(Long id);
}