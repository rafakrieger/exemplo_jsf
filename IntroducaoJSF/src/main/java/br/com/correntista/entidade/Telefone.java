package br.com.correntista.entidade;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "telefone")
public class Telefone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String numero;
    @Column(nullable = false)
    private String contato;
    private String tipo;
    
    @ManyToOne
    @JoinColumn(name = "id_pj")
    private PessoaJuridica pj;

    public Telefone() {
    }
    
    public Telefone(Long id, String numero, String contato, String tipo) {
		super();
		this.id = id;
		this.numero = numero;
		this.contato = contato;
		this.tipo = tipo;
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

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public PessoaJuridica getPj() {
		return pj;
	}

	public void setPj(PessoaJuridica pj) {
		this.pj = pj;
	}

	@Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Telefone other = (Telefone) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "Telefone [id=" + id + ", numero=" + numero + ", contato=" + contato + ", tipo=" + tipo + ", pj=" + pj
				+ "]";
	}
    
}
