package UserInterface;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TipoPokemonTest {
    TipoPokemon Nidorina = new TipoPokemon("Nidorina","Normal",15);
    @ParameterizedTest
    @ValueSource(strings = {"Nidorina", "Normal","15"})

    void probarMstrar(String candidate) {
        assertTrue(Nidorina.getNombre().contains(candidate));

    }
}