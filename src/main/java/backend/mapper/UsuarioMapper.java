package backend.mapper;

import backend.dto.UsuarioRequestDTO;
import backend.dto.UsuarioResponseDTO;
import backend.model.Usuario;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setPerfil(dto.getPerfil());
        usuario.setConsentimento(dto.getConsentimento());
        return usuario;
    }

    public static UsuarioResponseDTO toDTO(Usuario entity) {
        if (entity == null) {
            return null;
        }

        return new UsuarioResponseDTO(
            entity.getId(),
            entity.getNome(),
            entity.getEmail(),
            entity.getPerfil(),
            entity.getConsentimento(),
            entity.getDataCriacao()
        );
    }
}