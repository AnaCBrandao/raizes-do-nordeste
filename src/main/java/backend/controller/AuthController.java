package backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.dto.LoginRequestDTO;
import backend.dto.LoginResponseDTO;
import backend.service.AuthService; 

@RestController
@RequestMapping("/api/v3/auth")
@Tag(name = "Autenticação", description = "Endpoints para autenticação e geração de token JWT")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Autentica o usuário e retorna o token JWT de acesso")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginDTO) {
        LoginResponseDTO response = authService.autenticar(loginDTO);
        return ResponseEntity.ok(response);
    }
}