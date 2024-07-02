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
    private EspecialidadeModel especialidadeModel;
    private EspecialidadeDto especialidadeDto;

    @BeforeEach
    void setUp() {
        especialidadeModel = new EspecialidadeModel();
        especialidadeModel.setId(1L);
        especialidadeModel.setNome("Cardiologia");

        especialidadeDto = new EspecialidadeDto();
        especialidadeDto.setId(1L);
        especialidadeDto.setNome("Cardiologia");

        medicoModel = new MedicoModel();
        medicoModel.setId(1L);
        medicoModel.setNome("Dr. João");
        medicoModel.setCrm("12345");
        medicoModel.setEspecialidade(especialidadeModel);

        medicoDto = new MedicoDto();
        medicoDto.setId(1L);
        medicoDto.setNome("Dr. João");
        medicoDto.setCrm("12345");
        medicoDto.setEspecialidade(especialidadeDto);
    }

    @Test
    void testDelete() {
        Long id = 1L;
        medicoService.delete(id);
        verify(medicoRepository, times(1)).deleteById(id);
    }

    @Test
    void testFindAll() {
        when(medicoRepository.findAll()).thenReturn(Arrays.asList(medicoModel));
        when(modelMapper.map(any(MedicoModel.class), eq(MedicoDto.class))).thenReturn(medicoDto);

        List<MedicoDto> result = medicoService.findAll();

        assertEquals(1, result.size());
        assertEquals(medicoDto, result.get(0));
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        when(medicoRepository.findById(id)).thenReturn(Optional.of(medicoModel));
        when(medicoRepository.save(any(MedicoModel.class))).thenReturn(medicoModel);

        MedicoDto updatedMedico = medicoService.update(id, medicoDto);

        assertNotNull(updatedMedico);
        assertEquals(medicoDto.getNome(), updatedMedico.getNome());
        assertEquals(medicoDto.getCrm(), updatedMedico.getCrm());
    }

    @Test
    void testUpdateNotFound() {
        Long id = 1L;
        when(medicoRepository.findById(id)).thenReturn(Optional.empty());

        MedicoDto updatedMedico = medicoService.update(id, medicoDto);

        assertNull(updatedMedico);
    }

    @Test
    void testSave() {
        MedicoCreateRequest medicoRequest = new MedicoCreateRequest();
        medicoRequest.setNome("Dr. João");
        medicoRequest.setCrm("12345");
        medicoRequest.setEspecialidade(especialidadeDto);

        when(especialidadeRepository.findById(anyLong())).thenReturn(Optional.of(especialidadeModel));
        when(medicoRepository.save(any(MedicoModel.class))).thenReturn(medicoModel);

        MedicoDto savedMedico = medicoService.save(medicoRequest);

        assertNotNull(savedMedico);
        assertEquals(medicoDto.getNome(), savedMedico.getNome());
        assertEquals(medicoDto.getCrm(), savedMedico.getCrm());
    }

    @Test
    void testFindById() {
        Long id = 1L;
        when(medicoRepository.findById(id)).thenReturn(Optional.of(medicoModel));
        when(modelMapper.map(any(MedicoModel.class), eq(MedicoDto.class))).thenReturn(medicoDto);

        MedicoDto result = medicoService.findById(id);

        assertNotNull(result);
        assertEquals(medicoDto, result);
    }

    @Test
    void testFindByIdNotFound() {
        Long id = 1L;
        when(medicoRepository.findById(id)).thenReturn(Optional.empty());

        MedicoDto result = medicoService.findById(id);

        assertNull(result);
    }
}