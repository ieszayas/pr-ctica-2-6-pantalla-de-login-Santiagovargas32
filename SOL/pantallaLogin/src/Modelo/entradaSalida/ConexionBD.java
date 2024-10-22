/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.entradaSalida;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Santiago
 */
public class ConexionBD {

    private static Connection conexion;

    public static Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                Properties properties = new Properties();
                // Cargar el archivo de propiedades
                try (InputStream input = ConexionBD.class.getResourceAsStream("/config.properties")) {
                    if (input == null) {
                        throw new IOException("No se pudo encontrar el archivo de configuración.");
                    }
                    properties.load(input);
                }

                // Leer las propiedades
                String url = properties.getProperty("db.url");
                String user = properties.getProperty("db.user");
                String password = properties.getProperty("db.password");

                // Establecer la conexión
                conexion = DriverManager.getConnection(url, user, password);
            } catch (IOException e) {
                throw new SQLException("Error al cargar la configuración de la base de datos.", e.getMessage());
            } catch (SQLException e) {
                throw new SQLException("Error de conexión con la base de datos.", e.getMessage());
            }
        }
        return conexion;
    }

    public static void crearBaseDeDatosSiNoExiste() throws SQLException {
        Connection con = ConexionBD.getConexion();

        // Crear base de datos logindb si no existe
        String createDatabase = "CREATE DATABASE IF NOT EXISTS logindb";
        PreparedStatement psCreateDb = con.prepareStatement(createDatabase);
        psCreateDb.execute();

        // Usar la base de datos logindb
        String useDatabase = "USE logindb";
        PreparedStatement psUseDb = con.prepareStatement(useDatabase);
        psUseDb.execute();

        // Crear tabla usuarios si no existe
        String createTableUsuarios = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "username VARCHAR(50) NOT NULL UNIQUE, "
                + "password VARCHAR(255) NOT NULL, "
                + "nombre VARCHAR(100), "
                + "apellidos VARCHAR(100), "
                + "fecha_nacimiento DATE, "
                + "correo VARCHAR(100))";
        PreparedStatement psCreateTable = con.prepareStatement(createTableUsuarios);
        psCreateTable.execute();

        // Verificar si hay usuarios de prueba, si no, insertarlos
        String checkTestUsers = "SELECT COUNT(*) AS count FROM usuarios";
        PreparedStatement psCheckTestUsers = con.prepareStatement(checkTestUsers);
        ResultSet rs = psCheckTestUsers.executeQuery();
        if (rs.next() && rs.getInt("count") == 0) {
            // Insertar usuarios de prueba
            String insertTestUsers = "INSERT INTO usuarios (username, password, nombre, apellidos, fecha_nacimiento, correo) VALUES "
                    + "('user1', 'password1', 'Juan', 'Pérez', '1990-01-01', 'juan.perez@example.com'),"
                    + "('user2', 'password2', 'Ana', 'Gómez', '1985-05-15', 'ana.gomez@example.com')";
            PreparedStatement psInsertTestUsers = con.prepareStatement(insertTestUsers);
            psInsertTestUsers.executeUpdate();
            System.out.println("Usuarios de prueba insertados.");
        } else {
            System.out.println("La base de datos ya tiene usuarios.");
        }
    }
}
