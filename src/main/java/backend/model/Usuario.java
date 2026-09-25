package backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import backend.enums.PerfilEnum;

@Entity
@Table(name = "tb_usuarios")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String nome;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @Column(nullable = false)
  private String senha;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private PerfilEnum perfil;

  @Column(nullable = false)
  private Boolean consentimento;

  @Column(name = "data_criacao", nullable = false, updatable = false)
  private LocalDateTime dataCriacao;

  @PrePersist
  protected void onCreate() {
    this.dataCriacao = LocalDateTime.now();
    if (this.consentimento == null) {
      this.consentimento = false;
    }
  }

  public Usuario() {
  }

  public Usuario(Long id, String nome, String email, String senha, PerfilEnum perfil, Boolean consentimento) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.senha = senha;
    this.perfil = perfil;
    this.consentimento = consentimento;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public PerfilEnum getPerfil() {
    return perfil;
  }

  public void setPerfil(PerfilEnum perfil) {
    this.perfil = perfil;
  }

  public Boolean getConsentimento() {
    return consentimento;
  }

  public void setConsentimento(Boolean consentimento) {
    this.consentimento = consentimento;
  }

  public LocalDateTime getDataCriacao() {
    return dataCriacao;
  }

  public void setDataCriacao(LocalDateTime dataCriacao) {
    this.dataCriacao = dataCriacao;
  }
}