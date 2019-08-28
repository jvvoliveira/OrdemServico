package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    @Column(name = "SERV_ID")
    private long id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "SERV_STATUS")
    private Status status;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "SERV_DATA_INICIO")
    private Date inicio;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "SERV_DATA_FIM")
    private Date fim;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "SERV_PREV_DATA_FIM")
    private Date prevFim;
    
    @Column(name = "SERV_CUSTO_PECAS")
    private double custoPecas;
    
    @Column(name = "SERV_MAO_OBRA")
    private double MaoDeObra;
    
    @Transient
    private double valorTotal;
    
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_PESS_ID", referencedColumnName = "PESS_ID")
    private Pessoa cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_FUNC_ID", referencedColumnName = "PESS_ID")
    private Funcionario atendente;
    
    @OneToMany(mappedBy = "servico", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Equipamento> equipamentos;

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

    public Date getPrevFim() {
        return prevFim;
    }

    public void setPrevFim(Date prevFim) {
        this.prevFim = prevFim;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Pessoa getCliente() {
        return cliente;
    }

    public void setCliente(Pessoa cliente) {
        this.cliente = cliente;
    }

    public Funcionario getAtendente() {
        return atendente;
    }

    public void setAtendente(Funcionario atendente) {
        this.atendente = atendente;
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamento(Equipamento equipamentos) {
        this.equipamentos.add(equipamentos);
    }

    public void setEquipamentos(List<Equipamento> equipamentos) {
        this.equipamentos = equipamentos;
    }
    
}
