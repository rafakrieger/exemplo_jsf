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
import br.com.correntista.dao.PerfilDao;
import br.com.correntista.dao.PerfilDaoImpl;
import br.com.correntista.entidade.Perfil;

@ManagedBean(name="perfilC")
@ViewScoped
public class PerfilControle {
	
	private Perfil perfil;
	private PerfilDao perfilDao;
	private Session sessao;
	private List<Perfil> perfis;
	private DataModel<Perfil> modelPerfis;
	private int aba;
	
	public PerfilControle() {
		perfilDao = new PerfilDaoImpl();
	}
	
	public void pesquisarPorNome() {
		sessao = HibernateUtil.abrirSessao();
		try {
			perfis = perfilDao.pesquisarPorNome(perfil.getNome(), sessao);
			modelPerfis = new ListDataModel<>(perfis);
			perfil.setNome(null);
		} catch (HibernateException e) {
			System.out.println("Erro ao pesquisar: " + e.getMessage());
		} finally {
			sessao.close();
		}
	}
	
	public void salvar() {
		sessao = HibernateUtil.abrirSessao();
		try {
			perfilDao.salvarOuAlterar(perfil, sessao);
			perfil = null;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", 
					null));
		} catch (HibernateException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar", 
					null));
		} finally {
			sessao.close();
		}
	}
	
	public void excluir() {		
		perfil = modelPerfis.getRowData();		
		sessao = HibernateUtil.abrirSessao();
		try {
			perfilDao.excluir(perfil, sessao);
			perfil = null;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído com sucesso", 
					null));
			modelPerfis = null;
		} catch (HibernateException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir", 
					null));
		} finally {
			sessao.close();
		}		
	}
	
	public void prepAlterar() {
		perfil = modelPerfis.getRowData();
		aba = 1;			
	}

	public Perfil getPerfil() {
		if (perfil == null) {
			perfil = new Perfil();
		}
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public DataModel<Perfil> getModelPerfis() {
		return modelPerfis;
	}
	
	public int getAba() {
		return aba;
	}
	
}
