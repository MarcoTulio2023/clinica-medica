package br.edu.imepac.services;

import br.edu.imepac.dtos.ConvenioCreateRequest;
import br.edu.imepac.dtos.ConvenioDto;
import br.edu.imepac.models.ConvenioModel;
import br.edu.imepac.repositories.ConvenioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConvenioService {

    @Autowired
    private ConvenioRepository convenioRepository;

    public void delete(Long id) {
        convenioRepository.deleteById(id);
    }

    public List<ConvenioDto> findAll() {
        List<ConvenioModel> convenios = convenioRepository.findAll();
        return convenios.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ConvenioDto save(ConvenioCreateRequest convenioRequest) {
        ConvenioModel convenioModel = convertToModel(convenioRequest);
        ConvenioModel savedConvenio = convenioRepository.save(convenioModel);
        return convertToDto(savedConvenio);
    }

    public ConvenioDto update(Long id, ConvenioDto convenioDetails) {
        Optional<ConvenioModel> optionalConvenio = convenioRepository.findById(id);

        if (optionalConvenio.isPresent()) {
            ConvenioModel convenioModel = optionalConvenio.get();
            convenioModel.setEmpresa_convenio(convenioDetails.getEmpresa_convenio());
            convenioModel.setCnpj(convenioDetails.getCnpj());
            convenioModel.setTelefone(convenioDetails.getTelefone());

            ConvenioModel updatedConvenio = convenioRepository.save(convenioModel);
            return convertToDto(updatedConvenio);
        } else {
            // Handle case when convenio with given id is not found
            // You can throw an exception or return an appropriate response
            return null;
        }
    }

    public ConvenioDto findById(Long id) {
        Optional<ConvenioModel> optionalConvenio = convenioRepository.findById(id);
        return optionalConvenio.map(this::convertToDto).orElse(null);
    }

    private ConvenioModel convertToModel(ConvenioCreateRequest request) {
        ConvenioModel convenioModel = new ConvenioModel();
        convenioModel.setEmpresa_convenio(request.getEmpresa_convenio());
        convenioModel.setCnpj(request.getCnpj());
        convenioModel.setTelefone(request.getTelefone());
        return convenioModel;
    }

    private ConvenioDto convertToDto(ConvenioModel convenio) {
        ConvenioDto convenioDto = new ConvenioDto();
        convenioDto.setCodigo_convenio(convenio.getCodigo_convenio());
        convenioDto.setEmpresa_convenio(convenio.getEmpresa_convenio());
        convenioDto.setCnpj(convenio.getCnpj());
        convenioDto.setTelefone(convenio.getTelefone());
        return convenioDto;
    }
}
