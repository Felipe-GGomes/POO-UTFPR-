package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Produto> produtos = new ArrayList<>();

        while (true) {
            System.out.print("Deseja cadastrar um novo produto? (Digite 's' para sair ou qualquer outra tecla para continuar): ");
            String resposta = sc.nextLine();
            if (resposta.equalsIgnoreCase("s")) {
                break;
            }

            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("Valor Unitário: ");
            double valorUnitario = Double.parseDouble(sc.nextLine());

            System.out.print("Quantidade: ");
            double quantidade = Double.parseDouble(sc.nextLine());

            produtos.add(new Produto(nome, quantidade, valorUnitario));
        }

        File arquivo = new File("produtos.dat");
        ProdutoDAO.gravarProdutosNoArquivo(arquivo, produtos);

        String resultado = ProdutoDAO.leProdutoDoArquivo(arquivo);
        System.out.println(resultado);
    }
}