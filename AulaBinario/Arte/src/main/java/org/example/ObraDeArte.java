package org.example;

import java.io.Serializable;
import java.util.Arrays;

public class ObraDeArte implements Serializable {
    private String titulo;
    private String artista;
    private byte[] fotoDaObra;

    public ObraDeArte(String artista, byte[] fotoDaObra, String titulo) {
        this.artista = artista;
        this.fotoDaObra = fotoDaObra;
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public byte[] getFotoDaObra() {
        return fotoDaObra;
    }

    public String getTitulo() {
        return titulo;
    }

    @Override
    public String toString() {
        return "ObraDeArte{" +
                "artista='" + artista + '\'' +
                ", titulo='" + titulo + '\'' +
                ", fotoDaObra=" + Arrays.toString(fotoDaObra) +
                '}';
    }
}
