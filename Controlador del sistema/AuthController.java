/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yesica
 */
public class AuthController {
    private static AuthController instance;
    private Map<String, Usuario> usuarios;
    private Usuario usuarioActual;
    
    private AuthController() {
        usuarios = new HashMap<>();
        // Usuario de prueba
        usuarios.put("admin", new Usuario("admin", "admin123", "admin@test.com", "Administrador"));
    }
    
    public static AuthController getInstance() {
        if (instance == null) {
            instance = new AuthController();
        }
        return instance;
    }
    
    public boolean login(String username, String password) {
        Usuario usuario = usuarios.get(username);
        if (usuario != null && usuario.getPassword().equals(password)) {
            usuarioActual = usuario;
            return true;
        }
        return false;
    }
    
    public boolean registrarUsuario(String username, String password, String email, String nombre) {
        if (usuarios.containsKey(username)) {
            return false; // Usuario ya existe
        }
        
        Usuario nuevoUsuario = new Usuario(username, password, email, nombre);
        usuarios.put(username, nuevoUsuario);
        return true;
    }
    
    public void logout() {
        usuarioActual = null;
    }
    
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    
    public boolean isLoggedIn() {
        return usuarioActual != null;
    }
}
