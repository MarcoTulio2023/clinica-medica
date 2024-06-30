package br.edu.imepac.testes;

import br.edu.imepac.dto.ConsultCreateRequest;
import br.edu.imepac.dto.ConsultDTO;
import br.edu.imepac.model.ConsultModel;
import br.edu.imepac.models.MedicoModel;
import br.edu.imepac.models.PacienteModel;
import br.edu.imepac.models.UsuarioModel;
import br.edu.imepac.repositories.ConsultRepository;
import br.edu.imepac.repositories.MedicoRepository;
import br.edu.imepac.repositories.PacienteRepository;
import br.edu.imepac.repositories.UsuarioRepository;
import br.edu.imepac.service.ConsultService;
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
public class ConsultServiceTest {

    @Mock
    private ConsultRepository consultRepository;

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ConsultService consultService;

    private ConsultModel consultModel;
    private ConsultDTO consultDTO;
    private ConsultCreateRequest consultCreateRequest;

    @BeforeEach
    void setUp() {
        consultModel = new ConsultModel();
        consultModel.setRegistro_agenda(1L);
        consultDTO = new ConsultDTO();
        consultDTO.setRegistro_agenda(1L);
        consultCreateRequest = new ConsultCreateRequest();
    }

    @Test
    void testDelete() {
        Long id = 1L;
        doNothing().when(consultRepository).deleteById(id);
        consultService.delete(id);
        verify(consultRepository, times(1)).deleteById(id);
    }

    @Test
    void testFindAll() {
        when(consultRepository.findAll()).thenReturn(Arrays.asList(consultModel));
        when(modelMapper.map(any(ConsultModel.class), eq(ConsultDTO.class))).thenReturn(consultDTO);

        List<ConsultDTO> result = consultService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(consultRepository, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        when(consultRepository.findById(id)).thenReturn(Optional.of(consultModel));
        when(consultRepository.save(any(ConsultModel.class))).thenReturn(consultModel);
        when(modelMapper.map(any(ConsultModel.class), eq(ConsultDTO.class))).thenReturn(consultDTO);

        ConsultDTO result = consultService.update(id, consultDTO);

        assertNotNull(result);
        verify(consultRepository, times(1)).findById(id);
        verify(consultRepository, times(1)).save(any(ConsultModel.class));
    }

    @Test
    void testUpdate_NotFound() {
        Long id = 1L;
        when(consultRepository.findById(id)).thenReturn(Optional.empty());

        ConsultDTO result = consultService.update(id, consultDTO);

        assertNull(result);
        verify(consultRepository, times(1)).findById(id);
        verify(consultRepository, never()).save(any(ConsultModel.class));
    }

    @Test
    void testSave() {
        when(modelMapper.map(any(ConsultCreateRequest.class), eq(ConsultModel.class))).thenReturn(consultModel);
        when(consultRepository.save(any(ConsultModel.class))).thenReturn(consultModel);
        when(modelMapper.map(any(ConsultModel.class), eq(ConsultDTO.class))).thenReturn(consultDTO);

        ConsultDTO result = consultService.save(consultCreateRequest);

        assertNotNull(result);
        verify(consultRepository, times(1)).save(any(ConsultModel.class));
    }

    @Test
    void testFindById() {
        Long id = 1L;
        when(consultRepository.findById(id)).thenReturn(Optional.of(consultModel));
        when(modelMapper.map(any(ConsultModel.class), eq(ConsultDTO.class))).thenReturn(consultDTO);

        ConsultDTO result = consultService.findById(id);

        assertNotNull(result);
        verify(consultRepository, times(1)).findById(id);
    }

    @Test
    void testFindById_NotFound() {
        Long id = 1L;
        when(consultRepository.findById(id)).thenReturn(Optional.empty());

        ConsultDTO result = consultService.findById(id);

        assertNull(result);
        verify(consultRepository, times(1)).findById(id);
    }
}