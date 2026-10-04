package org.example.command;

import org.example.model.ListaDeCompras;
import org.example.model.PersistenciaJson;

public class SalvarEmArquivoJsonCommand implements Command {
    private ListaDeCompras model;

    public SalvarEmArquivoJsonCommand(ListaDeCompras model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.setEstrategiaPersistencia(new PersistenciaJson());
        model.salvar("lista_compras.json");
    }
}
