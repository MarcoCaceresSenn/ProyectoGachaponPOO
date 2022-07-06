package UserInterface;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class CarruselCarta extends JPanel {

    private int cambiaCarta = 0;
    private JLabel[] cartasGacha;
    public CarruselCarta(int periodoCambio, int indiceCartaInicio){
        cargarCartas(periodoCambio, indiceCartaInicio);
    }
    public void cargarCartas(int periodo, int indiceCartaInicio){
        this.cartasGacha = new JLabel[10];
        for (int i = 0; i < this.cartasGacha.length; i++) {
            this.cartasGacha[i] = new JLabel(new ImageIcon("data\\cartas\\"+(i+indiceCartaInicio)+".jpg"));
        }
        timer(periodo);
    }

    public void modificarCarta(int indicePosicion){
        this.add(this.cartasGacha[indicePosicion]);
        validate();
    }

    public void timer(int periodo){
        java.util.Timer tiempo = new Timer();
        TimerTask timer = new TimerTask() {
            @Override
            public void run() {
                modificarCarta(cambiaCarta);
                cambiaCarta++;
                if(cambiaCarta == 10){
                    cambiaCarta = 0;
                }
            }
        };
        tiempo.scheduleAtFixedRate(timer,0,periodo);
    }

    public void setCambiaCarta(int cambiaCarta) {
        this.cambiaCarta = cambiaCarta;
    }
}
