package br.com.correntista.entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "cliente")
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String email;
    
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Endereco endereco;
    
    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private List<Cartao> cartaos;

    public Cliente() {
    }

    public Cliente(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Cartao> getCartaos() {
        return cartaos;
    }

    public void setCartaos(List<Cartao> cartaos) {
        this.cartaos = cartaos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.correntista.entidade.Cliente[ id=" + id + " ]";
    }
    
}
