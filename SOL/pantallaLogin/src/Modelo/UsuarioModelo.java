/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author Santiago
 */
public class UsuarioModelo {

    private static ArrayList<Usuario> usuarios = new ArrayList<>();

    static {
        usuarios.add(new Usuario("javier", "1234"));
        usuarios.add(new Usuario("admin", "admin"));
    }

    public static Usuario verificarCredenciales(String nombre, String password) {
        for (Usuario u : usuarios) {
            if (u.getNombre().equals(nombre) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;  // Retorna null si no coincide
    }
}
