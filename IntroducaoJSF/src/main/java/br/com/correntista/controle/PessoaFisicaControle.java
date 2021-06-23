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
import javax.faces.model.SelectItem;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

import br.com.correntista.dao.HibernateUtil;
import br.com.correntista.dao.PessoaFisicaDao;
import br.com.correntista.dao.PessoaFisicaDaoImpl;
import br.com.correntista.dao.ProfissaoDao;
import br.com.correntista.dao.ProfissaoDaoImpl;
import br.com.correntista.entidade.Endereco;
import br.com.correntista.entidade.PessoaFisica;
import br.com.correntista.entidade.Profissao;
import br.com.correntista.webservice.WebServiceEndereco;

@ManagedBean(name = "pfisicaC")
@ViewScoped
public class PessoaFisicaControle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private PessoaFisica pf;
	private Profissao profissao;
	private PessoaFisicaDao pfDao;
	private Session sessao;
	private List<PessoaFisica> fisicas;
	private DataModel<PessoaFisica> modelFisicas;
	private List<SelectItem> comboProfissoes;
	private Endereco endereco;
	private int aba;

	public PessoaFisicaControle() {
		pfDao = new PessoaFisicaDaoImpl();
	}

	public void pesquisarPorNome() {
		sessao = HibernateUtil.abrirSessao();
		try {
			fisicas = pfDao.pesquisarPorNome(pf.getNome(), sessao);
			modelFisicas = new ListDataModel<>(fisicas);
			pf.setNome(null);
		} catch (HibernateException e) {
			System.out.println("Erro ao pesquisar: " + e.getMessage());
		} finally {
			sessao.close();
		}
	}

	public void loadComboProfissao() {
		sessao = HibernateUtil.abrirSessao();
		ProfissaoDao profissaoDao = new ProfissaoDaoImpl();
		try {
			List<Profissao> profissoes = profissaoDao.pesquisarTodos(sessao);
			comboProfissoes = new ArrayList<>();
			for (Profissao prof : profissoes) {
				comboProfissoes.add(new SelectItem(prof.getId(), prof.getNome()));
			}
		} catch (HibernateException e) {
			System.out.println("Erro ao carregar combo box: " + e.getMessage());
		} finally {
			sessao.close();
		}
	}

	public void salvar() {
		sessao = HibernateUtil.abrirSessao();
		try {
			endereco.setCliente(pf);
			pf.setEndereco(endereco);
			pf.setProfissao(profissao);
			pfDao.salvarOuAlterar(pf, sessao);
			endereco = null;
			profissao = null;
			pf = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", null));
			modelFisicas = null;
		} catch (HibernateException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar", null));
		} finally {
			sessao.close();
		}
	}

	public void excluir() {
		pf = modelFisicas.getRowData();
		sessao = HibernateUtil.abrirSessao();
		try {
			pfDao.excluir(pf, sessao);
			pf = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído com sucesso", null));
			modelFisicas = null;
		} catch (HibernateException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir", null));
		} finally {
			sessao.close();
		}
	}

	public void buscarCep() {
		WebServiceEndereco webService = new WebServiceEndereco();
		endereco = webService.pesquisarCep(endereco.getCep());
		if (endereco.getCep() == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "CEP não encontrado", null));
		}
	}

	public void prepAlterar() {
		pf = modelFisicas.getRowData();
		endereco = pf.getEndereco();
		profissao = pf.getProfissao();
		aba = 1;
	}

	@SuppressWarnings("rawtypes")
	public void onTabChange(TabChangeEvent event) {
		if (event.getTab().getTitle().equals("Novo")) {
			loadComboProfissao();
		}
	}

	@SuppressWarnings("rawtypes")
	public void onTabClose(TabCloseEvent event) {

	}

	// GET SET

	public PessoaFisica getPf() {
		if (pf == null) {
			pf = new PessoaFisica();
		}
		return pf;
	}

	public void setPf(PessoaFisica pf) {
		this.pf = pf;
	}

	public Profissao getProfissao() {
		if (profissao == null) {
			profissao = new Profissao();
		}
		return profissao;
	}

	public void setProfissao(Profissao profissao) {
		this.profissao = profissao;
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

	public int getAba() {
		return aba;
	}

	public DataModel<PessoaFisica> getModelFisicas() {
		return modelFisicas;
	}

	public List<SelectItem> getComboProfissoes() {
		return comboProfissoes;
	}

}
