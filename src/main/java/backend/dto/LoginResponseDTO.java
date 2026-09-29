package backend.dto;

public class LoginResponseDTO {

    private String token;
    private String tipoToken = "Bearer";
    private Long expiracaoEmSegundos = 86400L;
    private UsuarioResponseDTO usuario; 

    public LoginResponseDTO() {}

    public LoginResponseDTO(String token, UsuarioResponseDTO usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipoToken() {
        return tipoToken;
    }

    public void setTipoToken(String tipoToken) {
        this.tipoToken = tipoToken;
    }

    public Long getExpiracaoEmSegundos() {
        return expiracaoEmSegundos;
    }

    public void setExpiracaoEmSegundos(Long expiracaoEmSegundos) {
        this.expiracaoEmSegundos = expiracaoEmSegundos;
    }

    public UsuarioResponseDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponseDTO usuario) {
        this.usuario = usuario;
    }
}