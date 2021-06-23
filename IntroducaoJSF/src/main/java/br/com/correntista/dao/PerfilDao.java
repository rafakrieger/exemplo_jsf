package br.com.correntista.dao;

import br.com.correntista.entidade.Perfil;
import java.util.List;
import org.hibernate.*;

public interface PerfilDao extends BaseDao<Perfil, Long>{
    
    List<Perfil> pesquisarPorNome(String nome, Session sessao) throws HibernateException;
    List<Perfil> pesquisarTodos(Session sessao) throws HibernateException;
}
