package backend.mapper;

import backend.dto.UsuarioRequestDTO;
import backend.dto.UsuarioResponseDTO;
import backend.model.Usuario;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuario;
    }

    public static UsuarioResponseDTO toDTO(Usuario entity) {
        return new UsuarioResponseDTO(
            entity.getId(),
            entity.getNome(),
            entity.getEmail(),
            entity.getDataCriacao()
        );
    }
}