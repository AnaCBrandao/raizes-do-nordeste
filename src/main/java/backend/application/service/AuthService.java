package backend.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import backend.application.dto.LoginRequestDTO;
import backend.application.dto.LoginResponseDTO;
import backend.application.dto.UsuarioResponseDTO;
import backend.domain.enums.PerfilEnum;
import backend.domain.model.Usuario;
import backend.infrastructure.repository.UsuarioRepository;
import backend.infrastructure.security.JwtTokenProvider;

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