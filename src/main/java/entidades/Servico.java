package entidades;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    @Column(name = "SERV_DATA_PREV_FIM")
    private Date prevFim;
    
    @Transient
    private double custoPecas; //soma de peças de todos os equipamentos
    
    @Transient
    private double MaoDeObra; //soma da mão de obra de todos os equipamentos
    
    @Transient
    private double valorTotal; //mão de obra + custo de peças
   
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_CLI", nullable = false, referencedColumnName = "PESS_ID")
    private Cliente cliente;
    
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_FUNC", nullable = false, referencedColumnName = "PESS_ID")
    private Funcionario funcionario;
    
    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Equipamento> equipamentos;
    
    public Servico(){
        this.equipamentos = new ArrayList();
    }

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

    public Date getPrevFim() {
        return prevFim;
    }

    public void setPrevFim(Date prevFim) {
        this.prevFim = prevFim;
    }

    public double getCustoPecas() {
        this.setCustoPecas();
        return custoPecas;
    }

    private void setCustoPecas() {
        double valEquips = 0.0;
        for(Equipamento equip :equipamentos){
            valEquips += equip.getCustoPecas();
        }
        this.custoPecas = valEquips;
    }

    public double getMaoDeObra() {
        this.setMaoDeObra();
        return MaoDeObra;
    }

    private void setMaoDeObra() {
        double valEquips = 0.0;
        for(Equipamento equip :equipamentos){
            valEquips += equip.getMaoObra();
        }
        this.MaoDeObra = valEquips;
    }

    public double getValorTotal() {
        this.setValorTotal();
        return valorTotal;
    }

    private void setValorTotal() {
        double valEquips = 0.0;
        for(Equipamento equip : equipamentos){
            valEquips += equip.getValorTotal();
        }
        this.valorTotal = valEquips;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }
    
    public Equipamento getEquipamento(long id) {
        for(Equipamento equip :equipamentos){
            if(equip.getId() == id){
                return equip;
            }
        }
        return null;
    }

    public void setEquipamentos(Equipamento equipamento) {
        this.equipamentos.add(equipamento);
    }
    
    
}
