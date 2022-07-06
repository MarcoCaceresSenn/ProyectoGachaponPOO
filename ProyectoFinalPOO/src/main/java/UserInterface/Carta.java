package UserInterface;

public abstract class Carta implements Mostrable {

    private String nombre;
    private String rareza;
    private int id;

    public Carta(String nombre, String rareza, int id) {
        this.nombre = nombre;
        this.rareza = rareza;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRareza() {
        return rareza;
    }

    public void setRareza(String rareza) {
        this.rareza = rareza;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
