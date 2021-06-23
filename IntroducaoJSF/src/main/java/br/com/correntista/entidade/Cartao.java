package br.com.correntista.entidade;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "cartao")
public class Cartao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 19)
    private String numero;
    @Column(nullable = false)
    private String bandeira;
    @Column(nullable = false, length = 4)
    private String anoValidade;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    public Cartao() {
    }

    public Cartao(Long id, String numero, String bandeira, String anoValidade) {
        this.id = id;
        this.numero = numero;
        this.bandeira = bandeira;
        this.anoValidade = anoValidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getAnoValidade() {
        return anoValidade;
    }

    public void setAnoValidade(String anoValidade) {
        this.anoValidade = anoValidade;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cartao)) {
            return false;
        }
        Cartao other = (Cartao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.correntista.entidade.Cartao[ id=" + id + " ]";
    }

}
