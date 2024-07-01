package test.edu.imepac.services;

import br.edu.imepac.dtos.EspecialidadeDto;
import br.edu.imepac.dtos.MedicoCreateRequest;
import br.edu.imepac.dtos.MedicoDto;
import br.edu.imepac.models.EspecialidadeModel;
import br.edu.imepac.models.MedicoModel;
import br.edu.imepac.repositories.EspecialidadeRepository;
import br.edu.imepac.repositories.MedicoRepository;
import br.edu.imepac.services.MedicoService;
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
public class MedicoServiceTest {

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private EspecialidadeRepository especialidadeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MedicoService medicoService;

    private MedicoModel medicoModel;
    private MedicoDto medicoDto;
    private MedicoCreateRequest medicoCreateRequest;
    private EspecialidadeModel especialidadeModel;

    private EspecialidadeDto especialidadeDto;

    @BeforeEach
    public void setUp() {
        medicoModel = new MedicoModel();
        medicoModel.setId(1L);
        medicoModel.setNome("Dr. John Doe");
        medicoModel.setCrm("12345");

        especialidadeModel = new EspecialidadeModel();
        especialidadeModel.setId(1L);
        especialidadeModel.setNome("Cardiology");

        medicoDto = new MedicoDto();
        medicoDto.setId(1L);
        medicoDto.setNome("Dr. John Doe");
        medicoDto.setCrm("12345");
        medicoDto.setEspecialidade(especialidadeDto);

        medicoCreateRequest = new MedicoCreateRequest();
        medicoCreateRequest.setNome("Dr. John Doe");
        medicoCreateRequest.setCrm("12345");
        medicoCreateRequest.setEspecialidade(especialidadeDto);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        doNothing().when(medicoRepository).deleteById(id);
        medicoService.delete(id);
        verify(medicoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testFindAll() {
        when(medicoRepository.findAll()).thenReturn(Arrays.asList(medicoModel));
        when(modelMapper.map(any(MedicoModel.class), eq(MedicoDto.class))).thenReturn(medicoDto);

        List<MedicoDto> result = medicoService.findAll();
        assertEquals(1, result.size());
        assertEquals(medicoDto, result.get(0));
    }

    @Test
    public void testUpdate() {
        Long id = 1L;
        when(medicoRepository.findById(id)).thenReturn(Optional.of(medicoModel));
        when(especialidadeRepository.findById(anyLong())).thenReturn(Optional.of(especialidadeModel));
        when(medicoRepository.save(any(MedicoModel.class))).thenReturn(medicoModel);
        when(modelMapper.map(any(MedicoModel.class), eq(MedicoDto.class))).thenReturn(medicoDto);

        MedicoDto result = medicoService.update(id, medicoDto);
        assertNotNull(result);
        assertEquals(medicoDto, result);
    }

    @Test
    public void testUpdate_NotFound() {
        Long id = 1L;
        when(medicoRepository.findById(id)).thenReturn(Optional.empty());

        MedicoDto result = medicoService.update(id, medicoDto);
        assertNull(result);
    }

    @Test
    public void testSave() {
        when(modelMapper.map(any(MedicoCreateRequest.class), eq(MedicoModel.class))).thenReturn(medicoModel);
        when(especialidadeRepository.findById(anyLong())).thenReturn(Optional.of(especialidadeModel));
        when(medicoRepository.save(any(MedicoModel.class))).thenReturn(medicoModel);
        when(modelMapper.map(any(MedicoModel.class), eq(MedicoDto.class))).thenReturn(medicoDto);

        MedicoDto result = medicoService.save(medicoCreateRequest);
        assertNotNull(result);
        assertEquals(medicoDto, result);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        when(medicoRepository.findById(id)).thenReturn(Optional.of(medicoModel));
        when(modelMapper.map(any(MedicoModel.class), eq(MedicoDto.class))).thenReturn(medicoDto);

        MedicoDto result = medicoService.findById(id);
        assertNotNull(result);
        assertEquals(medicoDto, result);
    }

    @Test
    public void testFindById_NotFound() {
        Long id = 1L;
        when(medicoRepository.findById(id)).thenReturn(Optional.empty());

        MedicoDto result = medicoService.findById(id);
        assertNull(result);
    }
}