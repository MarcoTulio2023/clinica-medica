package br.edu.imepac.services;

import br.edu.imepac.dtos.PacienteCreateRequest;
import br.edu.imepac.dtos.PacienteDto;
import br.edu.imepac.models.PacienteModel;
import br.edu.imepac.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void delete(Long id) {
        pacienteRepository.deleteById(id);
    }

    public List<PacienteDto> findAll() {
        List<PacienteModel> pacientes = pacienteRepository.findAll();
        return pacientes.stream()
                .map(paciente -> modelMapper.map(paciente, PacienteDto.class))
                .collect(Collectors.toList());
    }

    public PacienteDto update(Long id, PacienteDto pacienteDetails) {
        Optional<PacienteModel> optionalPaciente = pacienteRepository.findById(id);

        if (optionalPaciente.isPresent()) {
            PacienteModel pacienteModel = optionalPaciente.get();
            
            pacienteModel.setNome(pacienteDetails.getNome());
            pacienteModel.setNumeroRg(pacienteDetails.getNumeroRg());
            pacienteModel.setOrgaoEmissor(pacienteDetails.getOrgaoEmissor());
            pacienteModel.setNumeroCpf(pacienteDetails.getNumeroCpf());
            pacienteModel.setEndereco(pacienteDetails.getEndereco());
            pacienteModel.setComplemento(pacienteDetails.getComplemento());
            pacienteModel.setBairro(pacienteDetails.getBairro());
            pacienteModel.setCidade(pacienteDetails.getCidade());
            pacienteModel.setEstado(pacienteDetails.getEstado());
            pacienteModel.setTelefone(pacienteDetails.getTelefone());
            pacienteModel.setCelular(pacienteDetails.getCelular());
            pacienteModel.setDataNascimento(pacienteDetails.getDataNascimento());
            pacienteModel.setSexo(pacienteDetails.getSexo());
            pacienteModel.setTemConvenio(pacienteDetails.getTemConvenio());
            pacienteModel.setCodigoConvenio(pacienteDetails.getCodigoConvenio());

            PacienteModel updatedPaciente = pacienteRepository.save(pacienteModel);
            return modelMapper.map(updatedPaciente, PacienteDto.class);
        } else {
            return null;
        }
    }

    public PacienteDto save(PacienteCreateRequest pacienteRequest) {
        PacienteModel pacienteModel = modelMapper.map(pacienteRequest, PacienteModel.class);
        PacienteModel savedPaciente = pacienteRepository.save(pacienteModel);
        return modelMapper.map(savedPaciente, PacienteDto.class);
    }

    public PacienteDto findById(Long id) {
        Optional<PacienteModel> optionalPaciente = pacienteRepository.findById(id);
        return optionalPaciente.map(pacienteModel -> modelMapper.map(pacienteModel, PacienteDto.class)).orElse(null);
    }
}
