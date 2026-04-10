package org.example;

import java.sql.*;

import static javax.swing.UIManager.getInt;
import static javax.swing.UIManager.getString;


public class Main {
    public static void main(String[] args) throws SQLException {

        //AHORA PONEMOS EL URL DE A BASE DE DATOS, NOMBRE DE USUARIO Y CONTRASEÑA
        String url = "jdbc:mariadb://localhost:3306/usuarios";
        String usuario = "root";
        String contrasenia = "";

        //PONEMOS LAS VARIABLES PARA LA CONEXION
        Connection conexion = null;
        Statement sentencia = null;
        ResultSet resultado = null;

        try {
            // Establecemos la conexión a la base de datos
            conexion = DriverManager.getConnection(url, usuario, contrasenia);

            System.out.println("CONEXION EXITOSA A MYSQL");

            // Creamos una sentencia SQL
            sentencia = conexion.createStatement();

            // Definimos la consulta SQL
            String consultaSQL = "SELECT nombre, email FROM usuarios";

            // Ejecutamos la consulta y obtenemos los resultados
            resultado = sentencia.executeQuery(consultaSQL);

            //TRANSACCION PARA EL CÓDIGO
            try { //DESACTIVO EL setAutoCommit E INSERTO EL USUARIO
                conexion.setAutoCommit(false);
                sentencia.executeUpdate("INSERT INTO usuarios(nombre, email) VALUES('Marcelo', 'marceluco@gmail.com')");
                conexion.commit();

                //SOLTAMOS LA EXCEPCIÓN SI NO SE CUMPLEN LAS CONDICIONES
            } catch(SQLException e) {
                if(conexion!=null){
                    try {
                        conexion.rollback();
                    }catch (SQLException ex) {
                        System.out.println(ex.toString());
                    }
                }
            }

            //CREAMOS EL OBJETO USUARIO PARA LA INFO DEL ARRAYLIST
            while (resultado.next()) {

                String nombre = resultado.getString("nombre");
                String email = resultado.getString("email");

                // Imprimimos los resultados
                System.out.println("Nombre: " + nombre + ", email: " + email);
            }

            //OTRA FORMA DE HACERLO COMO DAVID
            // AQUÍ INSERTAS LOS DOS USUARIOS
            insertarUsuario(conexion, "Rocio Ca", "rouss@gmail.com");
            insertarUsuario(conexion, "Nayeli Beitia", "nayelibeitia@hotmail.com");

            System.out.println("Usuarios insertados correctamente");

        } catch (SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        } finally {
            // CERRAMOS RECURSOS
            if (resultado != null) resultado.close();
            if (sentencia != null) sentencia.close();
            if (conexion != null) conexion.close();
        }
    }

    //MÉTODO PARA INSERTAR LOS USUARIOS YA CREADOS
    //El executeUpdate es para INSERTAR, MODIFICAR Y TAL LAS TABLAS/COLUMNAS QUE CREAMOS
    static void insertarUsuario(Connection con, String nombre, String email) throws SQLException {

        String sql = "INSERT INTO usuarios (nombre, email) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, email);
            ps.executeUpdate();

            System.out.println("Insertado: " + nombre);
        }
    }
    //MÉTODO PARA LISTAR LOS USUARIOS AÑADIDOS
    static void listar(Connection con) throws SQLException {
        System.out.println("\nUSUARIOS:");
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM usuarios");
        while (rs.next()) {
            System.out.println(rs.getInt("id") + " | " + rs.getString("nombre") + " | " + rs.getString("email"));
        }
    }
    //MÉTODO PARA ACTUALIZAR EL NOMBRE DE USUARIO POR SU ID
    static void actualizarPorID(Connection con, int id, String nuevoNombre) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, nuevoNombre);
        stmt.executeUpdate();
        System.out.println("Actualizado el id: " + id);
    }
    //MÉTODO PARA ELIMINAR UN USUARIO POR SU ID
    static void eliminarPorId(Connection con, int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        System.out.println("Eliminado el id: " + id);
    }

}