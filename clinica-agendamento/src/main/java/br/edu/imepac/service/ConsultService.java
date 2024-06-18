package br.edu.imepac.service;

import br.edu.imepac.dto.ConsultCreateRequest;
import br.edu.imepac.dto.ConsultDTO;
import br.edu.imepac.model.ConsultModel;
import br.edu.imepac.models.ConvenioModel;
import br.edu.imepac.models.MedicoModel;
import br.edu.imepac.models.PacienteModel;
import br.edu.imepac.models.UsuarioModel;
import br.edu.imepac.repositories.ConsultRepository;
import br.edu.imepac.repositories.MedicoRepository;
import br.edu.imepac.repositories.PacienteRepository;
import br.edu.imepac.repositories.UsuarioRepository;
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
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
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

    public ConsultDTO update(Long id, ConsultDTO consultDetails) {
        Optional<ConsultModel> optionalEspecialidade = consultRepository.findById(id);

        if (optionalEspecialidade.isPresent()) {
            ConsultModel consultModel = optionalEspecialidade.get();

            consultModel.setRegistro_agenda(id);
            consultModel.setHora_agenda(consultDetails.getHora_agenda());
            consultModel.setData_agenda(consultDetails.getData_agenda());
            consultModel.setRetorno(consultDetails.isRetorno());
            consultModel.setCancelado(consultDetails.isCancelado());
            consultModel.setMotivo_cancelamento(consultDetails.getMotivo_cancelamento());
            consultModel.setMedico(consultDetails.getMedico());
            consultModel.setPaciente(consultDetails.getPaciente());
            consultModel.setUsuario(consultDetails.getUsuario());

            if (consultDetails.getMedico() != null) {
                Optional<MedicoModel> medico = medicoRepository.findById(consultDetails.getMedico().getId());
                medico.ifPresent(consultModel::setMedico);
            }

            if (consultDetails.getPaciente() != null) {
                Optional<PacienteModel> paciente = pacienteRepository.findById(consultDetails.getPaciente().getId_paciente());
                paciente.ifPresent(consultModel::setPaciente);
            }
            if (consultDetails.getUsuario() != null) {
                Optional<UsuarioModel> usuario = usuarioRepository.findById(consultDetails.getUsuario().getId_usuario());
                usuario.ifPresent(consultModel::setUsuario);
            }

            ConsultModel updatedConsult = consultRepository.save(consultModel);
            return modelMapper.map(updatedConsult, ConsultDTO.class);
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
