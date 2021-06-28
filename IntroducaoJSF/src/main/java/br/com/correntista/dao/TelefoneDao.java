package br.com.correntista.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.correntista.entidade.Telefone;

public interface TelefoneDao {
	
	void remover(Telefone telefone, Session sessao) throws HibernateException;
}
