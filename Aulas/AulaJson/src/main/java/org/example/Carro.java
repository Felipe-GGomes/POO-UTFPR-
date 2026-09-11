package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Carro {
    private String marca;
    private String modelo;
    private int ano;

    @JsonProperty("Cor_veiculo")
    private String cor;

    public Carro() {}

    public Carro(int ano, String cor, String marca, String modelo) {
        this.ano = ano;
        this.cor = cor;
        this.marca = marca;
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
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

    @Override
    public String toString() {
        return "Carro{" +
                " ano=    " + ano +
                " marca=  " + marca + '\'' +
                " modelo= " + modelo + '\'' +
                " cor=    " + cor + "}\n";
    }
}
