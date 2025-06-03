/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yesica
 */
public class Usuario {
    private String username;
    private String password;
    private String email;
    private String nombre;
    private List<Producto> listaDeseos;
    
    public Usuario(String username, String password, String email, String nombre) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nombre = nombre;
        this.listaDeseos = new ArrayList<>();
    }
    
    // Getters y Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public List<Producto> getListaDeseos() { return listaDeseos; }
    public void setListaDeseos(List<Producto> listaDeseos) { this.listaDeseos = listaDeseos; }
    
    public void agregarADeseos(Producto producto) {
        if (!listaDeseos.contains(producto)) {
            listaDeseos.add(producto);
        }
    }
    
    public void removerDeDeseos(Producto producto) {
        listaDeseos.remove(producto);
    }
}
