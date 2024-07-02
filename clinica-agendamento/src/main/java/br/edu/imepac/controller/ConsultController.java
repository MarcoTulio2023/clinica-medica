package br.edu.imepac.controller;
import br.edu.imepac.dto.ConsultCreateRequest;
import br.edu.imepac.dto.ConsultDTO;
import br.edu.imepac.service.ConsultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultController {

    @Autowired
    private ConsultService consultService;

    @PostMapping
    public ResponseEntity<ConsultDTO> saveConsult(@RequestBody ConsultCreateRequest consultCreateRequest) {
        ConsultDTO savedConsulta = consultService.save(consultCreateRequest);
        return new ResponseEntity<>(savedConsulta, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ConsultDTO>> listAllConvenios() {
        List<ConsultDTO> consultas = consultService.findAll();
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ConsultDTO> getConsultaById(@PathVariable Long id) {
        ConsultDTO consultDTO = consultService.findById(id);
        if (consultDTO != null) {
            return new ResponseEntity<>(consultDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultDTO> updateConsultas(@PathVariable Long id, @RequestBody ConsultDTO consultaDetails) {
        ConsultDTO updatedConsulta = consultService.update(id, consultaDetails);
        if (updatedConsulta != null) {
            return new ResponseEntity<>(updatedConsulta, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteConsult(@PathVariable Long id) {
        consultService.delete(id);
    }

}
