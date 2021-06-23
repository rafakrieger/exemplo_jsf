package br.com.correntista.dao;

import br.com.correntista.entidade.Profissao;
import static br.com.correntista.util.UtilGerador.*;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProfissaoDaoImplTest {

    private Profissao profissao;
    private ProfissaoDao profissaoDao;
    private Session sessao;

    public ProfissaoDaoImplTest() {
        profissaoDao = new ProfissaoDaoImpl();
    }

    @Test
    public void testSalvar() {
        System.out.println("salvar");
        profissao = new Profissao(null, "profissão " + gerarCaracter(8), gerarCaracter(18));
        sessao = HibernateUtil.abrirSessao();
        profissaoDao.salvarOuAlterar(profissao, sessao);
        sessao.close();
        assertNotNull(profissao.getId());
    }

    @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarProfissaoBD();
        profissao.setDescricao(gerarCaracter(18));
        sessao = HibernateUtil.abrirSessao();
        profissaoDao.salvarOuAlterar(profissao, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirSessao();
        Profissao profissaoAlt = profissaoDao.pesquisarPorId(profissao.getId(), sessao);
        sessao.close();
        assertEquals(profissao.getDescricao(), profissaoAlt.getDescricao());
    }

    @Test
    public void testExcluir() {
        System.out.println("excluir");
        buscarProfissaoBD();
        sessao = HibernateUtil.abrirSessao();
        profissaoDao.excluir(profissao, sessao);
        Profissao profissaoExc = profissaoDao.pesquisarPorId(profissao.getId(), sessao);
        sessao.close();
        assertNull(profissaoExc);
    }

    @Test
    public void testPesquisarPorID() {
        System.out.println("pesquisarPorid");
        buscarProfissaoBD();
        sessao = HibernateUtil.abrirSessao();
        Profissao profissaoId = profissaoDao.pesquisarPorId(profissao.getId(), sessao);
        sessao.close();
        assertNotNull(profissaoId);
    }

    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscarProfissaoBD();
        sessao = HibernateUtil.abrirSessao();
        List<Profissao> profissaos = profissaoDao.pesquisarPorNome(profissao.getNome().substring(0, 4), sessao);
        sessao.close();
        assertTrue(profissaos.size() > 0);
    }

    public Profissao buscarProfissaoBD() {
        sessao = HibernateUtil.abrirSessao();
        Query consulta = sessao.createQuery("from Profissao");
        @SuppressWarnings("unchecked")
		List<Profissao> profissaos = consulta.list();
        sessao.close();
        if (profissaos.isEmpty()) {
            testSalvar();
        } else {
            profissao = profissaos.get(0);
        }
        return profissao;
    }


}
