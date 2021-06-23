package br.com.correntista.controle;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.hibernate.*;

import br.com.correntista.dao.HibernateUtil;
import br.com.correntista.dao.ProfissaoDao;
import br.com.correntista.dao.ProfissaoDaoImpl;
import br.com.correntista.entidade.Profissao;

@ManagedBean(name="profissaoC")
@ViewScoped
public class ProfissaoControle {
	
	private Profissao profissao;
	private ProfissaoDao profissaoDao;
	private Session sessao;
	private List<Profissao> profissoes;
	private DataModel<Profissao> modelProfissoes;
	private int aba;
	
	public ProfissaoControle() {
		profissaoDao = new ProfissaoDaoImpl();
	}
	
	public void pesquisarPorNome() {
		sessao = HibernateUtil.abrirSessao();
		try {
			profissoes = profissaoDao.pesquisarPorNome(profissao.getNome(), sessao);
			modelProfissoes = new ListDataModel<>(profissoes);
			profissao.setNome(null);
		} catch (HibernateException e) {
			System.out.println("Erro ao pesquisar: " + e.getMessage());
		} finally {
			sessao.close();
		}
	}
	
	public void salvar() {
		sessao = HibernateUtil.abrirSessao();
		try {
			profissaoDao.salvarOuAlterar(profissao, sessao);
			profissao = null;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", 
					null));
			modelProfissoes = null;
		} catch (HibernateException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar", 
					null));
		} finally {
			sessao.close();
		}
	}
	
	public void excluir() {		
		profissao = modelProfissoes.getRowData();		
		sessao = HibernateUtil.abrirSessao();
		try {
			profissaoDao.excluir(profissao, sessao);
			profissao = null;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído com sucesso", 
					null));
			modelProfissoes = null;
		} catch (HibernateException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir", 
					null));
		} finally {
			sessao.close();
		}		
	}
	
	public void prepAlterar() {
		profissao = modelProfissoes.getRowData();
		aba = 1;			
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

	public DataModel<Profissao> getModelProfissoes() {
		return modelProfissoes;
	}

	public int getAba() {
		return aba;
	}
	
}
