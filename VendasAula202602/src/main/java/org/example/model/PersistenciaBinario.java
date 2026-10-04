package org.example.model;

import java.io.*;
import java.util.List;

public class PersistenciaBinario implements PersistenciaStrategy {
    @Override
    public void salvar(List<Produto> produtos, String caminhoArquivo) {
        if (produtos != null && !produtos.isEmpty()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
                oos.writeObject(produtos);
            } catch (IOException e) {
                System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
            }
        } else {
            System.out.println("Lista vazia!");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Produto> carregar(String caminhoArquivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
            return (List<Produto>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar o arquivo: " + e.getMessage());
            return null;
        }
    }
}
