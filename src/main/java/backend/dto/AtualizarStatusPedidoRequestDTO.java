package backend.dto;

public class AtualizarStatusPedidoRequestDTO {

    private String novoStatus;

    public AtualizarStatusPedidoRequestDTO() {
    }

    public String getNovoStatus() {
        return novoStatus;
    }

    public void setNovoStatus(String novoStatus) {
        this.novoStatus = novoStatus;
    }
}