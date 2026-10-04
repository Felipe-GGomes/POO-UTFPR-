package org.example.command;

import org.example.model.ListaDeCompras;
import org.example.model.PersistenciaTexto;

public class SalvarEmArqTextoCommand implements Command {
    private ListaDeCompras model;

    public SalvarEmArqTextoCommand(ListaDeCompras model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.setEstrategiaPersistencia(new PersistenciaTexto());
        model.salvar("lista_compras.txt");
    }
}
