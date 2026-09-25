package backend.controller;

import backend.dto.ProdutoUnidadeResponseDTO;
import backend.service.UnidadeService;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unidades")
@Tag(name = "Unidade", description = "Endpoints para gestão de unidades")
public class UnidadeController {

    private final UnidadeService unidadeService;

    public UnidadeController(UnidadeService unidadeService) {
        this.unidadeService = unidadeService;
    }

    @GetMapping("/{unidadeId}/produtos")
    public ResponseEntity<List<ProdutoUnidadeResponseDTO>> listarProdutosPorUnidade(
            @PathVariable Long unidadeId,
            @RequestParam(required = false) String categoria) {

        List<ProdutoUnidadeResponseDTO> produtos = unidadeService.buscarProdutosPorUnidade(unidadeId, categoria);
        return ResponseEntity.ok(produtos);
    }
}