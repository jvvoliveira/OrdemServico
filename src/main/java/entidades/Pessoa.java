package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_PESSOA")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "COL_TIPO", discriminatorType = DiscriminatorType.STRING, length = 1)
@DiscriminatorValue(value = "C")
public class Pessoa implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "PESS_ID")
    private long id;
    
    @Column(name = "PESS_CPF", nullable = false, length = 14)
    private String cpf;
    
    @Column(name = "PESS_NOME", nullable = false, length = 100)
    private String nome;
    
    @Column(name = "PESS_EMAIL", length = 100)
    private String email;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "PESS_DATA_NASCIMENTO")
    private Date dataNasc;
    
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.PERSIST)
    private List<Telefone> telefones;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "FK_END_ID", referencedColumnName = "END_ID")
    private Endereco endereco;
    
    //Listas de serviço de um cliente
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST)
    private List<Servico> servicos;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Pessoa other = (Pessoa) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefone(Telefone telefones) {
        this.telefones.add(telefones);
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }
    
    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServico(Servico servico) {
        this.servicos.add(servico);
    }
    
    
}
