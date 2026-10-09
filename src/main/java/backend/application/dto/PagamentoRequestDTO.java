package backend.application.dto;

import java.math.BigDecimal;

public class PagamentoRequestDTO {

  private String formaPagamento;
  private BigDecimal valor;

  public PagamentoRequestDTO() {
  }

  public PagamentoRequestDTO(String formaPagamento, BigDecimal valor) {
    this.formaPagamento = formaPagamento;
    this.valor = valor;
  }

  public String getFormaPagamento() {
    return formaPagamento;
  }

  public void setFormaPagamento(String formaPagamento) {
    this.formaPagamento = formaPagamento;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }
}