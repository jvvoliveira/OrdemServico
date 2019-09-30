package entidades;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TB_EQUIPAMENTO")
public class Equipamento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EQUIP_ID")
    private long id;
    
    @NotNull(message = "Descrição do equipamento não pode ser nulo")
    @Size(max = 300, message = "Caracteres a mais na descrição do equipamento")
    @Column(name = "EQUIP_DESCRICAO")
    private String descricao;
    
    @NotNull(message = "Marca do equipamento não pode ser nulo")
    @Size(max = 30, message = "Caracteres a mais na marca do equipamento")
    @Column(name = "EQUIP_MARCA")
    private String marca;
    
    @NotNull(message = "Modelo do equipamento não pode ser nulo")
    @Size(max = 30, message = "Caracteres a mais no modelo do equipamento")
    @Column(name = "EQUIP_MODELO")
    private String modelo;
    
    @NotNull(message = "Série do equipamento não pode ser nulo")
    @Size(max = 30, message = "Caracteres a mais na série do equipamento")
    @Column(name = "EQUIP_SERIE")
    private String serie;
    
    @NotNull(message = "Defeito do equipamento não pode ser nulo")
    @Size(max = 300, message = "Caracteres a mais no defeito do equipamento")
    @Column(name = "EQUIP_DEFEITO")
    private String defeito;
    
    @Size(max = 400, message = "Caracteres a mais na solução do equipamento")
    @Column(name = "EQUIP_SOLUCAO")
    private String solucao;
    
    @Column(name = "EQUIP_MAO_OBRA")
    private double maoObra;
    
    @Column(name = "EQUIP_CUSTO_PECAS")
    private double custoPecas;
    
    @Transient
    private double valorTotal; //mão de obra + custo de peças
    
    @NotNull(message = "Equipamento deve estar associado a um serviço")
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
