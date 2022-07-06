package Almacenamiento;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class InstruccionTablaTest {

    @Test
    void probarRetornarID() throws SQLException {
        ConexionBD conn = new ConexionBD();
        InstruccionTabla it = new InstruccionTabla(conn);
        String idstring = it.retornarID("joaco","12234");
        int idint = Integer.parseInt(idstring);
        assertEquals(1,idint);

    }
}