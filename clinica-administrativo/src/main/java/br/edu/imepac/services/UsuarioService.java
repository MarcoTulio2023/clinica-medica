package br.edu.imepac.services;

import br.edu.imepac.dtos.UsuarioCreateRequest;
import br.edu.imepac.dtos.UsuarioDto;
import br.edu.imepac.models.UsuarioModel;
import br.edu.imepac.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<UsuarioDto> findAll() {
        List<UsuarioModel> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(usuario -> modelMapper.map(usuario, UsuarioDto.class)).collect(Collectors.toList());
    }

    public UsuarioDto update(Long id, UsuarioDto usuarioDetails) {
        Optional<UsuarioModel> optionalUsuario = usuarioRepository.findById(id);

        if (optionalUsuario.isPresent()) {
            UsuarioModel usuarioModel = optionalUsuario.get();
            usuarioModel.setNome(usuarioDetails.getNome());
            usuarioModel.setSenha(usuarioDetails.getSenha());
            UsuarioModel updatedUsuario = usuarioRepository.save(usuarioModel);
            return modelMapper.map(updatedUsuario, UsuarioDto.class);
        } else {
            return null;
        }
    }

    public UsuarioDto save(UsuarioCreateRequest usuarioRequest) {
        UsuarioModel usuarioModel = modelMapper.map(usuarioRequest, UsuarioModel.class);
        UsuarioModel savedUsuario = usuarioRepository.save(usuarioModel);
        return modelMapper.map(savedUsuario, UsuarioDto.class);
    }

    public UsuarioDto findById(Long id) {
        Optional<UsuarioModel> optionalUsuario = usuarioRepository.findById(id);
        return optionalUsuario.map(usuarioModel -> modelMapper.map(usuarioModel, UsuarioDto.class)).orElse(null);
    }
}
