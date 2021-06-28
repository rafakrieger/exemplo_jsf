package br.com.correntista.dao;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.correntista.entidade.Telefone;

public class TelefoneDaoImpl implements TelefoneDao, Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void remover(Telefone telefone, Session sessao) throws HibernateException {
		Transaction transacao = sessao.beginTransaction();
        sessao.delete(telefone);
        transacao.commit();		
	}
}
