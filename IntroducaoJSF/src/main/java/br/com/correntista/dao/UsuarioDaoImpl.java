/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.*;

import br.com.correntista.entidade.Usuario;

public class UsuarioDaoImpl extends BaseDaoImpl<Usuario, Long>
                                               implements UsuarioDao, Serializable{

	private static final long serialVersionUID = 1L;

	@Override
    public Usuario pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Usuario) sessao.get(Usuario.class, id);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Usuario> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Usuario where nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }
}
