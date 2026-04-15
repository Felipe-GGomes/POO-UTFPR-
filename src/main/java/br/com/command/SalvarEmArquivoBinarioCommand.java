package br.com.command;

import br.com.model.ListaDeCompras;
import br.com.model.PersistenciaBinario;

public class SalvarEmArquivoBinarioCommand implements Command {
    private ListaDeCompras model;

    public SalvarEmArquivoBinarioCommand(ListaDeCompras model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.setEstrategiaPersistencia(new PersistenciaBinario());
        model.salvar("lista_compras.bin");
    }
}