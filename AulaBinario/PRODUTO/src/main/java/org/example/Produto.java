package org.example;
import java.io.Serializable;

public class Produto implements Serializable {
    private String nome;
    private double valorUnitario;
    private double quantidade;
    private transient double total;

    public Produto(String nome, double quantidade, double valorUnitario) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public String getNome() {
        return nome;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public double getTotal() {
        return quantidade * valorUnitario;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    @Override
    public String toString() {
        return "| -" + nome + " - " + quantidade + ":" +  valorUnitario + total;
    }
}

