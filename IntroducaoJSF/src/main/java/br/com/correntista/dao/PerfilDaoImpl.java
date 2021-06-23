/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.dao;

import br.com.correntista.entidade.Perfil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.*;

public class PerfilDaoImpl extends BaseDaoImpl<Perfil, Long>
                                               implements PerfilDao, Serializable{

	private static final long serialVersionUID = 1L;

	@Override
    public Perfil pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Perfil) sessao.get(Perfil.class, id);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Perfil> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Perfil where nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Perfil> pesquisarTodos(Session sessao) throws HibernateException {
		return sessao.createQuery("from Perfil").list();
	}
    
}
