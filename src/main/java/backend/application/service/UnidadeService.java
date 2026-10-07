package backend.application.service;

import backend.application.dto.ProdutoUnidadeResponseDTO;
import backend.domain.enums.CategoriaProduto;
import backend.domain.model.Produto;
import backend.infrastructure.repository.ProdutoRepository;
import backend.infrastructure.repository.UnidadeRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnidadeService {

  private final ProdutoRepository produtoRepository;
  private final UnidadeRepository unidadeRepository;

  public UnidadeService(
      ProdutoRepository produtoRepository,
      UnidadeRepository unidadeRepository) {

      this.produtoRepository = produtoRepository;
      this.unidadeRepository = unidadeRepository;
  }

    public List<ProdutoUnidadeResponseDTO> buscarProdutosPorUnidade(
        Long unidadeId,
        String categoriaStr) {

    if (!unidadeRepository.existsById(unidadeId)) {
      throw new ResponseStatusException(
              HttpStatus.NOT_FOUND,
              "Unidade não encontrada"
      );
    }

    CategoriaProduto categoria = null;

    if (categoriaStr != null && !categoriaStr.isBlank()) {
        categoria = CategoriaProduto.valueOf(categoriaStr.toUpperCase());
    }

    List<Produto> produtos =
            produtoRepository.findProdutosAtivosPorUnidadeECategoria(
                    unidadeId, categoria);

    return produtos.stream()
            .map(p -> new ProdutoUnidadeResponseDTO(
                    p.getId(),
                    p.getNome(),
                    p.getDescricao(),
                    p.getPreco(),
                    p.getCategoria().name(),
                    p.getEstoque()
            ))
            .collect(Collectors.toList());
}
}