package entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import validadores.Valida_Matricula;

@Entity
@DiscriminatorValue(value = "F")
public class Funcionario extends Pessoa implements Serializable {
    
    @Valida_Matricula(message = "Matrícula inválida")
    @Size(min = 9, max = 9, message = "Matrícula com quantidade incorreta de caracteres")
    @Column(name = "FUNC_MATRICULA", unique = true)
    private String matricula;
    
    @Size(max = 30, message = "Caracteres a mais para cargo")
    @Column(name = "FUNC_CARGO")
    private String cargo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "funcionario")
    private List<Servico> servicos;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "funcionario")
    private List<Equipamento> equipamentos;

    public Funcionario(){
        this.servicos = new ArrayList();
        this.equipamentos = new ArrayList();
    }
    
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public void addServicos(Servico servico) {
        this.servicos.add(servico);
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(List<Equipamento> equipamentos) {
        this.equipamentos = equipamentos;
    }
    
    public void addEquipamentos(Equipamento equipamento) {
        this.equipamentos.add(equipamento);
        equipamento.setFuncionario(this);
    }
    
}
