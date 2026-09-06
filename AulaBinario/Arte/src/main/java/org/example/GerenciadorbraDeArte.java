package org.example;

import java.io.*;

public class GerenciadorbraDeArte {
    public static void salvarObra(ObraDeArte obra) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Obra.dat"))) {
            oos.writeObject(obra);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObraDeArte carregaObra(String caminho) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            return (ObraDeArte) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
