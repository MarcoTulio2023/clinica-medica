package br.edu.imepac.testes;

import br.edu.imepac.dto.ProntuarioCreateRequest;
import br.edu.imepac.dto.ProntuarioDto;
import br.edu.imepac.models.ProntuarioModel;
import br.edu.imepac.repositories.ProntuarioRepository;
import br.edu.imepac.service.ProntuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProntuarioServiceTest {

    @Mock
    private ProntuarioRepository prontuarioRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProntuarioService prontuarioService;

    private ProntuarioModel prontuarioModel;
    private ProntuarioDto prontuarioDto;
    private ProntuarioCreateRequest prontuarioCreateRequest;

    @BeforeEach
    void setUp() {
        prontuarioModel = new ProntuarioModel();
        prontuarioDto = new ProntuarioDto();
        prontuarioCreateRequest = new ProntuarioCreateRequest();
    }

    @Test
    void testDelete() {
        Long id = 1L;
        doNothing().when(prontuarioRepository).deleteById(id);
        prontuarioService.delete(id);
        verify(prontuarioRepository, times(1)).deleteById(id);
    }

    @Test
    void testFindAll() {
        when(prontuarioRepository.findAll()).thenReturn(Arrays.asList(prontuarioModel));
        when(modelMapper.map(any(ProntuarioModel.class), eq(ProntuarioDto.class))).thenReturn(prontuarioDto);

        List<ProntuarioDto> result = prontuarioService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(prontuarioRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(any(ProntuarioModel.class), eq(ProntuarioDto.class));
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        when(prontuarioRepository.findById(id)).thenReturn(Optional.of(prontuarioModel));
        when(prontuarioRepository.save(any(ProntuarioModel.class))).thenReturn(prontuarioModel);

        doReturn(prontuarioModel).when(modelMapper).map(eq(prontuarioDto), any(ProntuarioModel.class));
        doReturn(prontuarioDto).when(modelMapper).map(any(ProntuarioModel.class), eq(ProntuarioDto.class));

        ProntuarioDto result = prontuarioService.update(id, prontuarioDto);

        assertNotNull(result);
        verify(prontuarioRepository, times(1)).findById(id);
        verify(prontuarioRepository, times(1)).save(prontuarioModel);
        verify(modelMapper, times(1)).map(eq(prontuarioDto), any(ProntuarioModel.class));
        verify(modelMapper, times(1)).map(any(ProntuarioModel.class), eq(ProntuarioDto.class));
    }

    @Test
    void testUpdate_NotFound() {
        Long id = 1L;
        when(prontuarioRepository.findById(id)).thenReturn(Optional.empty());

        ProntuarioDto result = prontuarioService.update(id, prontuarioDto);

        assertNull(result);
        verify(prontuarioRepository, times(1)).findById(id);
        verify(prontuarioRepository, never()).save(any(ProntuarioModel.class));
        verify(modelMapper, never()).map(any(ProntuarioDto.class), any(ProntuarioModel.class));
        verify(modelMapper, never()).map(any(ProntuarioModel.class), eq(ProntuarioDto.class));
    }

    @Test
    void testSave() {
        when(modelMapper.map(any(ProntuarioCreateRequest.class), eq(ProntuarioModel.class))).thenReturn(prontuarioModel);
        when(prontuarioRepository.save(any(ProntuarioModel.class))).thenReturn(prontuarioModel);
        when(modelMapper.map(any(ProntuarioModel.class), eq(ProntuarioDto.class))).thenReturn(prontuarioDto);

        ProntuarioDto result = prontuarioService.save(prontuarioCreateRequest);

        assertNotNull(result);
        verify(modelMapper, times(1)).map(any(ProntuarioCreateRequest.class), eq(ProntuarioModel.class));
        verify(prontuarioRepository, times(1)).save(prontuarioModel);
        verify(modelMapper, times(1)).map(any(ProntuarioModel.class), eq(ProntuarioDto.class));
    }

    @Test
    void testFindById() {
        Long id = 1L;
        when(prontuarioRepository.findById(id)).thenReturn(Optional.of(prontuarioModel));
        when(modelMapper.map(any(ProntuarioModel.class), eq(ProntuarioDto.class))).thenReturn(prontuarioDto);

        ProntuarioDto result = prontuarioService.findById(id);

        assertNotNull(result);
        verify(prontuarioRepository, times(1)).findById(id);
        verify(modelMapper, times(1)).map(any(ProntuarioModel.class), eq(ProntuarioDto.class));
    }

    @Test
    void testFindById_NotFound() {
        Long id = 1L;
        when(prontuarioRepository.findById(id)).thenReturn(Optional.empty());

        ProntuarioDto result = prontuarioService.findById(id);

        assertNull(result);
        verify(prontuarioRepository, times(1)).findById(id);
        verify(modelMapper, never()).map(any(ProntuarioModel.class), eq(ProntuarioDto.class));
    }
}
