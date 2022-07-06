package UserInterface;

import Almacenamiento.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class PanelInicio extends JPanel implements ActionListener {
    private JLabel fondoLogin = new JLabel(new ImageIcon("data\\fondoLogin.png"));
    private VentanaInicial ventanaInicial;
    private JLabel etiquetaLogin;
    private JLabel etiquetaPassword;
    private JLabel registro;
    private JTextField textLogin = new JTextField();
    private JPasswordField textPassword = new JPasswordField();
    private JButton login;

    public PanelInicio(VentanaInicial ventanaInicial) {

        this.ventanaInicial = ventanaInicial;
        componentesIniciales();

    }

    private void componentesIniciales() {
        this.setLayout(null);

        this.etiquetaLogin = new JLabel("USER: ");
        this.etiquetaLogin.setForeground(Color.YELLOW);
        this.etiquetaLogin.setBounds(70, 150, 150, 25);
        this.etiquetaPassword = new JLabel("PASSWORD: ");
        this.etiquetaPassword.setForeground(Color.YELLOW);
        this.etiquetaPassword.setBounds(70, 250, 150, 25);
        //--------------------------------------------------
        this.textLogin.setBounds(70, 175, 200, 25);
        this.textPassword.setBounds(70, 275, 200, 25);
        //--------------------------------------------------------
        this.login = new JButton("Login");
        this.login.setBounds(115, 350, 90, 25);
        this.login.addActionListener(this);

        this.registro = new JLabel("[CLICK AQUI Y REGISTRATE]");
        this.registro.setForeground(Color.WHITE);
        this.registro.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                cambioColor(Color.red);
            }
        });
        this.registro.setBounds(75, 450, 170, 30);
        this.registro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelRegistro();
            }
        });


        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                cambioColor(Color.WHITE);
            }
        });

        this.setBackground(Color.WHITE);
        this.setBounds(0, 0, 300, 600);
        Dimension dimension = getToolkit().getScreenSize();

        this.fondoLogin.setBounds(0, 0, dimension.width, dimension.height);

        this.fondoLogin.add(this.etiquetaLogin);
        this.fondoLogin.add(this.etiquetaPassword);
        this.fondoLogin.add(this.textLogin);
        this.fondoLogin.add(this.textPassword);
        this.fondoLogin.add(this.login);
        this.fondoLogin.add(this.registro);
        this.add(this.fondoLogin);

        this.setVisible(true);
    }

    public void panelRegistro() {
        PanelRegistro panelRegistro = new PanelRegistro(this.ventanaInicial);
        this.ventanaInicial.remove(this);
        this.ventanaInicial.add(panelRegistro);
        this.ventanaInicial.validate();
    }

    public void panelPrincipal() throws SQLException {
        PanelPrincipal panelPrincipal = new PanelPrincipal(this.ventanaInicial, textLogin.getText(), textPassword.getText());
        this.ventanaInicial.remove(this);
        this.ventanaInicial.add(panelPrincipal);
        this.ventanaInicial.validate();
    }


    public void cambioColor(Color color) {
        this.registro.setForeground(color);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.login == e.getSource()) {
            Login login = new Login(this);
            if (login.crearUsuario()) {
                try {
                    panelPrincipal();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }


    public JTextField getTextLogin() {
        return textLogin;
    }

    public JTextField getTextPassword() {
        return textPassword;
    }

    public PanelInicio() {
    }
}
