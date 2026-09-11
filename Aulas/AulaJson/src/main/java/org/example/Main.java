package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //criando objetos
        ObjectMapper objectMapper = new ObjectMapper();
        List<Carro> carros = new ArrayList<>();
        File Json = new File("MeuCarro.json");

        Carro meucarro = new Carro(2022, "Carmim", "Chevrolet", "Onix");
        Carro meucarro1 = new Carro(1966, "Preto", "Chevrolet", "Opala");
        Carro meucarro2 = new Carro(2001, "Laranja", "Ford", "Pampa");
        Carro meucarro3 = new Carro(2020, "Preto", "Chevrolet", "Onix");

        carros.add(meucarro);
        carros.add(meucarro1);
        carros.add(meucarro2);
        carros.add(meucarro3);

        //serialização
        try{
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(Json, carros);
            System.out.println("Objetos salvos no arquivo: " + Json.getAbsolutePath());
        }catch(Exception e){
            System.out.println("Erro ao salvar o arquivo\n" + e.getMessage());
        }

        //desseriallização
        try{
            List<Carro> carrosDesserializados = new ArrayList<>();
            if(Json.exists()){
                carrosDesserializados = objectMapper.readValue(Json, objectMapper.getTypeFactory().constructCollectionType(List.class, Carro.class));
                System.out.println("\nObjeto Java Lido do arquivo ");
                System.out.println(carrosDesserializados.toString());
            }
        }catch (IOException ioe){
            System.out.println("ERRO " + ioe.getMessage());
        }
    }
}