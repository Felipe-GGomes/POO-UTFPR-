package org.example.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PersistenciaJson implements PersistenciaStrategy {
    @Override
    public void salvar(List<Produto> produtos, String caminhoArquivo) {
        if (produtos != null && !produtos.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                objectMapper.writeValue(new File(caminhoArquivo), produtos);
            } catch (IOException e) {
                System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
            }
        } else {
            System.out.println("Lista vazia!");
        }
    }

    @Override
    public List<Produto> carregar(String caminhoArquivo) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File(caminhoArquivo),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Produto.class));
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: " + e.getMessage());
            return null;
        }
    }
}
