package br.com.command;

import br.com.model.ListaDeCompras;
import br.com.model.PersistenciaTexto;

public class SalvarEmAqrTextoCommand implements Command{
    private ListaDeCompras model;

    public SalvarEmAqrTextoCommand(ListaDeCompras model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.setEstrategiaPersistencia(new PersistenciaTexto());
        model.salvar("lista_compras.txt");
    }
}
