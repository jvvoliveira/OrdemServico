package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "TB_EQUIPAMENTO")
public class Equipamento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EQUIP_ID")
    private long id;
    
    @Column(name = "EQUIP_DESCRICAO", length = 300)
    private String descricao;
    
    @Column(name = "EQUIP_MARCA", length = 30, nullable = false)
    private String marca;
    
    @Column(name = "EQUIP_MODELO", length = 30, nullable = false)
    private String modelo;
    
    @Column(name = "EQUIP_SERIE", length = 30, nullable = false)
    private String serie;
    
    @Column(name = "EQUIP_DEFEITO", length = 300, nullable = false)
    private String defeito;
    
    @Column(name = "EQUIP_SOLUCAO", length = 400)
    private String solucao;
    
    @Column(name = "EQUIP_MAO_OBRA")
    private double maoObra;
    
    @Column(name = "EQUIP_CUSTO_PECAS")
    private double custoPecas;
    
    @Transient
    private double valorTotal; //mão de obra + custo de peças
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FK_SERV", referencedColumnName = "SERV_ID")
    private Servico servico;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FK_FUNC", referencedColumnName = "PESS_ID")
    private Funcionario funcionario;
    
    public Equipamento(){
        
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Equipamento other = (Equipamento) obj;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getDefeito() {
        return defeito;
    }

    public void setDefeito(String defeito) {
        this.defeito = defeito;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public double getMaoObra() {
        return maoObra;
    }

    public void setMaoObra(double maoObra) {
        this.maoObra = maoObra;
    }

    public double getCustoPecas() {
        return custoPecas;
    }

    public void setCustoPecas(double custoPecas) {
        this.custoPecas = custoPecas;
    }

    public double getValorTotal() {
        this.setValorTotal();
        return valorTotal;
    }

    public void setValorTotal() {
        this.valorTotal = this.getCustoPecas() + this.getMaoObra();
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
}
