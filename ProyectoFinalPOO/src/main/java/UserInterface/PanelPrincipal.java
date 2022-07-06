package UserInterface;

import Almacenamiento.ConexionBD;
import Almacenamiento.InstruccionTabla;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


//panel de menu principal
public class PanelPrincipal extends JPanel implements ActionListener {
    Dimension dimension = getToolkit().getScreenSize();

    private JLabel fondo = new JLabel(new ImageIcon("data\\fondoPrincipal.png"));
    private JLabel iconTradeChat = new JLabel(new ImageIcon("data\\tradeCarta.png"));
    private JLabel iconUser = new JLabel(new ImageIcon("data\\iconTrainer.png"));
    private JLabel exitIcon = new JLabel(new ImageIcon("data\\logout.png"));
    private JLabel chatIcon = new JLabel(new ImageIcon("data\\iconChat.png"));
    private JLabel coleccion = new JLabel (new ImageIcon("data\\cardGacha.png"));
    private JLabel pikaGacha;

    private CarruselCarta carruselCarta1;
    private CarruselCarta carruselCarta2;
    private CarruselCarta carruselCarta3;

    private JLabel inventario = new JLabel("INVENTARIO");
    private JLabel intercambios = new JLabel("INTERCAMBIAR");
    private JLabel cerrarSesion;


    private VentanaInicial ventanaInicial;
    public PanelInicio inicio = new PanelInicio(ventanaInicial);

    private String nick;
    private String password;

    private InstruccionTabla instrucciones;

    private ConexionBD bd_pokemon;

    public PanelPrincipal(VentanaInicial ventanaInicial, String nick, String password) {
        this.ventanaInicial = ventanaInicial;
        this.nick = nick;
        this.instrucciones = new InstruccionTabla(this.inicio);
        this.bd_pokemon = new ConexionBD();
        this.password = password;
        componentesIniciales();
    }



    private void componentesIniciales() {
        this.setLayout(null);

        this.exitIcon.setBounds(23, 500, 170, 170);

        this.exitIcon.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                cambioColorLOut(Color.MAGENTA);
            }
        });
        this.exitIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelInicial();
            }
        });

        String ruta = "data\\pikaClose.png";
        String ruta2 = "data\\pikaBall10-3.png";
        this.pikaGacha = new JLabel(new ImageIcon(ruta));
        this.pikaGacha.setBounds(23, 101, 180, 180);
        this.pikaGacha.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                changeLabel(ruta2);
            }
        });
        this.pikaGacha.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                entregarCartaRandom();
            }
        });

        this.cerrarSesion = new JLabel("EXIT");
        this.cerrarSesion.setForeground(Color.WHITE);
        this.cerrarSesion.setBounds(73, 670, 100, 25);

        this.iconTradeChat.setBounds(23, 300, 180, 170);
        this.iconTradeChat.setVisible(true);
        this.iconTradeChat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                try {
                    String id_usuario_a_intercambiar = JOptionPane.showInputDialog(null, "Ingrese la ID del usuario con el que quiere intercambiar");
                    String id_pokemon_a_intercambiar = JOptionPane.showInputDialog(null, "Ingrese la ID del pokemon a intercambiar");
                    int id_usuario_numero = Integer.parseInt(id_pokemon_a_intercambiar);
                    int id_pokemon_numero = Integer.parseInt(id_pokemon_a_intercambiar);
                    String consulta = "SELECT * FROM proyectoPokemon.pokemon WHERE id = '" + id_pokemon_a_intercambiar + "';";
                    String resultado = "";
                    String dueñoDeCarta = "";
                    try {
                        ResultSet rs = bd_pokemon.conexion.createStatement().executeQuery(consulta);
                        resultado = rs.toString();
                        while (rs.next()) {
                            dueñoDeCarta = rs.getString("id_usuario");
                        }
                    } catch (SQLException x) {
                        System.out.println(x.getMessage());
                    }
                    String idusuario = instrucciones.retornarID(nick, password);
                    if (dueñoDeCarta == null) {
                        JOptionPane.showMessageDialog(null, "Esta carta no está en tu colección");
                    }if (dueñoDeCarta.equals(idusuario)) {
                        if (id_usuario_numero > 0 && id_pokemon_numero < 25 && id_pokemon_numero > 0 && id_pokemon_a_intercambiar != null) {
                            String query = "UPDATE `proyectoPokemon`.`pokemon` SET `id_usuario` = '" + id_usuario_a_intercambiar + "' WHERE (`id` = '" + id_pokemon_a_intercambiar + "');";
                            PreparedStatement stmt = bd_pokemon.conexion.prepareStatement(query);
                            stmt.execute();
                            JOptionPane.showMessageDialog(null, "¡Intercambio realizado exitosamente!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Datos ingresados incorrectos, intentelo nuevamente. \n");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Esta carta no está en tu colección");
                    }
                } catch (SQLException b) {
                    System.out.println(b.getMessage());
                }
            }
        });

        this.iconTradeChat.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                cambioColor(Color.MAGENTA);
            }
        });


        this.intercambios.setForeground(Color.WHITE);
        this.intercambios.setBounds(65, 475, 200, 25);

        //----------------------------------------------------------------------------------
        //boton para coleccion uwu
        this.coleccion.setBounds(1150,230,425,463);

        this.coleccion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                filterColection();
            }
        });
        this.fondo.add(this.coleccion);


        //----------------------------------------------------------------------------------

        this.fondo.setBounds(0, 0, dimension.width, dimension.height);
        this.fondo.setVisible(true);

        this.fondo.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                cambioColor(Color.white);
                cambioColorLOut(Color.white);
                changeLabel(ruta);
                pikaGacha.setBounds(23, 101, 180, 180);
            }
        });

        this.chatIcon.setBounds(1294, 30, 70, 60);
        this.chatIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Cliente abrirCliente = new Cliente(nick);
            }
        });
        this.fondo.add(this.chatIcon);


        this.carruselCarta1 = new CarruselCarta(1000, 0);
        this.carruselCarta1.setBounds(305, 230, 236, 328);
        this.carruselCarta2 = new CarruselCarta(900, 11);
        this.carruselCarta2.setBounds(585, 230, 236, 328);
        this.fondo.add(this.carruselCarta2);
        this.fondo.add(this.carruselCarta1);
        this.carruselCarta3 = new CarruselCarta(800, 20);
        this.carruselCarta3.setBounds(865, 230, 236, 328);
        this.fondo.add(this.carruselCarta3);

        this.fondo.add(this.pikaGacha);
        this.fondo.add(this.exitIcon);
        this.fondo.add(this.intercambios);
        this.fondo.add(this.cerrarSesion);
        this.fondo.add(this.iconTradeChat);
        this.add(this.iconTradeChat);

        this.add(this.fondo);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void panelInicial() {
       this.ventanaInicial.dispose();
    }

    public void cambioColor(Color color) {
        this.intercambios.setForeground(color);
    }

    public void cambioColorLOut(Color color) {
        this.cerrarSesion.setForeground(color);
    }

    public void changeLabel(String ruta) {
        this.pikaGacha.setIcon(new ImageIcon(ruta));
        this.pikaGacha.setBounds(20, 100, 185, 185);
    }

    public void entregarCartaRandom() {
        String nombrePokemon = "";
        String rarezaPokemon = "";
        PanelInicio panelinicioequisdedede;
        int cartaRandom = 0;
        //crear ventana
        JFrame ventanaPremio = new JFrame("ENHORABUENA");
        ventanaPremio.setSize(537, 390);

        //panel de la ventana
        JPanel panelCarta = new JPanel();
        panelCarta.setLayout(null);
        panelCarta.setSize(236, 329);
        panelCarta.setBackground(Color.white);

        //fondo
        JLabel fondo = new JLabel(new ImageIcon("data\\fondoPremio.png"));

        //agregar carta random
        boolean entregando = true;
        while (entregando) {
            System.out.println("Entramos al while");
            Random random = new Random();
            cartaRandom = random.nextInt(24);
            String consulta = "SELECT * FROM proyectoPokemon.pokemon WHERE id = '" + (cartaRandom + 1) + "';";
            String dueñoDeCarta = "";
            try {
                ResultSet rs = bd_pokemon.conexion.createStatement().executeQuery(consulta);
                String resultado = rs.toString();
                while (rs.next()) {
                    dueñoDeCarta = rs.getString("id_usuario");
                    nombrePokemon = rs.getString("nombre");
                    rarezaPokemon = rs.getString("rareza");
                    System.out.println(dueñoDeCarta + nombrePokemon + rarezaPokemon);
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            if (dueñoDeCarta == null) {
                try {
                    String idusuario = instrucciones.retornarID(this.nick, this.password);
                    System.out.println("Id usuario a insertar en pokemon: " + idusuario);
                    System.out.println("ID del pokemon a actualizar = " + (cartaRandom + 1));
                    String query = "UPDATE `proyectoPokemon`.`pokemon` SET `id_usuario` = '" + idusuario + "' WHERE (`id` = '" + (cartaRandom + 1) + "');";
                    PreparedStatement stmt = bd_pokemon.conexion.prepareStatement(query);
                    stmt.execute();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                entregando = false;
            }
        }
        JLabel cartaGacha = new JLabel(new ImageIcon("data\\cartas\\" + cartaRandom + ".jpg"));
        cartaGacha.setBounds(5, 10, 236, 329);

        //agregar info de la carta premio
        String idCarta = "ID Carta: " + (cartaRandom + 1);
        JLabel idCartaPremio = new JLabel(idCarta);
        String nombreCarta = "Nombre Carta: " + nombrePokemon;
        JLabel nombreCartaPremio = new JLabel(nombreCarta);
        String rarezaCarta = "Rareza Carta: " + rarezaPokemon;
        JLabel rarezaCartaPremio = new JLabel(rarezaCarta);
        idCartaPremio.setBounds(260, 10, 220, 25);
        nombreCartaPremio.setBounds(260, 60, 220, 25);
        rarezaCartaPremio.setBounds(260, 110, 220, 25);
        fondo.add(idCartaPremio);
        fondo.add(nombreCartaPremio);
        fondo.add(rarezaCartaPremio);


        JLabel agregadoAColeccion = new JLabel("Nuevo objeto agregado a colección");
        agregadoAColeccion.setBounds(280, 180, 220, 25);
        fondo.add(agregadoAColeccion);

        //agregar componentes al panel
        panelCarta.add(cartaGacha);
        fondo.add(panelCarta);

        //agregar todo en ventana
        ventanaPremio.add(fondo);
        ventanaPremio.setResizable(false);
        ventanaPremio.setVisible(true);


    }

    public void filterColection(){
        String duenioCarta = null;
        String nombrePokemon = null;
        String rarezaPokemon = null;
        ArrayList<Carta> catalogo = new ArrayList<>();
        java.util.List<Carta> listaPokemon = new ArrayList<>();
        List<Carta> listaTrainer = new ArrayList<>();
        String idusuario;
        try {
            System.out.println("EntrÃ³ al try");
            idusuario = instrucciones.retornarID(this.nick, this.password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("Salio del try");
        System.out.println(idusuario);
        for (int i = 0; i < 29; i++) {
            String consulta = "SELECT * FROM proyectoPokemon.pokemon WHERE id = '" + (i + 1) + "';";
            try {
                ResultSet rs = bd_pokemon.conexion.createStatement().executeQuery(consulta);
                String resultado = rs.toString();
                while (rs.next()) {
                    duenioCarta = rs.getString("id_usuario");
                    nombrePokemon = rs.getString("nombre");
                    rarezaPokemon = rs.getString("rareza");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            if (duenioCarta == null) {

            } else if (duenioCarta.equals(idusuario)) {
                if (rarezaPokemon.equals("Trainer")) {
                    TipoTrainer trainer = new TipoTrainer(nombrePokemon,rarezaPokemon,(i+1));
                    catalogo.add(trainer);
                }
                else{
                    TipoPokemon pokemon = new TipoPokemon(nombrePokemon,rarezaPokemon,(i+1));
                    catalogo.add(pokemon);
                }
            }
        }
        listaPokemon = catalogo.stream().filter(n -> n instanceof TipoPokemon).collect(Collectors.toList());
        listaTrainer = catalogo.stream().filter(n -> n instanceof TipoTrainer).collect(Collectors.toList());
        System.out.println("-CARTAS TIPO TRAINER-");
        listaTrainer.forEach((n) -> n.mostrar());
        System.out.println("-CARTAS LEGENDARIAS-");
        listaPokemon.forEach((n) -> {
            if(n.getRareza().equals("Legendaria")){
                n.mostrar();
            }
        });
        System.out.println("\n- CARTAS EPICAS -\n");
        listaPokemon.forEach((n) -> {
            if(n.getRareza().equals("Epica")){
                n.mostrar();
            }
        });
        System.out.println("\n -CARTAS POCO COMUNES");
        listaPokemon.forEach((n) -> {
            if(n.getRareza().equals("Poco comun")){
                n.mostrar();
            }
        });
        System.out.println("\n -CARTAS NORMALES-");
        listaPokemon.forEach((n) -> {
            if(n.getRareza().equals("Normal")){
                n.mostrar();
            }
        });
    }

    }


