package br.edu.imepac.services;

import br.edu.imepac.dtos.EspecialidadeDto;
import br.edu.imepac.dtos.MedicoCreateRequest;
import br.edu.imepac.dtos.MedicoDto;
import br.edu.imepac.models.EspecialidadeModel;
import br.edu.imepac.models.MedicoModel;
import br.edu.imepac.repositories.EspecialidadeRepository;
import br.edu.imepac.repositories.MedicoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
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
            medicoModel.setNome(medicoDetails.getNome());
            medicoModel.setCrm(medicoDetails.getCrm());

            // Lida com a especialidade separadamente
            if (medicoDetails.getEspecialidade() != null) {
                Optional<EspecialidadeModel> optionalEspecialidade = especialidadeRepository.findById(medicoDetails.getEspecialidade().getId());
                optionalEspecialidade.ifPresent(medicoModel::setEspecialidade);
            }

            MedicoModel updatedmedico = medicoRepository.save(medicoModel);
            MedicoDto medicoDto = new MedicoDto();
            medicoDto.setId(updatedmedico.getId());
            medicoDto.setNome(updatedmedico.getNome());
            medicoDto.setCrm(updatedmedico.getCrm());
            if (updatedmedico.getEspecialidade() != null){
                EspecialidadeDto especialidadeDto = new EspecialidadeDto();
                especialidadeDto.setId(updatedmedico.getEspecialidade().getId());
                especialidadeDto.setNome(updatedmedico.getEspecialidade().getNome());
                medicoDto.setEspecialidade(especialidadeDto);
            }

        } else {
            return null;
        }
        return medicoDetails;
    }

    public MedicoDto save(MedicoCreateRequest medicoRequest) {
       MedicoModel medicoModel = new MedicoModel();
       medicoModel.setNome(medicoRequest.getNome());
       medicoModel.setCrm(medicoRequest.getCrm());
       if (medicoRequest.getEspecialidade() != null){
           Optional<EspecialidadeModel> optionalEspecialidade = especialidadeRepository.findById(medicoRequest.getEspecialidade().getId());
           optionalEspecialidade.ifPresent(medicoModel::setEspecialidade);
       }

        MedicoModel updatedmedico = medicoRepository.save(medicoModel);
        MedicoDto medicoDto = new MedicoDto();
        medicoDto.setId(updatedmedico.getId());
        medicoDto.setNome(updatedmedico.getNome());
        medicoDto.setCrm(updatedmedico.getCrm());
        if (updatedmedico.getEspecialidade() != null){
            EspecialidadeDto especialidadeDto = new EspecialidadeDto();
            especialidadeDto.setId(updatedmedico.getEspecialidade().getId());
            especialidadeDto.setNome(updatedmedico.getEspecialidade().getNome());
            medicoDto.setEspecialidade(especialidadeDto);

            log.info("Médico {} foi salvo com sucesso.", medicoRequest.getNome());
            return medicoDto;
        }

        return medicoDto;
    }

    public MedicoDto findById(Long id) {
        Optional<MedicoModel> optionalMedico = medicoRepository.findById(id);
        return optionalMedico.map(medicoModel -> modelMapper.map(medicoModel, MedicoDto.class)).orElse(null);
    }
}
