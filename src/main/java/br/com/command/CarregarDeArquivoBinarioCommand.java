package br.com.command;

import br.com.model.ListaDeCompras;
import br.com.model.PersistenciaBinario;

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