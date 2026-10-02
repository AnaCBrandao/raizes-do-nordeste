package backend.controller;

import backend.dto.PagamentoRequestDTO;
import backend.dto.PagamentoResponseDTO;
import backend.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v3/pedidos")
@Tag(
        name = "Pagamentos",
        description = "Endpoints para processamento de pagamentos"
)
public class PagamentoController {

    private final PedidoService pedidoService;

    public PagamentoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/{pedidoId}/pagamentos")
    @Operation(
            summary = "Processa um pagamento mock para um pedido"
    )
    public ResponseEntity<PagamentoResponseDTO> processarPagamento(
            @PathVariable Long pedidoId,
            @RequestBody PagamentoRequestDTO request) {

        PagamentoResponseDTO response =
                pedidoService.processarPagamento(pedidoId, request);

        return ResponseEntity.ok(response);
    }
}