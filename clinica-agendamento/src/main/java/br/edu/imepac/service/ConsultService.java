package br.edu.imepac.service;

import br.edu.imepac.dto.ConsultCreateRequest;
import br.edu.imepac.dto.ConsultDTO;
import br.edu.imepac.model.ConsultModel;
import br.edu.imepac.repositories.ConsultRepository;
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

<<<<<<< Updated upstream
            // Converte a entidade atualizada de volta para DTO
            return modelMapper.map(updatedEspecialidade, ConsultDTO.class);
=======
            if (consultDetails.getPaciente() != null) {
                Optional<PacienteModel> paciente = pacienteRepository.findById(consultDetails.getPaciente().getId_paciente());
                paciente.ifPresent(consultModel::setPaciente);
            }
            if (consultDetails.getUsuario() != null) {
                Optional<UsuarioModel> usuario = usuarioRepository.findById(consultDetails.getUsuario().getId_usuario());
                usuario.ifPresent(consultModel::setUsuario);
            }

            ConsultModel updatedconsult = consultRepository.save(consultModel);
            ConsultDTO consultDto = new ConsultDTO();
            consultDto.setRegistro_agenda(updatedconsult.getRegistro_agenda());
            consultDto.setHora_agenda(updatedconsult.getHora_agenda());
            consultDto.setData_agenda(updatedconsult.getData_agenda());
            consultDto.setRetorno(updatedconsult.isRetorno());
            consultDto.setCancelado(updatedconsult.isCancelado());
            consultDto.setMotivo_cancelamento(consultDetails.getMotivo_cancelamento());
            consultDto.setMedico(consultDetails.getMedico());
            consultDto.setPaciente(consultDetails.getPaciente());
            consultDto.setUsuario(consultDetails.getUsuario());

            if (updatedconsult.getMedico() != null) {
                Optional<MedicoModel> medico = medicoRepository.findById(consultDetails.getMedico().getId());
                medico.ifPresent(consultModel::setMedico);
            }
            if (updatedconsult.getPaciente() != null) {
                Optional<PacienteModel> paciente = pacienteRepository.findById(consultDetails.getPaciente().getId_paciente());
                paciente.ifPresent(consultModel::setPaciente);
            }
            if (updatedconsult.getUsuario() != null) {
                Optional<UsuarioModel> usuario = usuarioRepository.findById(consultDetails.getUsuario().getId_usuario());
                usuario.ifPresent(consultModel::setUsuario);
            }

>>>>>>> Stashed changes
        } else {
            return null;
        }
        return consultDetails;
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
