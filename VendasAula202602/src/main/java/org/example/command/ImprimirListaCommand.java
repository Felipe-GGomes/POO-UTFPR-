package org.example.command;

import org.example.model.ListaDeCompras;

public class ImprimirListaCommand implements Command {
    private ListaDeCompras model;

    public ImprimirListaCommand(ListaDeCompras model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.imprimirLista();
        System.out.println("Valor Total R$ " + model.calcularValorTotal());
    }
}
