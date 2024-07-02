package test.edu.imepac.services;

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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EspecielidadeServiceTest {

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
    public void setUp() {
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
    public void testDelete() {
        doNothing().when(especialidadeRepository).deleteById(1L);
        especialidadeService.delete(1L);
        verify(especialidadeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindAll() {
        when(especialidadeRepository.findAll()).thenReturn(Arrays.asList(especialidadeModel));
        when(modelMapper.map(any(EspecialidadeModel.class), eq(EspecialidadeDto.class))).thenReturn(especialidadeDto);

        List<EspecialidadeDto> result = especialidadeService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Cardiology", result.get(0).getNome());
    }

    @Test
    public void testUpdateEspecialidadeFound() {
        // Arrange
        Long id = 1L;
        EspecialidadeDto especialidadeDetails = new EspecialidadeDto();
        especialidadeDetails.setId(id);
        especialidadeDetails.setNome("Updated Name");
        especialidadeDetails.setDescricao("Updated Description");

        EspecialidadeModel existingEspecialidade = new EspecialidadeModel();
        existingEspecialidade.setId(id);
        existingEspecialidade.setNome("Old Name");
        existingEspecialidade.setDescricao("Old Description");

        when(especialidadeRepository.findById(id)).thenReturn(Optional.of(existingEspecialidade));
        when(especialidadeRepository.save(any(EspecialidadeModel.class))).thenReturn(existingEspecialidade);

        // Act
        EspecialidadeDto updatedEspecialidade = especialidadeService.update(id, especialidadeDetails);

        // Assert
        assertNotNull(updatedEspecialidade);
        assertEquals(especialidadeDetails.getId(), updatedEspecialidade.getId());
        assertEquals(especialidadeDetails.getNome(), updatedEspecialidade.getNome());
        assertEquals(especialidadeDetails.getDescricao(), updatedEspecialidade.getDescricao());

        verify(especialidadeRepository, times(1)).findById(id);
        verify(especialidadeRepository, times(1)).save(any(EspecialidadeModel.class));
    }

    @Test
    public void testUpdateEspecialidadeNotFound() {
        // Arrange
        Long id = 1L;
        EspecialidadeDto especialidadeDetails = new EspecialidadeDto();
        especialidadeDetails.setId(id);
        especialidadeDetails.setNome("Updated Name");
        especialidadeDetails.setDescricao("Updated Description");

        when(especialidadeRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        EspecialidadeDto updatedEspecialidade = especialidadeService.update(id, especialidadeDetails);

        // Assert
        assertNull(updatedEspecialidade);

        verify(especialidadeRepository, times(1)).findById(id);
        verify(especialidadeRepository, times(0)).save(any(EspecialidadeModel.class));
    }


    @Test
    public void testSave() {
        when(modelMapper.map(any(EspecialidadeCreateRequest.class), eq(EspecialidadeModel.class))).thenReturn(especialidadeModel);
        when(especialidadeRepository.save(any(EspecialidadeModel.class))).thenReturn(especialidadeModel);
        when(modelMapper.map(any(EspecialidadeModel.class), eq(EspecialidadeDto.class))).thenReturn(especialidadeDto);

        EspecialidadeDto result = especialidadeService.save(especialidadeCreateRequest);

        assertNotNull(result);
        assertEquals("Cardiology", result.getNome());
    }

    @Test
    public void testFindById() {
        when(especialidadeRepository.findById(1L)).thenReturn(Optional.of(especialidadeModel));
        when(modelMapper.map(any(EspecialidadeModel.class), eq(EspecialidadeDto.class))).thenReturn(especialidadeDto);

        EspecialidadeDto result = especialidadeService.findById(1L);

        assertNotNull(result);
        assertEquals("Cardiology", result.getNome());
    }

    @Test
    public void testFindById_NotFound() {
        when(especialidadeRepository.findById(1L)).thenReturn(Optional.empty());

        EspecialidadeDto result = especialidadeService.findById(1L);

        assertNull(result);
    }
}