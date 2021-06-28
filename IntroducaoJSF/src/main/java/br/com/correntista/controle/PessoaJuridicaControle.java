package br.com.correntista.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import br.com.correntista.dao.HibernateUtil;
import br.com.correntista.dao.PessoaJuridicaDao;
import br.com.correntista.dao.PessoaJuridicaDaoImpl;
import br.com.correntista.dao.TelefoneDao;
import br.com.correntista.dao.TelefoneDaoImpl;
import br.com.correntista.entidade.Endereco;
import br.com.correntista.entidade.PessoaJuridica;
import br.com.correntista.entidade.Telefone;
import br.com.correntista.webservice.WebServiceEndereco;

@ManagedBean(name = "pjuridicaC")
@ViewScoped
public class PessoaJuridicaControle implements Serializable {

	private static final long serialVersionUID = 1L;
	private PessoaJuridica pj;
	private PessoaJuridicaDao pjDao;
	private Session sessao;
	private List<PessoaJuridica> juridicas;
	private DataModel<PessoaJuridica> modelJuridicas;
	private List<Telefone> telefones;
	private DataModel<Telefone> modelTelefones;
	private Endereco endereco;
	private Telefone telefone;
	private int aba;

	public PessoaJuridicaControle() {
		pjDao = new PessoaJuridicaDaoImpl();
	}

	public void pesquisarPorNome() {
		sessao = HibernateUtil.abrirSessao();
		try {
			juridicas = pjDao.pesquisarPorNome(pj.getNome(), sessao);
			modelJuridicas = new ListDataModel<>(juridicas);
			pj.setNome(null);
		} catch (HibernateException e) {
			System.out.println("Erro ao pesquisar: " + e.getMessage());
		} finally {
			sessao.close();
		}
	}

	public void salvar() {
		sessao = HibernateUtil.abrirSessao();
		try {		
			pj.setTelefones(telefones);
			endereco.setCliente(pj);			
			pj.setEndereco(endereco);
			pjDao.salvarOuAlterar(pj, sessao);
			endereco = null;
			pj = null;
			telefones = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", null));
			modelJuridicas = null;
			modelTelefones = null;
		} catch (HibernateException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar", null));
		} finally {
			sessao.close();
		}
	}


	public void adicionarTelefone() {
		if (telefones == null) {
			telefones = new ArrayList<>();
		}
		telefone.setPj(pj);
		telefones.add(telefone);
		modelTelefones = new ListDataModel<>(telefones);
		telefone = new Telefone();
	}

	public void apagarTelefone() {
		telefone = modelTelefones.getRowData();
		telefones.remove(telefone);
		if (telefone.getId() != null) {
			TelefoneDao telDao = new TelefoneDaoImpl();			
			try {
				sessao = HibernateUtil.abrirSessao();
				telDao.remover(telefone, sessao);
			} catch (HibernateException e) {
				System.out.println("Erro ao excluir telefone do banco: " + e.getMessage());
			} finally {
				sessao.close();
			}	
		}
	}

	public void excluir() {
		pj = modelJuridicas.getRowData();
		sessao = HibernateUtil.abrirSessao();
		try {
			pjDao.excluir(pj, sessao);
			pj = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído com sucesso", null));
			modelJuridicas = null;
		} catch (HibernateException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir", null));
		} finally {
			sessao.close();
		}
	}

	public void prepAlterar() {
		pj = modelJuridicas.getRowData();
		endereco = pj.getEndereco();
		telefones = pj.getTelefones();
		modelTelefones = new ListDataModel<>(telefones);
		aba = 1;
	}

	public void buscarCep() {
		WebServiceEndereco webService = new WebServiceEndereco();
		endereco = webService.pesquisarCep(endereco.getCep());
		if (endereco.getCep() == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "CEP não encontrado", null));
		}
	}

	// GET SET

	public PessoaJuridica getPj() {
		if (pj == null) {
			pj = new PessoaJuridica();
		}
		return pj;
	}

	public void setPj(PessoaJuridica pj) {
		this.pj = pj;
	}

	public Endereco getEndereco() {
		if (endereco == null) {
			endereco = new Endereco();
		}
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public DataModel<PessoaJuridica> getModelJuridicas() {
		return modelJuridicas;
	}

	public int getAba() {
		return aba;
	}

		public DataModel<Telefone> getModelTelefones() {
		return modelTelefones;
	}

	public Telefone getTelefone() {
		if (telefone == null) {
			telefone = new Telefone();
		}
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

}
