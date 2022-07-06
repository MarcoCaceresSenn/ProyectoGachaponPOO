package Almacenamiento;

import UserInterface.PanelInicio;
import UserInterface.VentanaInicial;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {
    @Test
    void probarlogin(){

       PanelInicio pi = new PanelInicio();
       pi.setVisible(false);
        Login log = new Login(pi);

        assertFalse(log.crearUsuario());

    }

}