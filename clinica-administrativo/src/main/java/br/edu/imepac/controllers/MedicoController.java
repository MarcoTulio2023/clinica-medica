package br.edu.imepac.controllers;

import br.edu.imepac.dtos.MedicoCreateRequest;
import br.edu.imepac.dtos.MedicoDto;
import br.edu.imepac.services.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @PostMapping
    public ResponseEntity<MedicoDto> saveMedico(@RequestBody MedicoCreateRequest medicoCreateRequest) {
        MedicoDto savedMedico = medicoService.save(medicoCreateRequest);
        if (savedMedico != null) {
            return new ResponseEntity<>(savedMedico, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Especialidade não encontrada
        }
    }

    @GetMapping
    public ResponseEntity<List<MedicoDto>> listAllMedicos() {
        List<MedicoDto> medicos = medicoService.findAll();
        return new ResponseEntity<>(medicos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MedicoDto> getMedicoById(@PathVariable Long id) {
        MedicoDto medicoDto = medicoService.findById(id);
        if (medicoDto != null) {
            return new ResponseEntity<>(medicoDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<MedicoDto> updateMedico(@PathVariable Long id, @RequestBody MedicoDto medicoDetails) {
//        MedicoDto updatedMedico = medicoService.update(id, medicoDetails);
//        if (updatedMedico != null) {
//            return new ResponseEntity<>(updatedMedico, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMedico(@PathVariable Long id) {
        medicoService.delete(id);
    }
}
