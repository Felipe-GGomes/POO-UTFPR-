package org.example.controller;

import org.example.model.ListaDeCompras;
import org.example.view.ListaDeComprasView;
import org.example.model.Produto;

public class ListaDeComprasController {
    private ListaDeCompras model;
    private ListaDeComprasView view;

    public ListaDeComprasController(ListaDeCompras model, ListaDeComprasView view) {
        this.model = model;
        this.view = view;
    }

    public void iniciar() {
        int opcao;
        do {
            view.exibirMenu();
            opcao = view.lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 0);
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                adicionarProduto();
                break;
            case 2:
                removerProduto();
                break;
            case 3:
                exibirLista();
                break;
            case 4:
                salvarEmArqTexto();
                break;
            case 5:
                carregarDeArqTexto();
                break;
            case 6:
                salvarEmArqBinario();
                break;
            case 7:
                carregarDeArqBinario();
                break;
            case 8:
                salvarEmArquivoJson();
                break;
            case 9:
                carregarDeArquivoJson();
                break;
            case 0:
                view.exibirMensagem("Saindo...");
                break;
            default:
                view.exibirMensagem("Opção inválida!");
        }
    }

    private void adicionarProduto() {
        String nome = view.lerNomeProduto();
        int quantidade = view.lerQuantidade();
        double preco = view.lerPreco();
        model.adicionarProduto(new Produto(nome, quantidade, preco));
    }

    private void removerProduto() {
        String nome = view.lerNomeProduto();
        model.removerProduto(nome);
    }

    private void salvarEmArquivoJson() {
        model.salvarEmArquivoJson("lista_compras.json");
    }

    private void carregarDeArquivoJson() {
        model.carregarDeArquivoJson("lista_compras.json");
    }

    private void exibirLista() {
        view.exibirMensagem(model.toString());
    }

    private void salvarEmArqTexto() {
        model.salvarEmArquivoTexto("lista_compras.txt");
    }

    private void carregarDeArqTexto() {
        model.carregarDeArquivoTexto("lista_compras.txt");
    }

    private void salvarEmArqBinario() {
        model.salvarEmArquivoBinario("lista_compras.bin");
    }

    private void carregarDeArqBinario() {
        model.carregarDeArquivoBinario("lista_compras.bin");
    }
}
