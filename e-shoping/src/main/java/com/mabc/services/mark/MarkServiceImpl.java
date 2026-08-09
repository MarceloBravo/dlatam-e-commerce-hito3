package com.mabc.services.mark;

import com.mabc.entities.Mark;
import com.mabc.repositories.MarkRepository;
import com.mabc.dto.MarkDTO;

import org.springframework.stereotype.Service;



/**
 * Implementación de la interfaz {@link MarkService} que proporciona la lógica
 * de negocio para la gestión de marcas de productos. Esta clase maneja las
 * operaciones CRUD de marcas, incluyendo validaciones de datos.
 */
public class MarkServiceImpl implements MarkService {
    
    /** Repositorio para el acceso a datos de marcas */
    private final MarkRepository repository;

    /**
     * Constructor de la clase MarkServiceImpl.
     * Inicializa el repositorio necesario para el acceso a datos de marcas.
     *
     * @param markRepository Repositorio de marcas
     */
    public MarkServiceImpl(MarkRepository markRepository) {
        this.repository = markRepository;
    }

    /**
     * Guarda o actualiza una marca en el sistema. Valida que los datos
     * obligatorios estén presentes antes de realizar la operación.
     *
     * @param markDTO Objeto DTO con los datos de la marca
     * @return La marca guardada con el ID asignado o actualizado
     * @throws IllegalArgumentException Si los datos de la marca no son válidos o están incompletos
     */
    @Override
    public MarkDTO saveMark(MarkDTO markDTO) {
        this.validaDatos(markDTO);

        Mark mark;
        if (markDTO.getId() == null) {
            mark = new Mark();
        } else {
            mark = this.repository.findById(markDTO.getId()).orElse(new Mark());
        }

        mark.setName(markDTO.getName());
        mark.setActive(markDTO.getActive());

        Mark savedMark = repository.save(mark);
        if(savedMark == null){
            return null;
        }
        markDTO.setId(savedMark.getId());
        return markDTO;
    }

    /**
     * Busca una marca por su identificador único en la base de datos.
     *
     * @param id Identificador de la marca a buscar
     * @return La marca encontrada convertida a DTO, o null si no existe
     */
    @Override
    public MarkDTO getMarkById(Long id) {
        Mark mark = repository.findById(id).orElse(null);
        if(mark == null){
            return null;
        }

        return new MarkDTO(mark.getId(), mark.getName(), mark.getActive());
    }

    /**
     * Elimina una marca del sistema. Verifica que la marca exista antes de eliminarla.
     *
     * @param id Identificador de la marca a eliminar
     * @throws IllegalArgumentException Si la marca con el ID especificado no existe
     */
    @Override
    public void deleteMark(Long id) {
        if(!this.repository.existsById(id)){
            throw new IllegalArgumentException("Imposible eliminar, Marca inexistente");
        }
        repository.deleteById(id);
    }

    /**
     * Valida que los datos obligatorios de la marca estén presentes y sean correctos.
     * Verifica que el nombre no sea nulo ni esté vacío, y que el estado activo esté definido.
     *
     * @param dto Objeto DTO con los datos de la marca a validar
     * @throws IllegalArgumentException Si los datos no son válidos o están incompletos
     */
    private void validaDatos(MarkDTO dto) throws IllegalArgumentException{
        Boolean isValid = true;
        isValid = dto.getName() != null && !dto.getName().isEmpty();
        isValid = isValid && dto.getActive() != null;
        if(!isValid){
            throw new IllegalArgumentException("Marca con datos no válidos o incompletos");
        }
    }
}