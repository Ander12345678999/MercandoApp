/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author yesica
 */
public class ProductoController {
    private static ProductoController instance;
    private List<Producto> productos;
    
    private ProductoController() {
        productos = new ArrayList<>();
        inicializarProductos();
    }
    
    public static ProductoController getInstance() {
        if (instance == null) {
            instance = new ProductoController();
        }
        return instance;
    }
    
    private void inicializarProductos() {
        productos.add(new Producto(1, "Laptop Gaming", "Laptop para gaming de alta gama", 1299.99, "Electrónicos"));
        productos.add(new Producto(2, "Smartphone", "Teléfono inteligente última generación", 799.99, "Electrónicos"));
        productos.add(new Producto(3, "Auriculares Bluetooth", "Auriculares inalámbricos con cancelación de ruido", 199.99, "Audio"));
        productos.add(new Producto(4, "Tablet", "Tablet de 10 pulgadas para trabajo y entretenimiento", 349.99, "Electrónicos"));
        productos.add(new Producto(5, "Cámara Digital", "Cámara profesional para fotografía", 899.99, "Fotografía"));
        productos.add(new Producto(6, "Smartwatch", "Reloj inteligente con monitor de salud", 299.99, "Wearables"));
        productos.add(new Producto(7, "Teclado Mecánico", "Teclado mecánico para gaming", 149.99, "Accesorios"));
        productos.add(new Producto(8, "Mouse Gaming", "Mouse ergonómico para gaming", 79.99, "Accesorios"));
    }
    
    public List<Producto> getTodosLosProductos() {
        return new ArrayList<>(productos);
    }
    
    public List<Producto> buscarProductos(String termino) {
        return productos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(termino.toLowerCase()) ||
                           p.getDescripcion().toLowerCase().contains(termino.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    public List<Producto> filtrarPorCategoria(String categoria) {
        return productos.stream()
                .filter(p -> p.getCategoria().equals(categoria))
                .collect(Collectors.toList());
    }
    
    public List<String> getCategorias() {
        return productos.stream()
                .map(Producto::getCategoria)
                .distinct()
                .collect(Collectors.toList());
    }
}
