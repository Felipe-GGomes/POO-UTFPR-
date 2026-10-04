package org.example.model;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListaDeCompras {
    private static ListaDeCompras instancia;
    private List<Produto> produtos;
    private PersistenciaStrategy estrategiaPersistencia;

    // Construtor privado: ninguém de fora consegue dar "new" (Singleton)
    private ListaDeCompras() {
        produtos = new ArrayList<>();
    }

    public static ListaDeCompras getInstancia() {
        if (instancia == null) {
            instancia = new ListaDeCompras();
        }
        return instancia;
    }

    // Adiciona um produto à lista
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    // Remove um produto da lista pelo nome
    public void removerProduto(String nome) {
        produtos.removeIf(p -> p.getNome().equalsIgnoreCase(nome));
    }

    // Strategy: define QUAL formato de arquivo será usado
    public void setEstrategiaPersistencia(PersistenciaStrategy estrategia) {
        this.estrategiaPersistencia = estrategia;
    }

    public void salvar(String nomeArquivo) {
        estrategiaPersistencia.salvar(produtos, nomeArquivo);
    }

    public void carregar(String nomeArquivo) {
        if (!Files.exists(Paths.get(nomeArquivo))) {
            System.out.println("Arquivo não encontrado!");
            return;
        }
        List<Produto> carregados = estrategiaPersistencia.carregar(nomeArquivo);
        if (carregados != null) { // se deu erro, a lista atual é preservada
            produtos = carregados;
        }
    }

    // Streams: filtra produtos com quantidade mínima
    public List<Produto> filtrarPorQuantidadeMinima(int quantidadeMinima) {
        return produtos.stream()
                .filter(p -> p.getQuantidade() >= quantidadeMinima)
                .collect(Collectors.toList());
    }

    // Streams: soma quantidade * preço de cada produto
    public double calcularValorTotal() {
        return produtos.stream()
                .mapToDouble(p -> p.getQuantidade() * p.getPreco())
                .sum();
    }

    // Streams: imprime em ordem alfabética (sem alterar a lista original)
    public void imprimirLista() {
        produtos.stream()
                .sorted(Comparator.comparing(p -> p.getNome().toLowerCase()))
                .forEach(p -> System.out.println(p));
    }

    @Override
    public String toString() {
        if (produtos.isEmpty()) {
            return "Lista de compras vazia.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("--- Lista de Compras ---\n");

        for (int i = 0; i < produtos.size(); i++) {
            sb.append((i + 1)).append(". ").append(produtos.get(i).toString()).append("\n");
        }
        return sb.toString();
    }
}
