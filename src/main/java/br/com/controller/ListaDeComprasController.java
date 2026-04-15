package br.com.controller;

import br.com.command.*;
import br.com.model.*;
import br.com.view.ListaDeComprasView;

import java.util.HashMap;
import java.util.Map;

public class ListaDeComprasController {
    private ListaDeCompras model;
    private ListaDeComprasView view;
    private Map<Integer, Command> comandos = new HashMap<>();

    public ListaDeComprasController(ListaDeCompras model, ListaDeComprasView view) {
        this.model = model;
        this.view = view;
        registrarComandos();
    }

    private void registrarComandos() {
        comandos.put(1, new AdicionarProdutoCommand(model, view));
        comandos.put(2, new RemoverProdutoCommand(model, view));
        comandos.put(3, new ExibirListaCommand(model, view));
        comandos.put(4, new SalvarEmAqrTextoCommand(model));
        comandos.put(5, new CarregarDeArqTextoCommand(model));
        comandos.put(6, new SalvarEmArquivoBinarioCommand(model));
        comandos.put(7, new CarregarDeArquivoBinarioCommand(model));
        comandos.put(8, new SalvarEmArquivoJsonCommand(model));
        comandos.put(9, new CarregarDeArquivoJsonCommand(model));
        comandos.put(10, new FiltrarPorQuantidadeMinimaCommand(model, view));
        comandos.put(11, new CalcularValorTotalCommand(model));
        comandos.put(12, new ImprimirListaCommand(model));
    }

    public void iniciar() {
        int opcao;
        do {
            view.exibirMenu();
            opcao = view.lerOpcao();
            if (opcao != 0) {
                processarOpcao(opcao);
            }
        } while (opcao != 0);
        view.exibirMensagem("Saindo...");
    }

    private void processarOpcao(int opcao) {
        Command comando = comandos.get(opcao);
        if (comando != null) {
            comando.execute();
        } else {
            view.exibirMensagem("Opção inválida!");
        }
    }
}
