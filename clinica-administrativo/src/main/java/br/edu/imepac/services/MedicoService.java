package br.edu.imepac.services;

import br.edu.imepac.dtos.MedicoCreateRequest;
import br.edu.imepac.dtos.MedicoDto;
import br.edu.imepac.models.EspecialidadeModel;
import br.edu.imepac.models.MedicoModel;
import br.edu.imepac.repositories.EspecialidadeRepository;
import br.edu.imepac.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void delete(Long id) {
        medicoRepository.deleteById(id);
    }

    public List<MedicoDto> findAll() {
        List<MedicoModel> medicos = medicoRepository.findAll();
        return medicos.stream().map(medico -> {
            MedicoDto medicoDto = modelMapper.map(medico, MedicoDto.class);
            return medicoDto;
        }).collect(Collectors.toList());
    }

//    public MedicoDto update(Long id, MedicoDto medicoDetails) {
//        Optional<MedicoModel> optionalMedico = medicoRepository.findById(id);
//
//        if (optionalMedico.isPresent()) {
//            MedicoModel medicoModel = optionalMedico.get();
//            medicoModel.setNome(medicoDetails.getNome());
//            medicoModel.setCrm(medicoDetails.getCrm());
//
//            Optional<EspecialidadeModel> optionalEspecialidade = especialidadeRepository.findById(medicoDetails.getEspecialidadeId());
//            if (optionalEspecialidade.isPresent()) {
//                medicoModel.setEspecialidade(optionalEspecialidade.get());
//            } else {
//                return null; // Especialidade não encontrada
//            }
//
//            MedicoModel updatedMedico = medicoRepository.save(medicoModel);
//
//            MedicoDto medicoDto = new MedicoDto();
//            medicoDto.setId(updatedMedico.getId());
//            medicoDto.setNome(updatedMedico.getNome());
//            medicoDto.setCrm(updatedMedico.getCrm());
//            medicoDto.setEspecialidadeId(updatedMedico.getEspecialidade().getId());
//
//            return medicoDto;
//        } else {
//            return null;
//        }
//    }

    public MedicoDto save(MedicoCreateRequest medicoRequest) {
        MedicoModel medicoModel = modelMapper.map(medicoRequest, MedicoModel.class);
        MedicoModel savedMedico = medicoRepository.save(medicoModel);
        MedicoDto medicoDto = modelMapper.map(savedMedico, MedicoDto.class);
        return medicoDto;
    }

    public MedicoDto findById(Long id) {
        Optional<MedicoModel> optionalMedico = medicoRepository.findById(id);
        if (optionalMedico.isPresent()) {
            MedicoModel medicoModel = optionalMedico.get();
            MedicoDto medicoDto = modelMapper.map(medicoModel,MedicoDto.class );
            return medicoDto;
        } else {
            return null;
        }
    }
}
