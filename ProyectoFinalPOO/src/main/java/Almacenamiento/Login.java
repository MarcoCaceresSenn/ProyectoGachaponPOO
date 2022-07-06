package Almacenamiento;
import UserInterface.PanelInicio;

import javax.swing.*;
import java.sql.SQLException;
public class Login {
    private String id_usuario;
     PanelInicio panelInicio;
    public Login(PanelInicio panelInicio){
        this.panelInicio = panelInicio;
    }



    public boolean crearUsuario(){
        try {
            String usuario = this.panelInicio.getTextLogin().getText();
            String contrasenia = this.panelInicio.getTextPassword().getText();
            ConexionBD conexion = new ConexionBD();
            String usuarioCorrecto = null;
            String passwordCorrecta = null;
            conexion.pst = conexion.conexion.prepareStatement("SELECT nombre, contrasenia from usuario where nombre = ?");
            conexion.pst.setString(1,usuario);
            conexion.resultado=conexion.pst.executeQuery();
            if(conexion.resultado.next()){
                usuarioCorrecto = conexion.resultado.getString(1);
                passwordCorrecta = conexion.resultado.getString(2);
            }

            if (usuario !=null && contrasenia!=null && usuario.equals(usuarioCorrecto)&&contrasenia.equals(passwordCorrecta)){
                //this.id_usuario = conexion.resultado.getString(0);
                return true;
            }
            else{
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}
