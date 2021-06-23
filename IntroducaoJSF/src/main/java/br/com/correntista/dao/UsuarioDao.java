
package br.com.correntista.dao;

import br.com.correntista.entidade.Usuario;

import java.util.List;
import org.hibernate.*;


public interface UsuarioDao extends BaseDao<Usuario, Long>{
    
    List<Usuario> pesquisarPorNome(String nome, Session sessao) throws HibernateException;
    
}
