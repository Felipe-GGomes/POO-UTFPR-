package br.com.command;

import br.com.model.ListaDeCompras;
import br.com.model.PersistenciaJson;

public class CarregarDeArquivoJsonCommand implements Command {
    private ListaDeCompras model;

    public CarregarDeArquivoJsonCommand(ListaDeCompras model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.setEstrategiaPersistencia(new PersistenciaJson());
        model.carregar("lista_compras.json");
    }
}
