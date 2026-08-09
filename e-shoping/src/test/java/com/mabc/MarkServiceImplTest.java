package com.mabc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mabc.dto.MarkDTO;
import com.mabc.entities.Mark;
import com.mabc.repositories.MarkRepository;
import com.mabc.services.mark.MarkServiceImpl;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class MarkServiceImplTest{

    private MarkRepository repository;
    private MarkServiceImpl service;

    @BeforeEach
    public void setUp(){
        this.repository = mock(MarkRepository.class);
        this.service = new MarkServiceImpl(this.repository);
    }

    @Test
    @DisplayName("Registra una Marca nueva con todas sus propiedades válidas")
    public void testSaveANewMarkWithValidProperties(){
        // Arrange
        MarkDTO markDTO = new MarkDTO(null, "Lenovo", true);
        Mark mark = new Mark(1L, "Lenovo", true);

        when(this.repository.save(any(Mark.class))).thenReturn(mark);

        // Act
        MarkDTO savedMarkDTO = this.service.saveMark(markDTO);

        // Assert
        assertNotNull(savedMarkDTO);
        assertEquals(mark.getName(), savedMarkDTO.getName());
        verify(this.repository, never()).findById(any());

        ArgumentCaptor<Mark> captor = ArgumentCaptor.forClass(Mark.class);
        verify(this.repository).save(captor.capture());
        assertNull(captor.getValue().getId());
        assertEquals("Lenovo", captor.getValue().getName());
        assertTrue(captor.getValue().getActive());
    }

    
    @Test
    @DisplayName("Intenta registrar una Marca nueva con nombre vacio, retorna un error de argumento")
    public void testSaveANewMarkWithEmptyNameReturnException(){
        // Arrange
        MarkDTO markDTO = new MarkDTO(null, "", true);

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> this.service.saveMark(markDTO));

        // Assert
        verify(this.repository, never()).findById(any());
        verify(this.repository, never()).save(any(Mark.class));
    }
    
    @Test
    @DisplayName("Intenta registrar una Marca nueva con nombre nulo, retorna un error de argumento")
    public void testSaveANewMarkWithNullNameReturnException(){
        // Arrange
        MarkDTO markDTO = new MarkDTO(null, null, true);

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> this.service.saveMark(markDTO));

        // Assert
        verify(this.repository, never()).findById(any());
        verify(this.repository, never()).save(any(Mark.class));
    }
    
    @Test
    @DisplayName("Intenta registrar una Marca nueva con activo nulo, retorna un error de argumento")
    public void testSaveANewMarkWithNullActiveReturnException(){
        // Arrange
        MarkDTO markDTO = new MarkDTO(null, "Lenovo", null);

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> this.service.saveMark(markDTO));

        // Assert
        verify(this.repository, never()).findById(any());
        verify(this.repository, never()).save(any(Mark.class));
    }

    @Test
    @DisplayName("Actualiza una Marca nueva con todas sus propiedades válidas")
    public void testUpdateMarkWithValidProperties(){
        // Arrange
        MarkDTO markDTO = new MarkDTO();
        markDTO.setId(1L);
        markDTO.setName("Lenovo");
        markDTO.setActive(true);

        Mark existsMark = new Mark();
        existsMark.setId(1L);
        existsMark.setName("Lenovo");
        existsMark.setActive(true);

        Mark savedMark = new Mark();
        savedMark.setId(1L);
        savedMark.setName("Lenovo");
        savedMark.setActive(true);

        when(this.repository.findById(anyLong())).thenReturn(Optional.of(existsMark));
        when(this.repository.save(any(Mark.class))).thenReturn(savedMark);

        // Act
        MarkDTO savedMarkDTO = this.service.saveMark(markDTO);

        // Assert
        assertNotNull(savedMarkDTO);
        assertEquals(savedMark.getName(), savedMarkDTO.getName());
        assertTrue(savedMarkDTO.getActive());
        verify(this.repository, times(1)).findById(1L);
        verify(this.repository, times(1)).save(existsMark);
    }

    @Test
    @DisplayName("Intenta grabar una Marca nueva con todas sus propiedades válidas pero el repositorio retorna un objeto nulo y debe retornar nulo")
    public void testUpdateMarkWithValidPropertiesButRepoReturnNull(){
        // Arrange
        MarkDTO markDTO = new MarkDTO();
        markDTO.setId(1L);
        markDTO.setName("Lenovo");
        markDTO.setActive(true);

        Mark existsMark = new Mark();
        existsMark.setId(1L);
        existsMark.setName("Lenovo");
        existsMark.setActive(true);

        Mark savedMark = new Mark();
        savedMark.setId(1L);
        savedMark.setName("Lenovo");
        savedMark.setActive(true);

        when(this.repository.findById(anyLong())).thenReturn(Optional.of(existsMark));
        when(this.repository.save(any(Mark.class))).thenReturn(null);

        // Act
        MarkDTO savedMarkDTO = this.service.saveMark(markDTO);

        // Assert
        assertNull(savedMarkDTO);
        verify(this.repository, times(1)).findById(1L);
        verify(this.repository, times(1)).save(existsMark);
    }

    @Test
    @DisplayName("Intenta registrar una Marca nueva con todas sus propiedades válidas pero genera un error al intentar grabar en el repositorio")
    public void testSaveANewMarkWithValidPropertiesButReturnRuntimeException(){
        // Arrange
        MarkDTO markDTO = new MarkDTO(null, "Lenovo", true);

        when(this.repository.save(any(Mark.class))).thenThrow(new RuntimeException("Error"));

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> this.service.saveMark(markDTO));

        // Assert
        assertEquals("Error", exception.getMessage());
        verify(this.repository, never()).findById(any());

        ArgumentCaptor<Mark> captor = ArgumentCaptor.forClass(Mark.class);
        verify(this.repository).save(captor.capture());
        assertNull(captor.getValue().getId());
        assertEquals("Lenovo", captor.getValue().getName());
        assertTrue(captor.getValue().getActive());
    }

    @Test
    @DisplayName("Debe retornar una marca existente a partir de un ID válido")
    public void testReturnAValidMarkWithValidId(){
        // Arrange
        Long searchedId = 1L;
        Mark existsMark = new Mark();
        existsMark.setId(1L);
        existsMark.setName("Lenovo");
        existsMark.setActive(true);

        when(this.repository.findById(anyLong())).thenReturn(Optional.of(existsMark));

        // Act
        MarkDTO foundMarkDTO = this.service.getMarkById(searchedId);

        // Assert
        assertNotNull(foundMarkDTO);
        assertEquals(foundMarkDTO.getId(), searchedId);
        verify(this.repository, times(1)).findById(searchedId);
    }

    @Test
    @DisplayName("Busca una marca existente a partir de un ID no existente pero retorna nulo")
    public void testReturnNullWithNotExistsId(){
        // Arrange
        Long searchedId = 99999L;
        Mark existsMark = new Mark(1L, "Lenovo", true);

        when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        MarkDTO foundMarkDTO = this.service.getMarkById(searchedId);

        // Assert
        assertNull(foundMarkDTO);
        verify(this.repository, times(1)).findById(searchedId);
    }

    @Test
    @DisplayName("Elimina una marca a partir de un ID existente")
    public void testDeleteMarkWithValidId(){
        // Arrange
        Long searchedId = 1L;
        Mark existsMark = new Mark(1L, "Lenovo", true);

        when(this.repository.existsById(anyLong())).thenReturn(true);
        doNothing().when(this.repository).deleteById(anyLong());

        // Act
        this.service.deleteMark(searchedId);        

        // Assert
        verify(this.repository, times(1)).existsById(searchedId);
        verify(this.repository, times(1)).deleteById(searchedId);
    }

    @Test
    @DisplayName("Intenta eliminar una marca a partir de un ID inexistente, genera un error de argumento")
    public void testDeleteMarkWithInvalidIdReturnException(){
        // Arrange
        Long searchedId = 1L;
        when(this.repository.existsById(anyLong())).thenReturn(false);

        // Act
        assertThrows(IllegalArgumentException.class, () -> this.service.deleteMark(searchedId));

        // Assert
        verify(this.repository, times(1)).existsById(searchedId);
        verify(this.repository, never()).deleteById(searchedId);
    }

}