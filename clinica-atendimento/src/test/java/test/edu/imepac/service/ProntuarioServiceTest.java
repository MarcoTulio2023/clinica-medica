package test.edu.imepac.service;

import br.edu.imepac.dto.ProntuarioCreateRequest;
import br.edu.imepac.dto.ProntuarioDto;
import br.edu.imepac.model.ConsultModel;
import br.edu.imepac.models.ProntuarioModel;
import br.edu.imepac.repositories.ConsultRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProntuarioServiceTest {

    @Mock
    private ProntuarioRepository prontuarioRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ConsultRepository consultRepository;

    @InjectMocks
    private ProntuarioService prontuarioService;

    private ProntuarioModel prontuarioModel;
    private ProntuarioDto prontuarioDto;
    private ProntuarioCreateRequest prontuarioCreateRequest;

    @BeforeEach
    void setUp() {
        prontuarioModel = new ProntuarioModel();
        prontuarioModel.setNum_pront(1L);
        prontuarioModel.setExames("Exames");
        prontuarioModel.setReceituario("Receituario");
        prontuarioModel.setHistorico("Historico");

        prontuarioDto = new ProntuarioDto();
        prontuarioDto.setNum_pront(1L);
        prontuarioDto.setExames("Exames");
        prontuarioDto.setReceituario("Receituario");
        prontuarioDto.setHistorico("Historico");

        prontuarioCreateRequest = new ProntuarioCreateRequest();
        prontuarioCreateRequest.setExames("Exames");
        prontuarioCreateRequest.setReceituario("Receituario");
        prontuarioCreateRequest.setHistorico("Historico");
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
        assertEquals(1, result.size());
        assertEquals(prontuarioDto, result.get(0));
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        when(prontuarioRepository.findById(id)).thenReturn(Optional.of(prontuarioModel));
        when(prontuarioRepository.save(any(ProntuarioModel.class))).thenReturn(prontuarioModel);
        when(modelMapper.map(any(ProntuarioModel.class), eq(ProntuarioDto.class))).thenReturn(prontuarioDto);

        ProntuarioDto result = prontuarioService.update(id, prontuarioDto);
        assertNotNull(result);
        assertEquals(prontuarioDto, result);
    }

    @Test
    void testUpdate_NotFound() {
        Long id = 1L;
        when(prontuarioRepository.findById(id)).thenReturn(Optional.empty());

        ProntuarioDto result = prontuarioService.update(id, prontuarioDto);
        assertNull(result);
    }

    @Test
    void testSave() {
        when(modelMapper.map(any(ProntuarioCreateRequest.class), eq(ProntuarioModel.class))).thenReturn(prontuarioModel);
        when(prontuarioRepository.save(any(ProntuarioModel.class))).thenReturn(prontuarioModel);
        when(modelMapper.map(any(ProntuarioModel.class), eq(ProntuarioDto.class))).thenReturn(prontuarioDto);

        ProntuarioDto result = prontuarioService.save(prontuarioCreateRequest);
        assertNotNull(result);
        assertEquals(prontuarioDto, result);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        when(prontuarioRepository.findById(id)).thenReturn(Optional.of(prontuarioModel));
        when(modelMapper.map(any(ProntuarioModel.class), eq(ProntuarioDto.class))).thenReturn(prontuarioDto);

        ProntuarioDto result = prontuarioService.findById(id);
        assertNotNull(result);
        assertEquals(prontuarioDto, result);
    }

    @Test
    void testFindById_NotFound() {
        Long id = 1L;
        when(prontuarioRepository.findById(id)).thenReturn(Optional.empty());

        ProntuarioDto result = prontuarioService.findById(id);
        assertNull(result);
    }
}