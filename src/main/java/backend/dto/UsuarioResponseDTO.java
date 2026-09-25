package backend.dto;

import backend.enums.PerfilEnum;
import java.time.LocalDateTime;

public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private PerfilEnum perfil;
    private Boolean consentimento;
    private LocalDateTime dataCriacao;

    public UsuarioResponseDTO() {}

    public UsuarioResponseDTO(Long id, String nome, String email, PerfilEnum perfil, Boolean consentimento, LocalDateTime dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.perfil = perfil;
        this.consentimento = consentimento;
        this.dataCriacao = dataCriacao;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public PerfilEnum getPerfil() { return perfil; }
    public void setPerfil(PerfilEnum perfil) { this.perfil = perfil; }

    public Boolean getConsentimento() { return consentimento; }
    public void setConsentimento(Boolean consentimento) { this.consentimento = consentimento; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}