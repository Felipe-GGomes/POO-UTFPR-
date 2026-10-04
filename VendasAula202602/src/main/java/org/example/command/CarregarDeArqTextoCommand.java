package org.example.command;

import org.example.model.ListaDeCompras;
import org.example.model.PersistenciaTexto;

public class CarregarDeArqTextoCommand implements Command {
    private ListaDeCompras model;

    public CarregarDeArqTextoCommand(ListaDeCompras model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.setEstrategiaPersistencia(new PersistenciaTexto());
        model.carregar("lista_compras.txt");
    }
}
