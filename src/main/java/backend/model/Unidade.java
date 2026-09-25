package backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "unidades")
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String endereco;

    private Boolean ativa;

    public Unidade() {}

    public Unidade(Long id, String nome, String endereco, Boolean ativa) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.ativa = ativa;
    }


    @OneToMany(mappedBy = "unidade", cascade = CascadeType.ALL)
    private List<Estoque> estoques = new ArrayList<>();

    public List<Estoque> consultaEstoque() {
        return this.estoques;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }
}