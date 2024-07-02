package br.edu.imepac.controller;
import br.edu.imepac.dto.ProntuarioCreateRequest;
import br.edu.imepac.dto.ProntuarioDto;
import br.edu.imepac.service.ProntuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prontuarios")
public class ProntuarioController {

    @Autowired
    private ProntuarioService prontuarioService;

    @PostMapping
    public ResponseEntity<ProntuarioDto> saveConsult(@RequestBody ProntuarioCreateRequest prontuarioCreateRequest) {
        ProntuarioDto savedProntuario = prontuarioService.save(prontuarioCreateRequest);
        return new ResponseEntity<>(savedProntuario, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProntuarioDto>> listAllConvenios() {
        List<ProntuarioDto> prontuarios = prontuarioService.findAll();
        return new ResponseEntity<>(prontuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProntuarioDto> getConsultaById(@PathVariable Long id) {
        ProntuarioDto prontuarioDto = prontuarioService.findById(id);
        if (prontuarioDto != null) {
            return new ResponseEntity<>(prontuarioDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProntuarioDto> updateConsultas(@PathVariable Long id, @RequestBody ProntuarioDto consultaDetails) {
        ProntuarioDto updatedProntuario = prontuarioService.update(id, consultaDetails);
        if (updatedProntuario != null) {
            return new ResponseEntity<>(updatedProntuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteConsult(@PathVariable Long id) {
        prontuarioService.delete(id);
    }

}
