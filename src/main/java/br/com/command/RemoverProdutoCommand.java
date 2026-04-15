package br.com.command;

import br.com.model.ListaDeCompras;
import br.com.view.ListaDeComprasView;

public class RemoverProdutoCommand implements Command {
    private ListaDeCompras model;
    private ListaDeComprasView view;

    public RemoverProdutoCommand(ListaDeCompras model, ListaDeComprasView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void execute() {
        String nome = view.lerNomeProduto();
        model.removerProduto(nome);
    }
}
