package entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import utils.Status;

@Entity
@Table(name = "TB_SERVICO")
public class Servico implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "COL_STATUS")
    private Status status;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "COL_DATA_INICIO")
    private Date inicio;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "COL_DATA_FIM")
    private Date fim;
    
    @Transient //não armazenado, calculado
    private double custoPecas;
    
    @Column(name = "COL_MAO_OBRA")
    private double MaoDeObra;
    
//    private Cliente cliente
//    private List<Funcionario> funcionarios;
//    private List<Equipamento> equipamentos;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Servico other = (Servico) obj;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public double getCustoPecas() {
        return custoPecas;
    }

    public void setCustoPecas(double custoPecas) {
        this.custoPecas = custoPecas;
    }

    public double getMaoDeObra() {
        return MaoDeObra;
    }

    public void setMaoDeObra(double MaoDeObra) {
        this.MaoDeObra = MaoDeObra;
    }
    
    
}
