package backend.application.dto;

import java.time.LocalDateTime;

public class PagamentoResponseDTO {

  private String transacaoId;
  private Long pedidoId;
  private String statusPagamento;
  private String statusPedidoAtualizado;
  private LocalDateTime dataProcessamento;

  public PagamentoResponseDTO() {
  }

  public PagamentoResponseDTO(
    String transacaoId,
    Long pedidoId,
    String statusPagamento,
    String statusPedidoAtualizado,
    LocalDateTime dataProcessamento) {

      this.transacaoId = transacaoId;
      this.pedidoId = pedidoId;
      this.statusPagamento = statusPagamento;
      this.statusPedidoAtualizado = statusPedidoAtualizado;
      this.dataProcessamento = dataProcessamento;
  }

  public String getTransacaoId() {
    return transacaoId;
  }

  public void setTransacaoId(String transacaoId) {
    this.transacaoId = transacaoId;
  }

  public Long getPedidoId() {
    return pedidoId;
  }

  public void setPedidoId(Long pedidoId) {
    this.pedidoId = pedidoId;
  }

  public String getStatusPagamento() {
    return statusPagamento;
  }

  public void setStatusPagamento(String statusPagamento) {
    this.statusPagamento = statusPagamento;
  }

  public String getStatusPedidoAtualizado() {
    return statusPedidoAtualizado;
  }

  public void setStatusPedidoAtualizado(String statusPedidoAtualizado) {
    this.statusPedidoAtualizado = statusPedidoAtualizado;
  }

  public LocalDateTime getDataProcessamento() {
    return dataProcessamento;
  }

  public void setDataProcessamento(LocalDateTime dataProcessamento) {
    this.dataProcessamento = dataProcessamento;
  }
}