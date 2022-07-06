package Almacenamiento;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConexionBDTest {

    @Test
     void probarconexion(){
        ConexionBD conn = new ConexionBD();
        assertEquals(conn.conexion=null,conn.conexion);

    }

    @Test
    void probardesconectar() throws SQLException {
        ConexionBD conn = new ConexionBD();

        conn.desconectarBD();

        assertTrue(conn.desconectarBD());
    }




}