/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.dao;

import br.com.correntista.entidade.Cartao;
import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.*;

/**
 *
 * @author Silvio
 */
public class CartaoDaoImpl extends BaseDaoImpl<Cartao, Long> implements CartaoDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
    public Cartao pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Cartao) sessao.get(Cartao.class, id);
    }

    @Override
    public Cartao pesquisarPorNumero(String numero, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Cartao where numero = :numero");
        consulta.setParameter("numero", numero);
        return (Cartao) consulta.uniqueResult();
    }

}
