package org.example;

import org.example.controller.ListaDeComprasController;
import org.example.model.ListaDeCompras;
import org.example.view.ListaDeComprasView;

public class Main {
    public static void main(String[] args) {
        ListaDeCompras model = new ListaDeCompras();
        ListaDeComprasView view = new ListaDeComprasView();
        ListaDeComprasController controller = new ListaDeComprasController(model, view);

        controller.iniciar();
    }
}