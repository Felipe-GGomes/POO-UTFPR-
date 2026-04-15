package br.com.command;

import br.com.model.ListaDeCompras;

public class CalcularValorTotalCommand implements Command{
    private ListaDeCompras model;

    public CalcularValorTotalCommand(ListaDeCompras model) {
        this.model = model;
    }

    @Override
    public void execute() {
        System.out.println("Valor Total R$ "+model.calcularValorTotal());
    }
}
