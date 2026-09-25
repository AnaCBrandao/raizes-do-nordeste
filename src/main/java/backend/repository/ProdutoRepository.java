package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.enums.CategoriaProduto;
import backend.model.Produto;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoria(CategoriaProduto categoria);

    List<Produto> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT e.produto FROM Estoque e " +
           "WHERE e.unidade.id = :unidadeId " +
           "AND (:categoria IS NULL OR e.produto.categoria = :categoria)")
    List<Produto> findProdutosAtivosPorUnidadeECategoria(
            @Param("unidadeId") Long unidadeId, 
            @Param("categoria") CategoriaProduto categoria);
}