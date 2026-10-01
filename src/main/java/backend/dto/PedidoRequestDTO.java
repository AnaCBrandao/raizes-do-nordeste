package backend.dto;

import java.util.List;

public class PedidoRequestDTO {

    private Long unidadeId;
    private String canalPedido;
    private String formaPagamento;
    private List<ItemPedidoRequestDTO> itens;

    public Long getUnidadeId() {
        return unidadeId;
    }

    public void setUnidadeId(Long unidadeId) {
        this.unidadeId = unidadeId;
    }

    public String getCanalPedido() {
        return canalPedido;
    }

    public void setCanalPedido(String canalPedido) {
        this.canalPedido = canalPedido;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List<ItemPedidoRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoRequestDTO> itens) {
        this.itens = itens;
    }
}