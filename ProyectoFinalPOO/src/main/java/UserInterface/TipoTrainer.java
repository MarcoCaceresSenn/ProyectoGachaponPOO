package UserInterface;

public class TipoTrainer extends Carta implements Mostrable {


    public TipoTrainer(String nombre, String rareza, int id) {
        super(nombre, rareza, id);
    }

    @Override
    public void mostrar() {
        System.out.println("Tipo : Trainer" +" Nombre: " +this.getNombre() +". Rareza: "+this.getRareza()+ ". ID: "+this.getId());

    }
}
