package br.edu.imepac.services;

import br.edu.imepac.dtos.EspecialidadeCreateRequest;
import br.edu.imepac.dtos.EspecialidadeDto;
import br.edu.imepac.models.EspecialidadeModel;
import br.edu.imepac.repositories.EspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EspecialidadeService {

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    public void delete(Long id) {
        especialidadeRepository.deleteById(id);
    }

    public List<EspecialidadeDto> findAll() {
        List<EspecialidadeModel> especialidades = especialidadeRepository.findAll();
        return especialidades.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public EspecialidadeDto save(EspecialidadeCreateRequest especialidadeDto) {
        EspecialidadeModel especialidadeModel = convertToModel(especialidadeDto);
        EspecialidadeModel savedEspecialidade = especialidadeRepository.save(especialidadeModel);
        return convertToDto(savedEspecialidade);
    }

    public EspecialidadeDto update(Long id, EspecialidadeDto especialidadeDto) {
        Optional<EspecialidadeModel> optionalEspecialidade = especialidadeRepository.findById(id);

        if (optionalEspecialidade.isPresent()) {
            EspecialidadeModel especialidadeModel = optionalEspecialidade.get();
            especialidadeModel.setNome_especialidade(especialidadeDto.getNome_especialidade());
            especialidadeModel.setDescricao(especialidadeDto.getDescricao());

            EspecialidadeModel updatedEspecialidade = especialidadeRepository.save(especialidadeModel);
            return convertToDto(updatedEspecialidade);
        } else {
            // Handle case when especialidade with given id is not found
            // You can throw an exception or return an appropriate response
            return null;
        }
    }

    public EspecialidadeDto findById(Long id) {
        Optional<EspecialidadeModel> optionalEspecialidade = especialidadeRepository.findById(id);
        return optionalEspecialidade.map(this::convertToDto).orElse(null);
    }

    private EspecialidadeModel convertToModel(EspecialidadeCreateRequest especialidadeDto) {
        EspecialidadeModel especialidadeModel = new EspecialidadeModel();
        especialidadeModel.setNome_especialidade(especialidadeDto.getNome_especialidade());
        especialidadeModel.setDescricao(especialidadeDto.getDescricao());
        return especialidadeModel;
    }

    private EspecialidadeDto convertToDto(EspecialidadeModel especialidadeModel) {
        EspecialidadeDto especialidadeDto = new EspecialidadeDto();
        especialidadeDto.setId_especialidade(especialidadeModel.getId_especialidade());
        especialidadeDto.setNome_especialidade(especialidadeModel.getNome_especialidade());
        especialidadeDto.setDescricao(especialidadeModel.getDescricao());
        return especialidadeDto;
    }
}
