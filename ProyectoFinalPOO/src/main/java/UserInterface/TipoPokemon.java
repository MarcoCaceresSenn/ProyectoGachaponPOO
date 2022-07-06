package UserInterface;

public class TipoPokemon extends Carta implements Mostrable{

    public TipoPokemon(String nombre, String rareza, int id) {
        super(nombre, rareza, id);
    }

    @Override
    public void mostrar() {
        System.out.println("Tipo : Pokemon." +" Nombre: " +this.getNombre() +". Rareza: "+this.getRareza() +". ID: "+this.getId());
    }
}
