package br.edu.imepac.testes;

import br.edu.imepac.dtos.PacienteCreateRequest;
import br.edu.imepac.dtos.PacienteDto;
import br.edu.imepac.models.ConvenioModel;
import br.edu.imepac.models.PacienteModel;
import br.edu.imepac.repositories.ConvenioRepository;
import br.edu.imepac.repositories.PacienteRepository;
import br.edu.imepac.services.PacienteService;
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
public class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private ConvenioRepository convenioRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PacienteService pacienteService;

    private PacienteModel pacienteModel;
    private PacienteDto pacienteDto;
    private PacienteCreateRequest pacienteCreateRequest;
    private ConvenioModel convenioModel;

    @BeforeEach
    void setUp() {
        pacienteModel = new PacienteModel();
        pacienteModel.setId_paciente(1L);
        pacienteModel.setNome("John Doe");

        pacienteDto = new PacienteDto();
        pacienteDto.setId(1L);
        pacienteDto.setNome("John Doe");

        pacienteCreateRequest = new PacienteCreateRequest();
        pacienteCreateRequest.setNome("John Doe");

        convenioModel = new ConvenioModel();
        convenioModel.setId(1L);
    }

    @Test
    void testDelete() {
        doNothing().when(pacienteRepository).deleteById(1L);
        pacienteService.delete(1L);
        verify(pacienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindAll() {
        when(pacienteRepository.findAll()).thenReturn(Arrays.asList(pacienteModel));
        when(modelMapper.map(any(PacienteModel.class), eq(PacienteDto.class))).thenReturn(pacienteDto);

        List<PacienteDto> result = pacienteService.findAll();
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getNome());
    }

    @Test
    void testUpdate() {
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(pacienteModel));
        when(pacienteRepository.save(any(PacienteModel.class))).thenReturn(pacienteModel);
        when(modelMapper.map(any(PacienteModel.class), eq(PacienteDto.class))).thenReturn(pacienteDto);

        PacienteDto result = pacienteService.update(1L, pacienteDto);
        assertNotNull(result);
        assertEquals("John Doe", result.getNome());
    }

    @Test
    void testUpdateNotFound() {
        when(pacienteRepository.findById(1L)).thenReturn(Optional.empty());

        PacienteDto result = pacienteService.update(1L, pacienteDto);
        assertNull(result);
    }

    @Test
    void testSave() {
        when(modelMapper.map(any(PacienteCreateRequest.class), eq(PacienteModel.class))).thenReturn(pacienteModel);
        when(pacienteRepository.save(any(PacienteModel.class))).thenReturn(pacienteModel);
        when(modelMapper.map(any(PacienteModel.class), eq(PacienteDto.class))).thenReturn(pacienteDto);

        PacienteDto result = pacienteService.save(pacienteCreateRequest);
        assertNotNull(result);
        assertEquals("John Doe", result.getNome());
    }

    @Test
    void testFindById() {
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(pacienteModel));
        when(modelMapper.map(any(PacienteModel.class), eq(PacienteDto.class))).thenReturn(pacienteDto);

        PacienteDto result = pacienteService.findById(1L);
        assertNotNull(result);
        assertEquals("John Doe", result.getNome());
    }

    @Test
    void testFindByIdNotFound() {
        when(pacienteRepository.findById(1L)).thenReturn(Optional.empty());

        PacienteDto result = pacienteService.findById(1L);
        assertNull(result);
    }
}