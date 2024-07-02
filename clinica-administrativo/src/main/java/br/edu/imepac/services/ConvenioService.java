package br.edu.imepac.services;

import br.edu.imepac.dtos.ConvenioCreateRequest;
import br.edu.imepac.dtos.ConvenioDto;
import br.edu.imepac.models.ConvenioModel;
import br.edu.imepac.repositories.ConvenioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConvenioService {

    @Autowired
    private ConvenioRepository convenioRepository;

    public void delete(Long id) {
        convenioRepository.deleteById(id);
    }

    public List<ConvenioDto> findAll() {
        List<ConvenioModel> convenios = convenioRepository.findAll();
        return convenios.stream().map(convenio -> {
            ConvenioDto convenioDto = new ConvenioDto();
            convenioDto.setId(convenio.getId());
            convenioDto.setNome(convenio.getNome());
            convenioDto.setTipoPlano(convenio.getTipoPlano());
            convenioDto.setCobertura(convenio.getCobertura());
            convenioDto.setInformacoesContato(convenio.getInformacoesContato());
            return convenioDto;
        }).collect(Collectors.toList());
    }

    public ConvenioDto update(Long id, ConvenioDto convenioDetails) {
        Optional<ConvenioModel> optionalConvenio = convenioRepository.findById(id);

        if (optionalConvenio.isPresent()) {
            ConvenioModel convenioModel = optionalConvenio.get();
            convenioModel.setNome(convenioDetails.getNome());
            convenioModel.setTipoPlano(convenioDetails.getTipoPlano());
            convenioModel.setCobertura(convenioDetails.getCobertura());
            convenioModel.setInformacoesContato(convenioDetails.getInformacoesContato());

            ConvenioModel updatedConvenio = convenioRepository.save(convenioModel);

            ConvenioDto convenioDto = new ConvenioDto();
            convenioDto.setId(updatedConvenio.getId());
            convenioDto.setNome(updatedConvenio.getNome());
            convenioDto.setTipoPlano(updatedConvenio.getTipoPlano());
            convenioDto.setCobertura(updatedConvenio.getCobertura());
            convenioDto.setInformacoesContato(updatedConvenio.getInformacoesContato());

            return convenioDto;
        } else {
            return null;
        }
    }

    public ConvenioDto save(ConvenioCreateRequest convenioRequest) {
        ConvenioModel convenioModel = new ConvenioModel();
        convenioModel.setNome(convenioRequest.getNome());
        convenioModel.setTipoPlano(convenioRequest.getTipoPlano());
        convenioModel.setCobertura(convenioRequest.getCobertura());
        convenioModel.setInformacoesContato(convenioRequest.getInformacoesContato());

        ConvenioModel savedConvenio = convenioRepository.save(convenioModel);

        ConvenioDto convenioDto = new ConvenioDto();
        convenioDto.setId(savedConvenio.getId());
        convenioDto.setNome(savedConvenio.getNome());
        convenioDto.setTipoPlano(savedConvenio.getTipoPlano());
        convenioDto.setCobertura(savedConvenio.getCobertura());
        convenioDto.setInformacoesContato(savedConvenio.getInformacoesContato());

        log.info("Convenio {} foi salvo com sucesso.", convenioRequest.getNome());
        return convenioDto;
    }

    public ConvenioDto findById(Long id) {
        Optional<ConvenioModel> optionalConvenio = convenioRepository.findById(id);
        if (optionalConvenio.isPresent()) {
            ConvenioModel convenioModel = optionalConvenio.get();
            ConvenioDto convenioDto = new ConvenioDto();
            convenioDto.setId(convenioModel.getId());
            convenioDto.setNome(convenioModel.getNome());
            convenioDto.setTipoPlano(convenioModel.getTipoPlano());
            convenioDto.setCobertura(convenioModel.getCobertura());
            convenioDto.setInformacoesContato(convenioModel.getInformacoesContato());
            return convenioDto;
        } else {
            return null;
        }
    }
}

