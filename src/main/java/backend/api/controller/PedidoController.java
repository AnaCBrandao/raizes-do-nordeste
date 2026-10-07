package backend.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import backend.application.dto.PedidoRequestDTO;
import backend.infrastructure.repository.UsuarioRepository;
import backend.application.service.PedidoService;
import backend.domain.enums.StatusPedido;
import backend.domain.model.Pedido;
import backend.domain.model.Usuario;
import backend.application.dto.AtualizarStatusPedidoRequestDTO;
import backend.application.dto.AtualizarStatusPedidoResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v3/pedidos")
@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoService.listarTodos();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {

        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/usuario/{usuarioId}")
    public List<Pedido> buscarPorUsuario(
            @PathVariable Long usuarioId) {

        return pedidoService.buscarPorUsuario(usuarioId);
    }


    @GetMapping("/status/{status}")
    public List<Pedido> buscarPorStatus(
            @PathVariable StatusPedido status) {

        return pedidoService.buscarPorStatus(status);
    }


    @PostMapping
    public ResponseEntity<Pedido> criar(
            @RequestBody PedidoRequestDTO request,
            Authentication authentication) {

        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário autenticado não encontrado"
                        )
                );

        Pedido novoPedido = pedidoService.criar(
                request,
                usuario
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novoPedido);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        pedidoService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{pedidoId}/status")
    public ResponseEntity<AtualizarStatusPedidoResponseDTO> atualizarStatus(
            @PathVariable Long pedidoId,
            @RequestBody AtualizarStatusPedidoRequestDTO request) {

        AtualizarStatusPedidoResponseDTO response =
                pedidoService.atualizarStatus(pedidoId, request);

        return ResponseEntity.ok(response);
    }
}