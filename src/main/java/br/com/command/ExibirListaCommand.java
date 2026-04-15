package br.com.command;

import br.com.model.ListaDeCompras;
import br.com.view.ListaDeComprasView;

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
