package br.edu.imepac.services;

import br.edu.imepac.dtos.EspecialidadeCreateRequest;
import br.edu.imepac.dtos.EspecialidadeDto;
import br.edu.imepac.models.EspecialidadeModel;
import br.edu.imepac.repositories.EspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EspecialidadeService {

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void delete(Long id) {
        especialidadeRepository.deleteById(id);
    }

    public List<EspecialidadeDto> findAll() {
        List<EspecialidadeModel> especialidades = especialidadeRepository.findAll();
        return especialidades.stream()
                .map(especialidade -> modelMapper.map(especialidade, EspecialidadeDto.class))
                .collect(Collectors.toList());
    }

    public EspecialidadeDto update(Long id, EspecialidadeDto especialidadeDetails) {
        Optional<EspecialidadeModel> optionalEspecialidade = especialidadeRepository.findById(id);

        if (optionalEspecialidade.isPresent()) {
            EspecialidadeModel especialidadeModel = optionalEspecialidade.get();

            // Mapeia as propriedades do DTO para a entidade existente
            modelMapper.map(especialidadeDetails, especialidadeModel);

            EspecialidadeModel updatedEspecialidade = especialidadeRepository.save(especialidadeModel);

            // Converte a entidade atualizada de volta para DTO
            return modelMapper.map(updatedEspecialidade, EspecialidadeDto.class);
        } else {
            return null;
        }
    }

    public EspecialidadeDto save(EspecialidadeCreateRequest especialidadeRequest) {
        EspecialidadeModel especialidadeModel = modelMapper.map(especialidadeRequest, EspecialidadeModel.class);
        EspecialidadeModel savedEspecialidade = especialidadeRepository.save(especialidadeModel);
        return modelMapper.map(savedEspecialidade, EspecialidadeDto.class);
    }

    public EspecialidadeDto findById(Long id) {
        Optional<EspecialidadeModel> optionalEspecialidade = especialidadeRepository.findById(id);
        return optionalEspecialidade.map(especialidade -> modelMapper.map(especialidade, EspecialidadeDto.class)).orElse(null);
    }
}
