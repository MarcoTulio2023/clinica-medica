package br.edu.imepac.services;

import br.edu.imepac.dtos.ConvenioDto;
import br.edu.imepac.dtos.EspecialidadeCreateRequest;
import br.edu.imepac.dtos.EspecialidadeDto;
import br.edu.imepac.models.ConvenioModel;
import br.edu.imepac.models.EspecialidadeModel;
import br.edu.imepac.repositories.EspecialidadeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
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
            especialidadeModel.setId(especialidadeDetails.getId());
            especialidadeModel.setNome(especialidadeDetails.getNome());
            especialidadeModel.setDescricao(especialidadeDetails.getDescricao());

            EspecialidadeModel updatesEspeccialidade = especialidadeRepository.save(especialidadeModel);

            EspecialidadeDto especialidadeDto = new EspecialidadeDto();
            especialidadeDto.setId(updatesEspeccialidade.getId());
            especialidadeDto.setNome(updatesEspeccialidade.getNome());
            especialidadeDto.setDescricao(updatesEspeccialidade.getDescricao());

            return especialidadeDto;
        } else {
            return null;
        }
    }

    public EspecialidadeDto save(EspecialidadeCreateRequest especialidadeRequest) {
        EspecialidadeModel especialidadeModel = modelMapper.map(especialidadeRequest, EspecialidadeModel.class);
        EspecialidadeModel savedEspecialidade = especialidadeRepository.save(especialidadeModel);

        log.info("Especialidade {} foi salva com sucesso.", especialidadeRequest.getNome());
        return modelMapper.map(savedEspecialidade, EspecialidadeDto.class);
    }

    public EspecialidadeDto findById(Long id) {
        Optional<EspecialidadeModel> optionalEspecialidade = especialidadeRepository.findById(id);
        return optionalEspecialidade.map(especialidade -> modelMapper.map(especialidade, EspecialidadeDto.class)).orElse(null);
    }
}
