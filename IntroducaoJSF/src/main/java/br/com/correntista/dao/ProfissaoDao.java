package br.com.correntista.dao;

import br.com.correntista.entidade.Profissao;
import java.util.List;
import org.hibernate.*;

public interface ProfissaoDao extends BaseDao<Profissao, Long>{
    
    List<Profissao> pesquisarPorNome(String nome, Session sessao) throws HibernateException;
    List<Profissao> pesquisarTodos(Session sessao) throws HibernateException;
}
