/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.entradaSalida;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Santiago
 */
public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/loginDB";
    private static final String USER = "root";  // Cambia a tu usuario de MySQL
    private static final String PASSWORD = "";  // Cambia a tu contraseña de MySQL
    private static Connection conexion;

    public static Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new SQLException("Error de conexión con la base de datos.", e);
            }
        }
        return conexion;
    }
}
