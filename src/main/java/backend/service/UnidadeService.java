package backend.service;

import backend.dto.ProdutoUnidadeResponseDTO;
import backend.enums.CategoriaProduto;
import backend.model.Produto;
import backend.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnidadeService {

    private final ProdutoRepository produtoRepository;

    public UnidadeService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoUnidadeResponseDTO> buscarProdutosPorUnidade(Long unidadeId, String categoriaStr) {
        CategoriaProduto categoria = null;
        if (categoriaStr != null && !categoriaStr.isBlank()) {
            categoria = CategoriaProduto.valueOf(categoriaStr.toUpperCase());
        }

        List<Produto> produtos = produtoRepository.findProdutosAtivosPorUnidadeECategoria(unidadeId, categoria);

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