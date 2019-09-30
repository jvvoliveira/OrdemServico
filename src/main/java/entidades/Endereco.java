package entidades;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TB_ENDERECO")
public class Endereco implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "END_ID")
    private long id;
    
    @NotNull(message = "Cidade não pode ser nulo")
    @Size(max = 80, message = "Caracteres a mais para cidade")
    @Column(name = "END_CIDADE")
    private String cidade;
    
    @NotNull(message = "Logradouro não pode ser nulo")
    @Size(max = 100, message = "Caracteres a mais para rua")
    @Column(name = "END_LOGRADOURO")
    private String rua;
    
    @Size(max = 150, message = "Caracteres a mais para complemento")
    @Column(name = "END_COMPLEMENTO")
    private String complemento;
    
    @Size(max = 100, message = "Caracteres a mais para bairro")
    @Column(name = "END_BAIRRO")
    private String bairro;
    
    @NotNull(message = "Número não pode ser nulo")
    @Column(name = "END_NUMERO")
    private int numero;
    
    @NotNull(message = "CEP não pode ser nulo")
    @Size(min = 8, max = 9, message = "Quantidade incorreta de caracteres para CEP")
    @Column(name = "END_CEP")
    private String cep;
    
//    @NotNull(message = "Endereço precisa estar associado a um cliente")
    @OneToOne(mappedBy = "endereco", cascade = CascadeType.PERSIST)
    private Cliente cliente;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Endereco other = (Endereco) obj;
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
}
