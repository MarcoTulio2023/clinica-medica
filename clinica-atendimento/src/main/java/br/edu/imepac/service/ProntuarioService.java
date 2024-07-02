package br.edu.imepac.service;

import br.edu.imepac.dto.ProntuarioCreateRequest;
import br.edu.imepac.dto.ProntuarioDto;
import br.edu.imepac.model.ConsultModel;
import br.edu.imepac.models.ProntuarioModel;
import br.edu.imepac.repositories.ProntuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProntuarioService {

    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void delete(Long id) {
        prontuarioRepository.deleteById(id);
    }

    public List<ProntuarioDto> findAll() {
        List<ProntuarioModel> prontuarios = prontuarioRepository.findAll();
        return prontuarios.stream()
                .map(prontuario -> modelMapper.map(prontuario, ProntuarioDto.class))
                .collect(Collectors.toList());
    }

    public ProntuarioDto update(Long id, ProntuarioDto especialidadeDetails) {
        Optional<ProntuarioModel> optionalProntuario = prontuarioRepository.findById(id);

        if (optionalProntuario.isPresent()) {
            ProntuarioModel prontuarioModel = optionalProntuario.get();

            // Mapeia as propriedades do DTO para a entidade existente
            modelMapper.map(especialidadeDetails, prontuarioModel);

            ProntuarioModel updatedProntuario = prontuarioRepository.save(prontuarioModel);

            // Converte a entidade atualizada de volta para DTO
            return modelMapper.map(updatedProntuario, ProntuarioDto.class);
        } else {
            return null;
        }
    }

    public ProntuarioDto save(ProntuarioCreateRequest prontuarioRequest) {
        ProntuarioModel prontuarioModel = modelMapper.map(prontuarioRequest, ProntuarioModel.class);
        ProntuarioModel savedProtuario = prontuarioRepository.save(prontuarioModel);
        return modelMapper.map(savedProtuario, ProntuarioDto.class);
    }

    public ProntuarioDto findById(Long id) {
        Optional<ProntuarioModel> optionalProntuario = prontuarioRepository.findById(id);
        return optionalProntuario.map(consulta -> modelMapper.map(consulta, ProntuarioDto.class)).orElse(null);
    }
}
