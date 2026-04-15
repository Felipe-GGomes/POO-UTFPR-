package br.com.command;

import br.com.model.ListaDeCompras;
import br.com.model.PersistenciaTexto;

public class CarregarDeArqTextoCommand implements Command{
    private ListaDeCompras model;

    public CarregarDeArqTextoCommand(ListaDeCompras model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.setEstrategiaPersistencia(new PersistenciaTexto());
        model.carregar("lista_compras.txt"); //ou "D:/dev/lista_compras.txt"
    }
}
