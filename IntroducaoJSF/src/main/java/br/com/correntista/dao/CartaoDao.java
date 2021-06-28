package br.com.correntista.dao;

import br.com.correntista.entidade.Cartao;
import org.hibernate.*;

public interface CartaoDao extends BaseDao<Cartao, Long> {

    Cartao pesquisarPorNumero(String numero, Session session) throws HibernateException;
}
