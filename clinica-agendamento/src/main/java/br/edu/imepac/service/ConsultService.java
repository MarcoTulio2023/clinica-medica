package br.edu.imepac.service;

import br.edu.imepac.Repository.ConsultRepository;
import br.edu.imepac.dto.ConsultCreateRequest;
import br.edu.imepac.dto.ConsultDTO;
import br.edu.imepac.model.ConsultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultService {

    @Autowired
    private ConsultRepository consultRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void delete(Long id) {
        consultRepository.deleteById(id);
    }

    public List<ConsultDTO> findAll() {
        List<ConsultModel> consultas = consultRepository.findAll();
        return consultas.stream()
                .map(consulta -> modelMapper.map(consulta, ConsultDTO.class))
                .collect(Collectors.toList());
    }

    public ConsultDTO update(Long id, ConsultDTO especialidadeDetails) {
        Optional<ConsultModel> optionalEspecialidade = consultRepository.findById(id);

        if (optionalEspecialidade.isPresent()) {
            ConsultModel consultModel = optionalEspecialidade.get();

            // Mapeia as propriedades do DTO para a entidade existente
            modelMapper.map(especialidadeDetails, consultModel);

            ConsultModel updatedEspecialidade = consultRepository.save(consultModel);

            // Converte a entidade atualizada de volta para DTO
            return modelMapper.map(updatedEspecialidade, ConsultDTO.class);
        } else {
            return null;
        }
    }

    public ConsultDTO save(ConsultCreateRequest consultaRequest) {
        ConsultModel consultModel = modelMapper.map(consultaRequest, ConsultModel.class);
        ConsultModel savedConsult = consultRepository.save(consultModel);
        return modelMapper.map(savedConsult, ConsultDTO.class);
    }

    public ConsultDTO findById(Long id) {
        Optional<ConsultModel> optionalConsult = consultRepository.findById(id);
        return optionalConsult.map(consulta -> modelMapper.map(consulta, ConsultDTO.class)).orElse(null);
    }
}
