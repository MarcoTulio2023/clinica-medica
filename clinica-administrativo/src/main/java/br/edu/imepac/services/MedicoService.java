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
        return medicos.stream().map(medico -> modelMapper.map(medico, MedicoDto.class)).collect(Collectors.toList());
    }

    public MedicoDto update(Long id, MedicoDto medicoDetails) {
        Optional<MedicoModel> optionalMedico = medicoRepository.findById(id);

        if (optionalMedico.isPresent()) {
            MedicoModel medicoModel = optionalMedico.get();

            // Mapeia os detalhes do DTO para o modelo, exceto a especialidade
            medicoModel.setId(id);
            medicoModel.setNome(medicoDetails.getNome());
            medicoModel.setCrm(medicoDetails.getCrm());

            // Lida com a especialidade separadamente
            if (medicoDetails.getEspecialidade() != null) {
                Optional<EspecialidadeModel> optionalEspecialidade = especialidadeRepository.findById(medicoDetails.getEspecialidade().getId());
                optionalEspecialidade.ifPresent(medicoModel::setEspecialidade);
            }

<<<<<<< Updated upstream
            MedicoModel updatedMedico = medicoRepository.save(medicoModel);
            return modelMapper.map(updatedMedico, MedicoDto.class);
=======
            //Realiza o mesmo procedimento porem com o medico DTO
            MedicoModel updatedmedico = medicoRepository.save(medicoModel);
            MedicoDto medicoDto = new MedicoDto();
            medicoDto.setId(updatedmedico.getId());
            medicoDto.setNome(updatedmedico.getNome());
            medicoDto.setCrm(updatedmedico.getCrm());

            //Aqui lida com o save da especialidade
            if (updatedmedico.getEspecialidade() != null){
                EspecialidadeDto especialidadeDto = new EspecialidadeDto();
                especialidadeDto.setId(updatedmedico.getEspecialidade().getId());
                especialidadeDto.setNome(updatedmedico.getEspecialidade().getNome());
                medicoDto.setEspecialidade(especialidadeDto);
            }

>>>>>>> Stashed changes
        } else {
            return null;
        }
    }

    public MedicoDto save(MedicoCreateRequest medicoRequest) {
        MedicoModel medicoModel = modelMapper.map(medicoRequest, MedicoModel.class);

        if (medicoRequest.getEspecialidade() != null) {
            Optional<EspecialidadeModel> optionalEspecialidade = especialidadeRepository.findById(medicoRequest.getEspecialidade().getId());
            optionalEspecialidade.ifPresent(medicoModel::setEspecialidade);
        }

        MedicoModel savedMedico = medicoRepository.save(medicoModel);
        return modelMapper.map(savedMedico, MedicoDto.class);
    }

    public MedicoDto findById(Long id) {
        Optional<MedicoModel> optionalMedico = medicoRepository.findById(id);
        return optionalMedico.map(medicoModel -> modelMapper.map(medicoModel, MedicoDto.class)).orElse(null);
    }
}
