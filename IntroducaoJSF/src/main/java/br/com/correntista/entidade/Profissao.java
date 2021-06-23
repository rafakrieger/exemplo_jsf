/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.entidade;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author Silvio
 */
@Entity
@Table(name = "profissao")
public class Profissao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Lob
    private String descricao;

    public Profissao() {
    }

    public Profissao(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Profissao other = (Profissao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

   

    @Override
    public String toString() {
        return "br.com.correntista.entidade.Profissao[ id=" + id + " ]";
    }
    
}
