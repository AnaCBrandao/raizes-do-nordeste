package backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import backend.dto.UsuarioRequestDTO;
import backend.dto.UsuarioResponseDTO;
import backend.mapper.UsuarioMapper;
import backend.model.Usuario;
import backend.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioResponseDTO> listarTodos() {

        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioResponseDTO> buscarPorId(Long id) {

        return usuarioRepository.findById(id)
                .map(UsuarioMapper::toDTO);
    }

    public UsuarioResponseDTO salvar(UsuarioRequestDTO dto) {

        if (Boolean.FALSE.equals(dto.getConsentimento())) {
            throw new IllegalArgumentException(
                    "É necessário aceitar os termos de consentimento para se cadastrar."
            );
        }

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException(
                    "E-mail já cadastrado!"
            );
        }

        Usuario usuario = UsuarioMapper.toEntity(dto);

        String senhaHash = passwordEncoder.encode(usuario.getSenha());

        usuario.setSenha(senhaHash);

        Usuario salvo = usuarioRepository.save(usuario);

        return UsuarioMapper.toDTO(salvo);
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}