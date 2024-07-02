package test.edu.imepac.services;

import br.edu.imepac.dtos.ConvenioCreateRequest;
import br.edu.imepac.dtos.ConvenioDto;
import br.edu.imepac.models.ConvenioModel;
import br.edu.imepac.repositories.ConvenioRepository;
import br.edu.imepac.services.ConvenioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConvenioServiceTest {

    @Mock
    private ConvenioRepository convenioRepository;

    @InjectMocks
    private ConvenioService convenioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDelete() {
        Long id = 1L;
        doNothing().when(convenioRepository).deleteById(id);
        convenioService.delete(id);
        verify(convenioRepository, times(1)).deleteById(id);
    }

    @Test
    void testFindAll() {
        ConvenioModel convenio1 = new ConvenioModel();
        convenio1.setId(1L);
        convenio1.setNome("Convenio 1");
        convenio1.setTipoPlano("Tipo 1");
        convenio1.setCobertura("Cobertura 1");
        convenio1.setInformacoesContato("Contato 1");

        ConvenioModel convenio2 = new ConvenioModel();
        convenio2.setId(2L);
        convenio2.setNome("Convenio 2");
        convenio2.setTipoPlano("Tipo 2");
        convenio2.setCobertura("Cobertura 2");
        convenio2.setInformacoesContato("Contato 2");

        when(convenioRepository.findAll()).thenReturn(Arrays.asList(convenio1, convenio2));

        List<ConvenioDto> result = convenioService.findAll();

        assertEquals(2, result.size());
        assertEquals("Convenio 1", result.get(0).getNome());
        assertEquals("Convenio 2", result.get(1).getNome());
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        ConvenioDto convenioDetails = new ConvenioDto();
        convenioDetails.setNome("Updated Convenio");
        convenioDetails.setTipoPlano("Updated Tipo");
        convenioDetails.setCobertura("Updated Cobertura");
        convenioDetails.setInformacoesContato("Updated Contato");

        ConvenioModel existingConvenio = new ConvenioModel();
        existingConvenio.setId(id);
        existingConvenio.setNome("Old Convenio");
        existingConvenio.setTipoPlano("Old Tipo");
        existingConvenio.setCobertura("Old Cobertura");
        existingConvenio.setInformacoesContato("Old Contato");

        when(convenioRepository.findById(id)).thenReturn(Optional.of(existingConvenio));
        when(convenioRepository.save(any(ConvenioModel.class))).thenReturn(existingConvenio);

        ConvenioDto result = convenioService.update(id, convenioDetails);

        assertNotNull(result);
        assertEquals("Updated Convenio", result.getNome());
        assertEquals("Updated Tipo", result.getTipoPlano());
        assertEquals("Updated Cobertura", result.getCobertura());
        assertEquals("Updated Contato", result.getInformacoesContato());
    }

    @Test
    void testUpdate_NotFound() {
        Long id = 1L;
        ConvenioDto convenioDetails = new ConvenioDto();
        when(convenioRepository.findById(id)).thenReturn(Optional.empty());

        ConvenioDto result = convenioService.update(id, convenioDetails);

        assertNull(result);
    }

    @Test
    void testSave() {
        ConvenioCreateRequest convenioRequest = new ConvenioCreateRequest();
        convenioRequest.setNome("New Convenio");
        convenioRequest.setTipoPlano("New Tipo");
        convenioRequest.setCobertura("New Cobertura");
        convenioRequest.setInformacoesContato("New Contato");

        ConvenioModel savedConvenio = new ConvenioModel();
        savedConvenio.setId(1L);
        savedConvenio.setNome("New Convenio");
        savedConvenio.setTipoPlano("New Tipo");
        savedConvenio.setCobertura("New Cobertura");
        savedConvenio.setInformacoesContato("New Contato");

        when(convenioRepository.save(any(ConvenioModel.class))).thenReturn(savedConvenio);

        ConvenioDto result = convenioService.save(convenioRequest);

        assertNotNull(result);
        assertEquals("New Convenio", result.getNome());
        assertEquals("New Tipo", result.getTipoPlano());
        assertEquals("New Cobertura", result.getCobertura());
        assertEquals("New Contato", result.getInformacoesContato());
    }

    @Test
    void testFindById() {
        Long id = 1L;
        ConvenioModel convenio = new ConvenioModel();
        convenio.setId(id);
        convenio.setNome("Convenio 1");
        convenio.setTipoPlano("Tipo 1");
        convenio.setCobertura("Cobertura 1");
        convenio.setInformacoesContato("Contato 1");

        when(convenioRepository.findById(id)).thenReturn(Optional.of(convenio));

        ConvenioDto result = convenioService.findById(id);

        assertNotNull(result);
        assertEquals("Convenio 1", result.getNome());
        assertEquals("Tipo 1", result.getTipoPlano());
        assertEquals("Cobertura 1", result.getCobertura());
        assertEquals("Contato 1", result.getInformacoesContato());
    }

    @Test
    void testFindById_NotFound() {
        Long id = 1L;
        when(convenioRepository.findById(id)).thenReturn(Optional.empty());

        ConvenioDto result = convenioService.findById(id);

        assertNull(result);
    }
}