package br.com.correntista.dao;

import static br.com.correntista.util.UtilGerador.gerarCaracter;
import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import br.com.correntista.entidade.Perfil;

public class PerfilDaoImplTest {

	private Perfil perfil;
	private PerfilDao perfilDao;
	private Session sessao;

	public PerfilDaoImplTest() {
        perfilDao = new PerfilDaoImpl();
    }

	@Test
	public void testSalvar() {
		System.out.println("salvar");
		perfil = new Perfil(null, "perfil " + gerarCaracter(8), gerarCaracter(18));
		sessao = HibernateUtil.abrirSessao();
		perfilDao.salvarOuAlterar(perfil, sessao);
		sessao.close();
		assertNotNull(perfil.getId());
	}

	@Test
	public void testAlterar() {
		System.out.println("alterar");
		buscarPerfilBD();
		perfil.setDescricao(gerarCaracter(18));
		sessao = HibernateUtil.abrirSessao();
		perfilDao.salvarOuAlterar(perfil, sessao);
		sessao.close();

		sessao = HibernateUtil.abrirSessao();
		Perfil perfilAlt = perfilDao.pesquisarPorId(perfil.getId(), sessao);
		sessao.close();
		assertEquals(perfil.getDescricao(), perfilAlt.getDescricao());
	}

	@Test
	public void testExcluir() {
		System.out.println("excluir");
		buscarPerfilBD();
		sessao = HibernateUtil.abrirSessao();
		perfilDao.excluir(perfil, sessao);
		Perfil perfilExc = perfilDao.pesquisarPorId(perfil.getId(), sessao);
		sessao.close();
		assertNull(perfilExc);
	}

	@Test
	public void testPesquisarPorID() {
		System.out.println("pesquisarPorid");
		buscarPerfilBD();
		sessao = HibernateUtil.abrirSessao();
		Perfil perfilId = perfilDao.pesquisarPorId(perfil.getId(), sessao);
		sessao.close();
		assertNotNull(perfilId);
	}

	@Test
	public void testPesquisarPorNome() {
		System.out.println("pesquisarPorNome");
		buscarPerfilBD();
		sessao = HibernateUtil.abrirSessao();
		List<Perfil> perfils = perfilDao.pesquisarPorNome(perfil.getNome().substring(0, 4), sessao);
		sessao.close();
		assertTrue(perfils.size() > 0);
	}

	public Perfil buscarPerfilBD() {
		sessao = HibernateUtil.abrirSessao();
		Query consulta = sessao.createQuery("from Perfil");
		@SuppressWarnings("unchecked")
		List<Perfil> perfils = consulta.list();
		sessao.close();
		if (perfils.isEmpty()) {
			testSalvar();
		} else {
			perfil = perfils.get(0);
		}
		return perfil;
	}

}
