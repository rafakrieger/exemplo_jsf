package br.com.correntista.entidade;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "pessoa_juridica")
@PrimaryKeyJoinColumn(name = "id_cliente")
public class PessoaJuridica extends Cliente {

	private static final long serialVersionUID = 1L;
	@Column(nullable = false, unique = true)
	private String cnpj;
	@Column(nullable = false)
	private String inscricao_estadual;
	
	@OneToMany(mappedBy = "pj", cascade=CascadeType.ALL)
    private List<Telefone> telefones;

	public PessoaJuridica() {
	}

	public PessoaJuridica(Long id, String nome, String email, String cnpj, String inscricao_estadual) {
		super(id, nome, email);
		this.cnpj = cnpj;
		this.inscricao_estadual = inscricao_estadual;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricao_estadual() {
		return inscricao_estadual;
	}

	public void setInscricao_estadual(String inscricao_estadual) {
		this.inscricao_estadual = inscricao_estadual;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	

}
