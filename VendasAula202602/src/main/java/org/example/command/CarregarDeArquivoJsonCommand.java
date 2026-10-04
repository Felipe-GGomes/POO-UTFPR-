package org.example.command;

import org.example.model.ListaDeCompras;
import org.example.model.PersistenciaJson;

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
