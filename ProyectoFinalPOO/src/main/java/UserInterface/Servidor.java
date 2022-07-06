package UserInterface;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        MarcoServidor marcoServidor = new MarcoServidor();
        marcoServidor.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}

class MarcoServidor extends JFrame implements Runnable {
    public MarcoServidor() {
        setBounds(1200, 300, 280, 350);
        JPanel ventana = new JPanel();
        ventana.setLayout(new BorderLayout());
        this.areaDeTexto = new JTextArea();
        ventana.add(areaDeTexto, BorderLayout.CENTER);
        add(ventana);
        setVisible(true);
        Thread hilo1 = new Thread(this);
        hilo1.start();
    }

    @Override
    public void run() {
        try {
            ServerSocket socketServer = new ServerSocket(1928);
            String nickEntrante, ipEntrante, mensajeEntrante;
            PaqueteEnviado paqueteRecibido;
            while (true) {
                Socket socket2 = socketServer.accept();
                ObjectInputStream flujoObjetoEntrante = new ObjectInputStream(socket2.getInputStream());
                paqueteRecibido = (PaqueteEnviado) flujoObjetoEntrante.readObject();
                nickEntrante = paqueteRecibido.getNickname();
                ipEntrante = paqueteRecibido.getIp();
                mensajeEntrante = paqueteRecibido.getMensaje();
                areaDeTexto.append("\n" + "De: " + nickEntrante + ". Para IP destinatario: "
                        + ipEntrante + "\n" + mensajeEntrante);
                Socket socketEnviadoADestinatario = new Socket(ipEntrante, 2859);
                ObjectOutputStream paqueteADestinatario = new ObjectOutputStream(socketEnviadoADestinatario.getOutputStream());
                paqueteADestinatario.writeObject(paqueteRecibido);
                paqueteADestinatario.close();
                socketEnviadoADestinatario.close();
                socket2.close();
            }


        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private JTextArea areaDeTexto;
}
