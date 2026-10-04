package org.example.command;

import org.example.model.ListaDeCompras;
import org.example.model.PersistenciaBinario;

public class CarregarDeArquivoBinarioCommand implements Command {
    private ListaDeCompras model;

    public CarregarDeArquivoBinarioCommand(ListaDeCompras model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.setEstrategiaPersistencia(new PersistenciaBinario());
        model.carregar("lista_compras.bin");
    }
}
