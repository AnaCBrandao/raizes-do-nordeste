package backend.application.dto;

import java.time.LocalDateTime;

public class AtualizarStatusPedidoResponseDTO {

    private Long pedidoId;
    private String statusAnterior;
    private String statusAtual;
    private LocalDateTime dataAtualizacao;

    public AtualizarStatusPedidoResponseDTO() {
    }

    public AtualizarStatusPedidoResponseDTO(
            Long pedidoId,
            String statusAnterior,
            String statusAtual,
            LocalDateTime dataAtualizacao) {

        this.pedidoId = pedidoId;
        this.statusAnterior = statusAnterior;
        this.statusAtual = statusAtual;
        this.dataAtualizacao = dataAtualizacao;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getStatusAnterior() {
        return statusAnterior;
    }

    public void setStatusAnterior(String statusAnterior) {
        this.statusAnterior = statusAnterior;
    }

    public String getStatusAtual() {
        return statusAtual;
    }

    public void setStatusAtual(String statusAtual) {
        this.statusAtual = statusAtual;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}