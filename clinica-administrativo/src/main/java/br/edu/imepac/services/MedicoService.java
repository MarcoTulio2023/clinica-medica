package br.edu.imepac.services;

import br.edu.imepac.dtos.MedicoCreateRequest;
import br.edu.imepac.dtos.MedicoDto;
import br.edu.imepac.models.MedicoModel;
import br.edu.imepac.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public void delete(Long id) {
        medicoRepository.deleteById(id);
    }

    public List<MedicoDto> findAll() {
        List<MedicoModel> medicos = medicoRepository.findAll();
        return medicos.stream().map(medico -> {
            MedicoDto medicoDto = new MedicoDto();
            medicoDto.setId(medico.getId());
            medicoDto.setNome_medico(medico.getNome_medico());
            medicoDto.setCrm(medico.getCrm());
            return medicoDto;
        }).collect(Collectors.toList());
    }

    public MedicoDto update(Long id, MedicoDto medicoDetails) {
        Optional<MedicoModel> optionalMedico = medicoRepository.findById(id);

        if (optionalMedico.isPresent()) {
            MedicoModel medicoModel = optionalMedico.get();
            medicoModel.setNome_medico(medicoDetails.getNome_medico());
            medicoModel.setCrm(medicoDetails.getCrm());

            MedicoModel updatedMedico = medicoRepository.save(medicoModel);

            MedicoDto medicoDto = new MedicoDto();
            medicoDto.setId(updatedMedico.getId());
            medicoDto.setNome_medico(updatedMedico.getNome_medico());
            medicoDto.setCrm(updatedMedico.getCrm());

            return medicoDto;
        } else {
            return null;
        }
    }

    public MedicoDto save(MedicoCreateRequest medicoRequest) {
        MedicoModel medicoModel = new MedicoModel();
        medicoModel.setNome_medico(medicoRequest.getNome_medico());
        medicoModel.setCodigo_especialidade(medicoRequest.getCodigo_especialidade());
        medicoModel.setCrm(medicoRequest.getCrm());
        medicoModel.setSenha(medicoRequest.getSenha());

        MedicoModel savedMedico = medicoRepository.save(medicoModel);

        MedicoDto medicoDto = new MedicoDto();
        medicoDto.setId(savedMedico.getId());
        medicoDto.setNome_medico(savedMedico.getNome_medico());
        medicoDto.setCrm(savedMedico.getCrm());

        return medicoDto;
    }

    public MedicoDto findById(Long id) {
        Optional<MedicoModel> optionalMedico = medicoRepository.findById(id);
        if (optionalMedico.isPresent()) {
            MedicoModel medicoModel = optionalMedico.get();
            MedicoDto medicoDto = new MedicoDto();
            medicoDto.setId(medicoModel.getId());
            medicoDto.setNome_medico(medicoModel.getNome_medico());
            medicoDto.setCrm(medicoModel.getCrm());
            return medicoDto;
        } else {
            return null;
        }
    }
}
