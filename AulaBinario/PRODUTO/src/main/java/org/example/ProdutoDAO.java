package org.example;

import java.io.*;
import java.util.List;

public class ProdutoDAO {

    public static void gravarProdutosNoArquivo(File arquivo, List<Produto> produtos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(produtos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String leProdutoDoArquivo(File arquivo) {
        StringBuilder resultado = new StringBuilder();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            List<Produto> produtos = (List<Produto>) ois.readObject();
            for (Produto p : produtos) {
                resultado.append(p).append("\n");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultado.toString();
    }
}