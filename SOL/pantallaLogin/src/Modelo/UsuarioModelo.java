/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Modelo.entradaSalida.ConexionBD;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Santiago
 */
public class UsuarioModelo {

    public static Usuario verificarCredenciales(String username, String password) throws SQLException {
        Connection con = ConexionBD.getConexion();
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String storedPassword = rs.getString("password");

            // Verificación de la contraseña (comparación directa, sin encriptación)
            if (password.equals(storedPassword)) {
                // Retornamos el objeto Usuario
                Usuario user = new Usuario(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getDate("fecha_nacimiento"),
                        rs.getString("correo")
                );
                return user;
            }
        }
        return null; // Retorna null si no coincide el username o la contraseña
    }

    public static boolean agregarUsuario(String username, String password) throws SQLException {
        Connection con = ConexionBD.getConexion();
        String sql = "INSERT INTO usuarios (username, password) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        // No encriptamos la contraseña
        ps.setString(1, username);
        ps.setString(2, password);

        int rowsInserted = ps.executeUpdate();
        return rowsInserted > 0;
    }

    public static void agregarNuevoUsuario(String username, String password, String nombre, String apellido, String fechaNacimiento, String correo) throws SQLException {
        String query = "INSERT INTO usuarios (username, password, nombre, apellidos, fecha_nacimiento, correo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password); // Asegúrate de cifrar la contraseña
            ps.setString(3, (nombre.isEmpty() ? null : nombre)); // Si está vacío, insertar NULL
            ps.setString(4, (apellido.isEmpty() ? null : apellido));
            ps.setDate(5, (fechaNacimiento.isEmpty() ? null : java.sql.Date.valueOf(fechaNacimiento))); // Convertir a SQL DATE
            ps.setString(6, (correo.isEmpty() ? null : correo));

            ps.executeUpdate();
        }
    }

    public static boolean existeUsuario(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM usuarios WHERE username = ?";
        try (PreparedStatement stmt = Modelo.entradaSalida.ConexionBD.getConexion().prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // Retorna true si el usuario existe
                }
            }
        }
        return false;
    }

    public static void actualizarDatosOpcionales(String username, String nombre, String apellido, String fechaNacimiento, String correo) throws SQLException {
        String query = "UPDATE usuarios SET nombre = ?, apellido = ?, fecha_nacimiento = ?, correo = ? WHERE username = ?";
        try (PreparedStatement stmt = Modelo.entradaSalida.ConexionBD.getConexion().prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, fechaNacimiento);  // Convertir a formato de fecha adecuado si es necesario
            stmt.setString(4, correo);
            stmt.setString(5, username);
            stmt.executeUpdate();
        }
    }

}
