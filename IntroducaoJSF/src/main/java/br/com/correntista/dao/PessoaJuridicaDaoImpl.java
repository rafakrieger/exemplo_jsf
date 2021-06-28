package br.com.correntista.dao;


import br.com.correntista.entidade.PessoaJuridica;
import java.io.Serializable;
import java.util.List;
import org.hibernate.*;


public class PessoaJuridicaDaoImpl extends BaseDaoImpl<PessoaJuridica, Long>
                                               implements PessoaJuridicaDao, Serializable{

	private static final long serialVersionUID = 1L;

	@Override
    public PessoaJuridica pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (PessoaJuridica) sessao.get(PessoaJuridica.class, id);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<PessoaJuridica> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("select distinct(pj) from PessoaJuridica pj left join fetch pj.telefones"
                + " where nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public PessoaJuridica pesquisarPorCnpj(String cnpj, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from PessoaJuridica pj left join fetch pj.telefones"
                + " where cnpj = :cnpj");
        consulta.setParameter("cnpj",  cnpj);
        return (PessoaJuridica) consulta.uniqueResult();
                
    }
}
