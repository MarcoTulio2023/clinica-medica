package test.edu.imepac.services;

import br.edu.imepac.dtos.UsuarioCreateRequest;
import br.edu.imepac.dtos.UsuarioDto;
import br.edu.imepac.models.UsuarioModel;
import br.edu.imepac.repositories.UsuarioRepository;
import br.edu.imepac.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServieTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ModelMapper modelMapper;

    private UsuarioModel usuarioModel;
    private UsuarioDto usuarioDto;
    private UsuarioCreateRequest usuarioCreateRequest;

    @BeforeEach
    public void setUp() {
        usuarioModel = new UsuarioModel();
        usuarioModel.setId_usuario(1L);
        usuarioModel.setNome("Test User");
        usuarioModel.setSenha("password");

        usuarioDto = new UsuarioDto();
        usuarioDto.setId(1L);
        usuarioDto.setNome("Test User");
        usuarioDto.setSenha("password");

        usuarioCreateRequest = new UsuarioCreateRequest();
        usuarioCreateRequest.setNome("Test User");
        usuarioCreateRequest.setSenha("password");
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        doNothing().when(usuarioRepository).deleteById(id);
        usuarioService.delete(id);
        verify(usuarioRepository, times(1)).deleteById(id);
    }

    @Test
    public void testFindAll() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuarioModel));
        when(modelMapper.map(any(UsuarioModel.class), eq(UsuarioDto.class))).thenReturn(usuarioDto);

        List<UsuarioDto> result = usuarioService.findAll();
        assertEquals(1, result.size());
        assertEquals("Test User", result.get(0).getNome());
    }

    @Test
    public void testUpdate() {
        Long id = 1L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioModel));
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuarioModel);

        UsuarioDto updatedDto = usuarioService.update(id, usuarioDto);
        assertNotNull(updatedDto);
        assertEquals("Test User", updatedDto.getNome());
    }

    @Test
    public void testUpdate_NotFound() {
        Long id = 1L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        UsuarioDto updatedDto = usuarioService.update(id, usuarioDto);
        assertNull(updatedDto);
    }

    @Test
    public void testSave() {
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuarioModel);

        UsuarioDto savedDto = usuarioService.save(usuarioCreateRequest);
        assertNotNull(savedDto);
        assertEquals("Test User", savedDto.getNome());
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioModel));
        when(modelMapper.map(any(UsuarioModel.class), eq(UsuarioDto.class))).thenReturn(usuarioDto);

        UsuarioDto foundDto = usuarioService.findById(id);
        assertNotNull(foundDto);
        assertEquals("Test User", foundDto.getNome());
    }

    @Test
    public void testFindById_NotFound() {
        Long id = 1L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        UsuarioDto foundDto = usuarioService.findById(id);
        assertNull(foundDto);
    }
}