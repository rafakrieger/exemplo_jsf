/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.dao;

import br.com.correntista.entidade.PessoaFisica;
import java.util.List;
import org.hibernate.*;

/**
 *
 * @author Silvio
 */
public interface PessoaFisicaDao extends BaseDao<PessoaFisica, Long>{
    
    List<PessoaFisica> pesquisarPorNome(String nome, Session sessao) throws HibernateException;
    
    PessoaFisica pesquisarPorCpf(String cpf, Session sessao) throws HibernateException;
}
