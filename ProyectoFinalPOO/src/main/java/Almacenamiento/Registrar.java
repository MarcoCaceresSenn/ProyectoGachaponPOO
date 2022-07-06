package Almacenamiento;

import UserInterface.PanelRegistro;

import javax.swing.*;
import java.sql.SQLException;
public class Registrar {
    PanelRegistro panelRegistro;

    public Registrar(PanelRegistro panelRegistro) {
        this.panelRegistro = panelRegistro;
    }

    public void registrarUsuario() {
        String textName = panelRegistro.getTextoNombre().getText();
        String textPass = panelRegistro.getTextoClave().getText();
        String textConfirmarPass = panelRegistro.getConfirmarcontraseña().getText();


        try {
            validarPassword(textPass,textConfirmarPass);
            try {
                ConexionBD con = new ConexionBD();
                String sql = "insert into usuario(nombre,contrasenia) values (?,?)";
                con.pst = con.conexion.prepareStatement(sql);
                con.pst.setString(1, textName);
                con.pst.setString(2, textPass);
                con.pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Se han insertado los datos");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error de conexión:" + ex.getMessage());
            }
        }
        catch(RegistroException e){
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    public boolean validarPassword(String textPass, String textPassConfirm) throws RegistroException{
        if (!textPass.equals(textPassConfirm)){
            throw  new RegistroException("Error de confirmacion de contraseña. ");
        }
        if (textPass.length() < 5){
            throw new RegistroException("La contraseña es muy corta");
        }

        return  true;
    }
}