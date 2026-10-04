package org.example;

public class Pessoas {
    private String nome;
    private int idade;
    private double salario;

    public Pessoas(int idade, String nome, double salario) {
        this.idade = idade;
        this.nome = nome;
        this.salario = salario;
    }

    public int getIdade() {
        return idade;
    }

    public String getNome() {
        return nome;
    }

    public double getSalario() {
        return salario;
    }

    @Override
    public String toString() {
        return "Pessoas{" +
                "idade=" + idade +
                ", nome='" + nome + '\'' +
                ", salario=" + salario +
                '}';
    }
}
