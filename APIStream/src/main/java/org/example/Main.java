package org.example;

import java.util.*;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        List<Pessoas> pessoas = Arrays.asList(
                new Pessoas(31, "Jaque", 5000.01),
                new Pessoas(23, "bruno", 5000.00),
                new Pessoas(23, "Tatiane", 4999.99),
                new Pessoas(19, "Vitor", 1800),
                new Pessoas(18, "Carlos", 1910),
                new Pessoas(99, "Severino", 100000),
                new Pessoas(16, "Pedro", 900),
                new Pessoas(44, "Eduardo", 900)
        );

        for (Pessoas pessoa : pessoas) {
            System.out.println(pessoa.getNome());
        }

        pessoas.stream()
                .map(Pessoas::getNome)
                .forEach(System.out::println);

        List<String> nomes = pessoas.stream()
                .map(Pessoas::getNome)
                .collect(Collectors.toList());
        System.out.println(nomes.toString());
        //pessoas.sort(Comparator.comparing(Pessoas::getNome));
        //pessoas.forEach(p -> System.out.println(p.getNome()));
        List<String> nomesOrdenados = pessoas.stream()
                .map(Pessoas::getNome)
                .sorted()
                .toList();
        System.out.println(nomesOrdenados.toString());

        List<Pessoas> pessoasMiasDe20anos = pessoas.stream()
                .filter(p -> p.getIdade() > 20)
                .toList();
        System.out.println(pessoasMiasDe20anos);


        List<Double> listaDeSalarios = pessoas.stream()
                .map(Pessoas::getSalario)
                .toList();
        System.out.println(listaDeSalarios.toString());

        Set<Double> listaDeSalariosSemRepeticao = pessoas.stream()
                .map(Pessoas::getSalario)
                .collect(Collectors.toSet());
        System.out.println(listaDeSalariosSemRepeticao.toString());

        List<Double> salariosDistintos = pessoas.stream()
                .map(Pessoas::getSalario)
                .distinct()
                .toList();
        System.out.println(salariosDistintos.toString());

        List<Pessoas> pessoasmais5k = pessoas.stream()
                .filter(p -> p.getSalario() > 4999.99)
                .toList();
        System.out.println(pessoasmais5k);

        long Nropessoasmais5k = pessoas.stream()
                .filter(p -> p.getSalario() > 4999.99)
                .count();
        System.out.println(Nropessoasmais5k);

//        double somaSalarios = pessoas.stream()
//                .map(pessoas -> pessoas.getSalario())
//                .reduce(0.0,(s1,s2)-> s1 + s2);
//        System.out.println(somaSalarios);

        double somaSalarios2 = pessoas.stream()
                .mapToDouble(Pessoas::getSalario)
                .sum();
        System.out.println(somaSalarios2);

        double mediasalarios = pessoas.stream()
                .mapToDouble(Pessoas::getSalario)
                .average()
                .orElse(0.0);
        System.out.println(mediasalarios);

        double maiorSalario = pessoas.stream()
                .mapToDouble(Pessoas::getSalario)
                .max()
                .orElse(0.0);
        System.out.println(maiorSalario);

        double menorSalario = pessoas.stream()
                .mapToDouble(Pessoas::getSalario)
                .min()
                .orElse(0.0);
        System.out.println(menorSalario);
    }




}