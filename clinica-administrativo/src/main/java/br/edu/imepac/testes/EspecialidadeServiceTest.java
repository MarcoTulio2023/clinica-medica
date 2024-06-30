package br.edu.imepac.testes;

import br.edu.imepac.dtos.EspecialidadeCreateRequest;
import br.edu.imepac.dtos.EspecialidadeDto;
import br.edu.imepac.models.EspecialidadeModel;
import br.edu.imepac.repositories.EspecialidadeRepository;
import br.edu.imepac.services.EspecialidadeService;
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
public class EspecialidadeServiceTest {

    @Mock
    private EspecialidadeRepository especialidadeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EspecialidadeService especialidadeService;

    private EspecialidadeModel especialidadeModel;
    private EspecialidadeDto especialidadeDto;
    private EspecialidadeCreateRequest especialidadeCreateRequest;

    @BeforeEach
    void setUp() {
        especialidadeModel = new EspecialidadeModel();
        especialidadeModel.setId(1L);
        especialidadeModel.setNome("Cardiology");

        especialidadeDto = new EspecialidadeDto();
        especialidadeDto.setId(1L);
        especialidadeDto.setNome("Cardiology");

        especialidadeCreateRequest = new EspecialidadeCreateRequest();
        especialidadeCreateRequest.setNome("Cardiology");
    }

    @Test
    void testDelete() {
        doNothing().when(especialidadeRepository).deleteById(1L);
        especialidadeService.delete(1L);
        verify(especialidadeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindAll() {
        when(especialidadeRepository.findAll()).thenReturn(Arrays.asList(especialidadeModel));
        when(modelMapper.map(any(EspecialidadeModel.class), eq(EspecialidadeDto.class))).thenReturn(especialidadeDto);
        List<EspecialidadeDto> result = especialidadeService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Cardiology", result.get(0).getNome());
    }

    @Test
    void testUpdate() {
        when(especialidadeRepository.findById(1L)).thenReturn(Optional.of(especialidadeModel));
        when(especialidadeRepository.save(any(EspecialidadeModel.class))).thenReturn(especialidadeModel);

        doReturn(especialidadeModel).when(modelMapper).map(eq(especialidadeDto), any(EspecialidadeModel.class));
        doReturn(especialidadeDto).when(modelMapper).map(any(EspecialidadeModel.class), eq(EspecialidadeDto.class));

        EspecialidadeDto result = especialidadeService.update(1L, especialidadeDto);
        assertNotNull(result);
        assertEquals("Cardiology", result.getNome());

        verify(especialidadeRepository, times(1)).findById(1L);
        verify(especialidadeRepository, times(1)).save(especialidadeModel);
        verify(modelMapper, times(1)).map(eq(especialidadeDto), any(EspecialidadeModel.class));
        verify(modelMapper, times(1)).map(any(EspecialidadeModel.class), eq(EspecialidadeDto.class));
    }

    @Test
    void testUpdate_NotFound() {
        when(especialidadeRepository.findById(1L)).thenReturn(Optional.empty());
        EspecialidadeDto result = especialidadeService.update(1L, especialidadeDto);
        assertNull(result);
    }

    @Test
    void testSave() {
        when(modelMapper.map(any(EspecialidadeCreateRequest.class), eq(EspecialidadeModel.class))).thenReturn(especialidadeModel);
        when(especialidadeRepository.save(any(EspecialidadeModel.class))).thenReturn(especialidadeModel);
        when(modelMapper.map(any(EspecialidadeModel.class), eq(EspecialidadeDto.class))).thenReturn(especialidadeDto);

        EspecialidadeDto result = especialidadeService.save(especialidadeCreateRequest);
        assertNotNull(result);
        assertEquals("Cardiology", result.getNome());
    }

    @Test
    void testFindById() {
        when(especialidadeRepository.findById(1L)).thenReturn(Optional.of(especialidadeModel));
        when(modelMapper.map(any(EspecialidadeModel.class), eq(EspecialidadeDto.class))).thenReturn(especialidadeDto);

        EspecialidadeDto result = especialidadeService.findById(1L);
        assertNotNull(result);
        assertEquals("Cardiology", result.getNome());
    }

    @Test
    void testFindById_NotFound() {
        when(especialidadeRepository.findById(1L)).thenReturn(Optional.empty());
        EspecialidadeDto result = especialidadeService.findById(1L);
        assertNull(result);
    }
}
