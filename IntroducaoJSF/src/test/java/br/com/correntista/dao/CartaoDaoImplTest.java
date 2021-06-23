/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.dao;

import br.com.correntista.entidade.Cartao;
import br.com.correntista.entidade.Cliente;
import br.com.correntista.entidade.Endereco;
import br.com.correntista.entidade.PessoaFisica;
import br.com.correntista.entidade.PessoaJuridica;
import static br.com.correntista.util.UtilGerador.*;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Silvio
 */
public class CartaoDaoImplTest {

    private Cartao cartao;
    private CartaoDao cartaoDao;
    private Session sessao;

    public CartaoDaoImplTest() {
        cartaoDao = new CartaoDaoImpl();
    }

//    @Test
    public void testSalvarComPessoaFisica() {
        System.out.println("salvar cartão pessoa física");
        PessoaFisicaDaoImplTest fisicaTeste = new PessoaFisicaDaoImplTest();
        PessoaFisica pessoaFisica = fisicaTeste.buscaPessoaFisicaBD();

        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);// retorna o ano atual em inteiro
        String anoVencimento = Integer.toString((anoAtual + 4));// somando + 4 anos no ano atual(não é obrigatório)
        cartao = new Cartao(null, gerarCartaoCredito(), "master", anoVencimento);
        cartao.setCliente(pessoaFisica);
        sessao = HibernateUtil.abrirSessao();
        cartaoDao.salvarOuAlterar(cartao, sessao);
        sessao.close();
        assertNotNull(cartao.getId());
    }

//    @Test
    public void testSalvarComPessoaJuridica() {
        System.out.println("salvar cartão com pessoa jurídica");
        PessoaJuridicaDaoImplTest juridicaTeste = new PessoaJuridicaDaoImplTest();
        PessoaJuridica pessoaJuridica = juridicaTeste.buscaPessoaJuridicaBD();

        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);// retorna o ano atual em inteiro
        String anoVencimento = Integer.toString((anoAtual + 4));// somando + 4 anos no ano atual(não é obrigatório)
        cartao = new Cartao(null, gerarCartaoCredito(), "master", anoVencimento);
        cartao.setCliente(pessoaJuridica);
        sessao = HibernateUtil.abrirSessao();
        cartaoDao.salvarOuAlterar(cartao, sessao);
        sessao.close();
        assertNotNull(cartao.getId());//o valor para o numero do cartão esta indo muito grande
    }

//    @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarCartaoPessoaFisicaBD();
        cartao.setNumero(gerarCartaoCredito());
        sessao = HibernateUtil.abrirSessao();
        cartaoDao.salvarOuAlterar(cartao, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirSessao();
        Cartao cartaoAlt = cartaoDao.pesquisarPorId(cartao.getId(), sessao);
        sessao.close();
        assertEquals(cartao.getNumero(), cartaoAlt.getNumero());

    }

//    @Test
    public void testExcluir() {
        System.out.println("excluir");// só excluir o cartão, não a pessoa fisica
        buscarCartaoPessoaFisicaBD();
        sessao = HibernateUtil.abrirSessao();
        cartaoDao.excluir(cartao, sessao);
        Cartao cartaoAlt = cartaoDao.pesquisarPorId(cartao.getId(), sessao);
        sessao.close();
        assertNull(cartaoAlt);
    }

    @Test
    public void testPesquisarPorNumero() {
        System.out.println("pesquisarPorNumero");
        buscarCartaoPessoaFisicaBD();
        sessao = HibernateUtil.abrirSessao();
        Cartao cartaoPorNumero = cartaoDao.pesquisarPorNumero(cartao.getNumero(), sessao);
        sessao.close();
        Cliente cliente = cartaoPorNumero.getCliente();
        Endereco endereco = cliente.getEndereco();
        assertNotNull(cartaoPorNumero);
        assertNotNull(cliente);// ou assertNotNull(cartaoPorNumero.getPessoaFisica())
        assertNotNull(endereco);//ou assertNotNull(cartaoPorNumero.getPessoaFisica().getEndereco())
    }    // tomar cuidado se esqueceu de gravar algum cliente (pessoa fisica)sem endereço na hora do teste do cliente

    public Cartao buscarCartaoPessoaFisicaBD() {
        sessao = HibernateUtil.abrirSessao();
        Query consulta = sessao.createQuery("from Cartao");
        @SuppressWarnings("unchecked")
		List<Cartao> cartaos = consulta.list();
        sessao.close();
        if (cartaos.isEmpty()) {
            testSalvarComPessoaFisica();
        } else {
            cartao = cartaos.get(0);
        }
        return cartao;
    }

}
