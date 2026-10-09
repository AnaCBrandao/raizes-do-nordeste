package backend.application.service;

import backend.infrastructure.repository.EstoqueRepository;
import backend.infrastructure.repository.PedidoRepository;
import backend.infrastructure.repository.ProdutoRepository;
import backend.infrastructure.repository.UnidadeRepository;
import backend.application.dto.AtualizarStatusPedidoRequestDTO;
import backend.application.dto.AtualizarStatusPedidoResponseDTO;
import backend.application.dto.ItemPedidoRequestDTO;
import backend.application.dto.PagamentoRequestDTO;
import backend.application.dto.PagamentoResponseDTO;
import backend.application.dto.PedidoRequestDTO;
import backend.domain.enums.CanalPedido;
import backend.domain.enums.FormaPagamento;
import backend.domain.enums.StatusPedido;
import backend.domain.model.Estoque;
import backend.domain.model.ItemPedido;
import backend.domain.model.Pedido;
import backend.domain.model.Produto;
import backend.domain.model.Unidade;
import backend.domain.model.Usuario;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {

  @Autowired
  private PedidoRepository pedidoRepository;

  @Autowired
  private ProdutoRepository produtoRepository;

  @Autowired
  private UnidadeRepository unidadeRepository;

  @Autowired
  private EstoqueRepository estoqueRepository;

  public List<Pedido> listarTodos() {
    return pedidoRepository.findAll();
  }

  public java.util.Optional<Pedido> buscarPorId(Long id) {
    return pedidoRepository.findById(id);
  }

  public List<Pedido> buscarPorUsuario(Long usuarioId) {
    return pedidoRepository.findByUsuarioId(usuarioId);
  }

  public List<Pedido> buscarPorStatus(StatusPedido status) {
    return pedidoRepository.findByStatus(status);
  }

  @Transactional
  public Pedido criar(PedidoRequestDTO request, Usuario usuario) {

    Unidade unidade = unidadeRepository.findById(request.getUnidadeId())
      .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND,
          "Unidade não encontrada"
      ));

      if (unidade.getAtiva() != null && !unidade.getAtiva()) {
        throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "A unidade está inativa"
        );
      }

      if (request.getItens() == null || request.getItens().isEmpty()) {
        throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "O pedido deve possuir pelo menos um item"
        );
      }

      CanalPedido canalPedido;

      try {
        canalPedido = CanalPedido.valueOf(
          request.getCanalPedido().toUpperCase()
        );
      } catch (IllegalArgumentException | NullPointerException e) {
        throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "Canal de pedido inválido"
        );
      }

      FormaPagamento formaPagamento;

      try {
        formaPagamento = FormaPagamento.valueOf(
          request.getFormaPagamento().toUpperCase()
        );
      } catch (IllegalArgumentException | NullPointerException e) {
        throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "Forma de pagamento inválida"
        );
      }

      Pedido pedido = new Pedido();

      pedido.setUsuario(usuario);
      pedido.setUnidade(unidade);
      pedido.setCanalPedido(canalPedido);
      pedido.setFormaPagamento(formaPagamento);
      pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
      pedido.setValorTotal(BigDecimal.ZERO);

      BigDecimal valorTotal = BigDecimal.ZERO;

      for (ItemPedidoRequestDTO itemRequest : request.getItens()) {

        if (itemRequest.getProdutoId() == null) {
          throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Produto não informado"
          );
        }

        if (itemRequest.getQuantidade() == null ||
          itemRequest.getQuantidade() <= 0) {

          throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "A quantidade deve ser maior que zero"
          );
        }

        Produto produto = produtoRepository
          .findById(itemRequest.getProdutoId())
          .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Produto não encontrado: "
              + itemRequest.getProdutoId()
          ));

        Estoque estoque = estoqueRepository
          .findByProdutoIdAndUnidadeId(
            produto.getId(),
            unidade.getId()
          )
          .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Produto " + produto.getId()
              + " não possui estoque na unidade "
              + unidade.getId()
          ));

          if (estoque.getQuantidade() < itemRequest.getQuantidade()) {
            throw new ResponseStatusException(
              HttpStatus.BAD_REQUEST,
              "Estoque insuficiente para o produto "
                + produto.getId()
                + ". Disponível: "
                + estoque.getQuantidade()
            );
          }

          BigDecimal subtotal = produto.getPreco()
            .multiply(
              BigDecimal.valueOf(
                itemRequest.getQuantidade()
              )
            );

          ItemPedido item = new ItemPedido();

          item.setPedido(pedido);
          item.setProduto(produto);
          item.setQuantidade(itemRequest.getQuantidade());
          item.setPrecoUnitario(produto.getPreco());
          item.setSubtotal(subtotal);

          pedido.adicionarItem(item);

          estoque.setQuantidade(
            estoque.getQuantidade()
            - itemRequest.getQuantidade()
          );

          estoqueRepository.save(estoque);

          valorTotal = valorTotal.add(subtotal);
      }


      pedido.setValorTotal(valorTotal);

      return pedidoRepository.save(pedido);
  }


  public void deletar(Long id) {
    pedidoRepository.deleteById(id);
  }

  @Transactional
  public PagamentoResponseDTO processarPagamento(
    Long pedidoId,
    PagamentoRequestDTO request) {

    Pedido pedido = pedidoRepository.findById(pedidoId)
      .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Pedido não encontrado"
      ));

    if (pedido.getStatus() != StatusPedido.AGUARDANDO_PAGAMENTO) {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "O pedido não está aguardando pagamento"
      );
    }

    if (request.getFormaPagamento() == null ||
      request.getFormaPagamento().isBlank()) {

      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "Forma de pagamento não informada"
      );
    }

    if (request.getValor() == null ||
      request.getValor().compareTo(BigDecimal.ZERO) <= 0) {

      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "O valor do pagamento deve ser maior que zero"
      );
    }

    boolean aprovado =
      request.getValor().compareTo(pedido.getValorTotal()) == 0;

    String transacaoId = "PAG-MOCK-" +
      java.util.UUID.randomUUID()
        .toString()
        .substring(0, 6)
        .toUpperCase();

    String statusPagamento;
    String statusPedido;

    if (aprovado) {
      statusPagamento = "APROVADO";
      pedido.setStatus(StatusPedido.EM_PREPARO);
      statusPedido = StatusPedido.EM_PREPARO.name();
    } else {
      statusPagamento = "RECUSADO";
      pedido.setStatus(StatusPedido.CANCELADO);
      statusPedido = StatusPedido.CANCELADO.name();
    }

    pedidoRepository.save(pedido);

    return new PagamentoResponseDTO(
      transacaoId,
      pedido.getId(),
      statusPagamento,
      statusPedido,
      java.time.LocalDateTime.now()
    );
  }

  @Transactional
  public AtualizarStatusPedidoResponseDTO atualizarStatus(
    Long pedidoId,
    AtualizarStatusPedidoRequestDTO request) {

    Pedido pedido = pedidoRepository.findById(pedidoId)
      .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Pedido não encontrado"
      ));

    if (request.getNovoStatus() == null ||
      request.getNovoStatus().isBlank()) {

      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "Novo status não informado"
      );
    }

    StatusPedido novoStatus;

    try {
      novoStatus = StatusPedido.valueOf(
        request.getNovoStatus().toUpperCase()
      );
    } catch (IllegalArgumentException e) {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "Status inválido: " + request.getNovoStatus()
      );
    }

    StatusPedido statusAnterior = pedido.getStatus();

    pedido.setStatus(novoStatus);

    pedidoRepository.save(pedido);

    return new AtualizarStatusPedidoResponseDTO(
      pedido.getId(),
      statusAnterior.name(),
      novoStatus.name(),
      java.time.LocalDateTime.now()
    );
  }
}