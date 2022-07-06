package Almacenamiento;


import java.sql.*;


public class ConexionBD {
    public Connection conexion = null;

    public Statement sentencia;
    public ResultSet resultado;
    public PreparedStatement pst;

    public ConexionBD() {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
            System.out.println("error");
        }

        try {
            conexion = DriverManager.getConnection("jdbc:mysql://bruselas.ceisufro.cl/proyectoPokemon", "pokemon", "pokemon.2022");
        } catch (SQLException e) {
            System.out.println("no fue posible realizar la conexión a la BD");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }


    public boolean desconectarBD() throws SQLException {
        try{
            if (conexion != null){
                if (sentencia!= null){
                    conexion.close();
                }
                conexion.close();
            }
        }catch(SQLException e){
            System.out.println(e.getErrorCode());
        }
        return true;
    }
}

