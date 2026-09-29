package backend.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import backend.dto.LoginRequestDTO;
import backend.dto.LoginResponseDTO;
import backend.dto.UsuarioResponseDTO;
import backend.enums.PerfilEnum;
import backend.model.Usuario;
import backend.repository.UsuarioRepository;
import backend.security.JwtTokenProvider;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponseDTO autenticar(LoginRequestDTO request) {

        Usuario usuario = usuarioRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                    new RuntimeException("E-mail ou senha inválidos"));

        if (!passwordEncoder.matches(
                request.getSenha(),
                usuario.getSenha())) {

            throw new RuntimeException("E-mail ou senha inválidos");
        }

        String token = jwtTokenProvider.gerarToken(usuario.getEmail());

        UsuarioResponseDTO usuarioDTO = new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil(),
                usuario.getConsentimento(),
                usuario.getDataCriacao()
        );

        return new LoginResponseDTO(token, usuarioDTO);
    }
}