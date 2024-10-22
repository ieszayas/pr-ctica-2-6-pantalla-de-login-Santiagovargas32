/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Date;

/**
 *
 * @author Santiago
 */
public class Usuario {

    private String nombre;
    private String password;
    private int id;
    private String username;
    private String apellidos;
    private Date fechaNacimiento;
    private String correo;

    // Constructor, getters y setters
    public Usuario(int id, String username, String nombre, String apellidos, Date fechaNacimiento, String correo) {
        this.id = id;
        this.username = username;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
    }

    public Usuario(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
    
    

    @Override
    public String toString() {
        return "Usuario{" + "nombre=" + nombre + ", password=" + password + ", id=" + id + ", username=" + username + ", apellidos=" + apellidos + ", fechaNacimiento=" + fechaNacimiento + ", correo=" + correo + '}';
    }
    
    
}
