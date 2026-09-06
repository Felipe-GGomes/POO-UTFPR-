package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        byte[] fotoOriginal = Files.readAllBytes(Paths.get("obra.jpg"));

        ObraDeArte obra = new ObraDeArte("Guernica", fotoOriginal, "fotoOriginal");

        GerenciadorbraDeArte.salvarObra(obra);

        ObraDeArte obraRecuperada = GerenciadorbraDeArte.carregaObra("obra.dat");
        byte[] fotoRecuperada = obraRecuperada.getFotoDaObra();

        Files.write(Paths.get("obra_recuperada.jpg"), fotoRecuperada);

        System.out.println(obraRecuperada);
        System.out.println("Imagem recuperada gerada com sucesso!");
    }
}