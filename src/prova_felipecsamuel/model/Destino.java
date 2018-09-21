package prova_felipecsamuel.model;

import java.util.Date;

/**
 *
 * @author Felipe
 */
public class Destino {
    private int codigo;
    private String descricao;
    private Date inicio;
    private Date termino;
    private double valorTotal;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getTermino() {
        return termino;
    }

    public void setTermino(Date termino) {
        this.termino = termino;
    }

    public double getValor() {
        return valorTotal;
    }

    public void setValor(double valor) {
        this.valorTotal = valor;
    }

    @Override
    public String toString() {
        return codigo + " - " + descricao;
    }
    
    
}
