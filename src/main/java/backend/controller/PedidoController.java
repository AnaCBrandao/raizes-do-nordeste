package backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import backend.dto.PedidoRequestDTO;
import backend.model.Pedido;
import backend.model.Usuario;
import backend.repository.UsuarioRepository;
import backend.enums.StatusPedido;
import backend.service.PedidoService;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
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
}