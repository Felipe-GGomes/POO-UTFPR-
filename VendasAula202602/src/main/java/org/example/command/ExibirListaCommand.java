package org.example.command;

import org.example.model.ListaDeCompras;
import org.example.model.Produto;
import org.example.view.ListaDeComprasView;

public class ExibirListaCommand implements Command {
    private ListaDeCompras model;
    private ListaDeComprasView view;

    public ExibirListaCommand(ListaDeCompras model, ListaDeComprasView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void execute() {
        view.exibirMensagem(model.toString());
    }
}
