package UserInterface;

import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.net.*;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cliente {
    private String nicknameJlabel;
    public Cliente(String nick) {
        this.nicknameJlabel=nick;
        MarcoCliente marcoDelCliente = new MarcoCliente(nick);
    }
    public Cliente(){

    }

    public String getNicknameJlabel() {
        return nicknameJlabel;
    }

    public void setNicknameJlabel(String nicknameJlabel) {
        this.nicknameJlabel = nicknameJlabel;
    }
}

class MarcoCliente extends JFrame {
    public MarcoCliente(String nick) {
        this.setSize(280,350);
        this.setBounds(600, 300, 280, 350);
        this.setResizable(true);
        VentanaCliente ventanaPrincipal = new VentanaCliente(nick);
        this.add(ventanaPrincipal);
        this.setVisible(true);
    }

}

class VentanaCliente extends JPanel implements Runnable {

    public VentanaCliente(String nickname) {

        JPanel panelMensajeria = new JPanel();
        this.setSize(280,350);
        nickVentana = new JLabel(nickname);
        add(nickVentana);
        JLabel texto = new JLabel("-CHAT-");
        this.add(texto);
        ipVentana = new JTextField(8);
        add(ipVentana);

        this.areaChat = new JTextArea(12, 20);
        this.areaChat.validate();
        this.areaChat.setEditable(false);
        MouseMotionListener doScrollRectToVisible = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Rectangle rectangulo = new Rectangle(e.getX(), e.getY(), 1, 1);
                ((JPanel) e.getSource()).scrollRectToVisible(rectangulo);
            }
        };
        this.addMouseMotionListener(doScrollRectToVisible);
        areaChat.setAutoscrolls(true);
        add(new JScrollPane(areaChat));
        this.campoChat = new JTextField(20);
        this.add(campoChat);
        JButton boton = new JButton("Enviar mensaje");
        EnviaTexto eventoTexto = new EnviaTexto();
        boton.addActionListener(eventoTexto);
        this.add(boton);
        Thread hiloCliente = new Thread(this);
        hiloCliente.start();
    }


    @Override
    public void run() {
        try {
            ServerSocket clienteSocketEscucha = new ServerSocket(2859);
            Socket socketCliente;
            PaqueteEnviado paqueteRecibidoDeServidor;
            while (true) {
                socketCliente = clienteSocketEscucha.accept();
                ObjectInputStream flujoEntranteServidor = new ObjectInputStream(socketCliente.getInputStream());
                paqueteRecibidoDeServidor= (PaqueteEnviado) flujoEntranteServidor.readObject();
                areaChat.append("\n"+ paqueteRecibidoDeServidor.getNickname()+": "+ paqueteRecibidoDeServidor.getMensaje());

            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private class EnviaTexto implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Socket socket1 = new Socket("192.168.143.240", 1928);
                PaqueteEnviado paqueteEnviado = new PaqueteEnviado();
                paqueteEnviado.setNickname(nickVentana.getText());
                paqueteEnviado.setIp(ipVentana.getText());
                paqueteEnviado.setMensaje(campoChat.getText());
                ObjectOutputStream flujoObjetoSaliente = new ObjectOutputStream(socket1.getOutputStream());
                flujoObjetoSaliente.writeObject(paqueteEnviado);
                areaChat.append("\n"+nickVentana.getText()+": "+campoChat.getText());
                flujoObjetoSaliente.close();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException(ex);
            }
        }
    }

    private JTextArea areaChat;
    private JTextField campoChat, ipVentana;
    private JLabel nickVentana;
    private JButton boton;
}

class PaqueteEnviado implements Serializable {

    private String nickname;
    private String ip;
    private String mensaje;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
