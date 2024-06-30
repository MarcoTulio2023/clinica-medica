package br.edu.imepac.testes;

import br.edu.imepac.dtos.UsuarioCreateRequest;
import br.edu.imepac.dtos.UsuarioDto;
import br.edu.imepac.models.UsuarioModel;
import br.edu.imepac.repositories.UsuarioRepository;
import br.edu.imepac.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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

/*
The warnings you are seeing are related to the dynamic loading
of Java agents, specifically the Byte Buddy agent used by
Mockito for creating mock objects. These warnings are not
critical and do not affect the success of your tests,
but they indicate that future versions of the JVM may
change how dynamic agent loading is handled.
*/

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UsuarioService usuarioService;

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
        usuarioDto.setNome("Test User");
        usuarioDto.setSenha("password");

        usuarioCreateRequest = new UsuarioCreateRequest();
        usuarioCreateRequest.setNome("Test User");
        usuarioCreateRequest.setSenha("password");
    }

    @Test
    public void testDelete() {
        doNothing().when(usuarioRepository).deleteById(1L);
        usuarioService.delete(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
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
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioModel));
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuarioModel);
        when(modelMapper.map(any(UsuarioModel.class), eq(UsuarioDto.class))).thenReturn(usuarioDto);

        UsuarioDto updatedUsuario = usuarioService.update(1L, usuarioDto);
        assertNotNull(updatedUsuario);
        assertEquals("Test User", updatedUsuario.getNome());
    }

    @Test
    public void testUpdate_NotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        UsuarioDto updatedUsuario = usuarioService.update(1L, usuarioDto);
        assertNull(updatedUsuario);
    }

    @Test
    public void testSave() {
        when(modelMapper.map(any(UsuarioCreateRequest.class), eq(UsuarioModel.class))).thenReturn(usuarioModel);
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuarioModel);
        when(modelMapper.map(any(UsuarioModel.class), eq(UsuarioDto.class))).thenReturn(usuarioDto);

        UsuarioDto savedUsuario = usuarioService.save(usuarioCreateRequest);
        assertNotNull(savedUsuario);
        assertEquals("Test User", savedUsuario.getNome());
    }

    @Test
    public void testFindById() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioModel));
        when(modelMapper.map(any(UsuarioModel.class), eq(UsuarioDto.class))).thenReturn(usuarioDto);

        UsuarioDto foundUsuario = usuarioService.findById(1L);
        assertNotNull(foundUsuario);
        assertEquals("Test User", foundUsuario.getNome());
    }

    @Test
    public void testFindById_NotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        UsuarioDto foundUsuario = usuarioService.findById(1L);
        assertNull(foundUsuario);
    }
}