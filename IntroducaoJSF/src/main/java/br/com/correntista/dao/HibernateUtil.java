package br.com.correntista.dao;

import br.com.correntista.entidade.*;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    
    static {
        try {
            Configuration cfg = new Configuration();
            cfg.addAnnotatedClass(Profissao.class);
            cfg.addAnnotatedClass(Cliente.class);
            cfg.addAnnotatedClass(Endereco.class);
            cfg.addAnnotatedClass(PessoaFisica.class);
            cfg.addAnnotatedClass(PessoaJuridica.class);
            cfg.addAnnotatedClass(Cartao.class);
            cfg.addAnnotatedClass(Perfil.class);
            cfg.addAnnotatedClass(Usuario.class);
            cfg.addAnnotatedClass(Telefone.class);

            cfg.configure("/META-INF/hibernate.cfg.xml");
            StandardServiceRegistryBuilder build = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
            sessionFactory = cfg.buildSessionFactory(build.build());
        } catch (HibernateException ex) {
            System.err.println("Erro ao criar Hibernate util." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static Session abrirSessao() {
        return sessionFactory.openSession();
    }
}
