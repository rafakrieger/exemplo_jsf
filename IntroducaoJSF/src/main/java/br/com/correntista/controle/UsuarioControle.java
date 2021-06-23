package br.com.correntista.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.hibernate.*;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

import br.com.correntista.dao.HibernateUtil;
import br.com.correntista.dao.PerfilDao;
import br.com.correntista.dao.PerfilDaoImpl;
import br.com.correntista.dao.UsuarioDao;
import br.com.correntista.dao.UsuarioDaoImpl;
import br.com.correntista.entidade.Perfil;
import br.com.correntista.entidade.Usuario;
import br.com.correntista.util.UtilGerador;

@ManagedBean(name="usuarioC")
@ViewScoped
public class UsuarioControle {
	
	private Usuario usuario;
	private Perfil perfil;
	private UsuarioDao usuarioDao;
	private Session sessao;
	private List<Usuario> usuarios;
	private List<SelectItem> comboPerfis;
	private DataModel<Usuario> modelUsuarios;
	private int aba;
	
	public UsuarioControle() {
		usuarioDao = new UsuarioDaoImpl();
	}
	
	public void pesquisarPorNome() {
		sessao = HibernateUtil.abrirSessao();
		try {
			usuarios = usuarioDao.pesquisarPorNome(usuario.getNome(), sessao);
			modelUsuarios = new ListDataModel<>(usuarios);
			usuario.setNome(null);
		} catch (HibernateException e) {
			System.out.println("Erro ao pesquisar: " + e.getMessage());
		} finally {
			sessao.close();
		}
	}
	
	public void loadComboPerfil() {
		sessao = HibernateUtil.abrirSessao();
		PerfilDao perfilDao = new PerfilDaoImpl();
		try {
			List<Perfil> perfis = perfilDao.pesquisarTodos(sessao);
			comboPerfis = new ArrayList<>();
			for (Perfil perfil : perfis) {
				comboPerfis.add(new SelectItem(perfil.getId(), perfil.getNome()));
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
			usuario.setSenha(UtilGerador.gerarNumero(5));
			usuario.setPerfil(perfil);
			usuarioDao.salvarOuAlterar(usuario, sessao);
			usuario = null;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", 
					null));
			modelUsuarios = null;
		} catch (HibernateException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar", 
					null));
		} finally {
			sessao.close();
		}
	}
	
	public void excluir() {		
		usuario = modelUsuarios.getRowData();		
		sessao = HibernateUtil.abrirSessao();
		try {
			usuarioDao.excluir(usuario, sessao);
			usuario = null;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído com sucesso", 
					null));
			modelUsuarios = null;
		} catch (HibernateException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir", 
					null));
		} finally {
			sessao.close();
		}		
	}
	
	@SuppressWarnings("rawtypes")
	public void onTabChange(TabChangeEvent event) {		
		if (event.getTab().getTitle().equals("Novo")) {
			loadComboPerfil();
		}
    }

    @SuppressWarnings("rawtypes")
	public void onTabClose(TabCloseEvent event) {
        
    }
	
	public void prepAlterar() {
		usuario = modelUsuarios.getRowData();
		aba = 1;			
	}
	
	
	// GET SET
	
	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}
	

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	


	public DataModel<Usuario> getModelUsuarios() {
		return modelUsuarios;
	}

	public int getAba() {
		return aba;
	}

	public List<SelectItem> getComboPerfis() {
		return comboPerfis;
	}

	
}
