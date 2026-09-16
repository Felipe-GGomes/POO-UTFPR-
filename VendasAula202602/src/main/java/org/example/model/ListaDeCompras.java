package org.example.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ListaDeCompras {
    private List<Produto> produtos;

    public ListaDeCompras() {
        produtos = new ArrayList<>();
    }

    // Adiciona um produto à lista
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    // Remove um produto da lista pelo nome
    public void removerProduto(String nome) {
        produtos.removeIf(p -> p.getNome().equalsIgnoreCase(nome));
    }

    // Salva todos os produtos em um arquivo de texto, um por linha
    public void salvarEmArquivoTexto(String nomeArquivo) {
        if (!produtos.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, false))) {
                for (Produto produto : produtos) {
                    writer.write(produto.getNome() + " - " + produto.getQuantidade() + " - " + produto.getPreco());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
            }
        } else {
            System.out.println("Lista vazia!");
        }
    }

    // Lê o arquivo de texto de volta e reconstrói a lista de produtos
    public void carregarDeArquivoTexto(String nomeArquivo) {
        produtos.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(" - ");
                produtos.add(new Produto(partes[0], Integer.parseInt(partes[1]), Double.parseDouble(partes[2])));
            }
            System.out.println("Lista do Arquivo de Texto");
            System.out.println(this.toString());
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: " + e.getMessage());
        }
    }

    // Salva a lista inteira em arquivo binário via serialização (ObjectOutputStream)
    public void salvarEmArquivoBinario(String nomeArquivo) {
        if (!produtos.isEmpty()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
                oos.writeObject(produtos);
            } catch (IOException e) {
                System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
            }
        } else {
            System.out.println("Lista vazia!");
        }
    }

    // Lê a lista de volta do arquivo binário (ObjectInputStream)
    @SuppressWarnings("unchecked")
    public void carregarDeArquivoBinario(String nomeArquivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            produtos = (List<Produto>) ois.readObject();
            System.out.println("Lista do Arquivo Binário");
            System.out.println(this.toString());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar o arquivo: " + e.getMessage());
        }
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

    public void salvarEmArquivoJson(String nomeArquivo) {
        if (!produtos.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                objectMapper.writeValue(new File(nomeArquivo), produtos);
            } catch (IOException e) {
                System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
            }
        } else {
            System.out.println("Lista vazia!");
        }
    }

    public void carregarDeArquivoJson(String nomeArquivo) {
        produtos.clear();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            produtos = objectMapper.readValue(new File(nomeArquivo),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Produto.class));
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: " + e.getMessage());
        }
    }
}