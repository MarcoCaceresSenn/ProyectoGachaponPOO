package UserInterface;


import javax.swing.*;

public class VentanaInicial extends JFrame {
    PanelInicio primerPanel = new PanelInicio(this);
    public VentanaInicial(){
        componentesIniciales();

    }
    private void componentesIniciales(){
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);//width = 1536, height = 864
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        this.add(this.primerPanel);
    }

}
