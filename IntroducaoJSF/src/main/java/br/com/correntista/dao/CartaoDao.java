/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.dao;

import br.com.correntista.entidade.Cartao;
import org.hibernate.*;

/**
 *
 * @author Silvio
 */
public interface CartaoDao extends BaseDao<Cartao, Long> {

    Cartao pesquisarPorNumero(String numero, Session session) throws HibernateException;
}
