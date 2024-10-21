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
            String hashedPassword = rs.getString("password");

            // Verificación de la contraseña
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);

            // Si la contraseña es válida, retornamos un objeto Usuario
            if (result.verified) {
                // Suponiendo que tienes una clase Usuario que recibe estos campos
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

        // Encriptar la contraseña
        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());  // 12 es el costo (factor de trabajo)

        ps.setString(1, username);
        ps.setString(2, hashedPassword);

        int rowsInserted = ps.executeUpdate();
        return rowsInserted > 0;
    }
}
