package com.example.comandacamarero;

import java.util.ArrayList;
import java.util.List;

public class Comanda {

    private String nombreCamarero;
    private String numeroMesa;
    private ArrayList<String> pedidosComensales;

    // Constructor
    public Comanda(String nombreCamarero, String numeroMesa) {
        this.nombreCamarero = nombreCamarero;
        this.numeroMesa = numeroMesa;
        this.pedidosComensales = new ArrayList<>();
    }

    // Getters y setters
    public String getNombreCamarero() {
        return nombreCamarero;
    }

    public void setNombreCamarero(String nombreCamarero) {
        this.nombreCamarero = nombreCamarero;
    }

    public String getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(String numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public List<String> getPedidosComensales() {
        return pedidosComensales;
    }

    public void agregarPedido(String pedido) {
        this.pedidosComensales.add(pedido);
    }
}
