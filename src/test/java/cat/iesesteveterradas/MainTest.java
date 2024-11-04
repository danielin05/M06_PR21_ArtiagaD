package cat.iesesteveterradas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class MainTest {
    @Test
    public void testReadFileContent() throws IOException {
        // Assumim que 'obtenirPathFitxer' i 'readFileContent' són els mètodes que volem provar
        // Aquesta crida llançarà IOException si el fitxer no existeix o no es pot llegir
        List<String> content = Main.readFileContent(Main.obtenirPathFitxer());

        // Verificar que el contingut llegit és el que esperem
        assertEquals("1. Ser Clar amb els Requeriments: Crear un document de especificació de requisits de software que detalli els requeriments i les especificacions de disseny.", content.get(0));
        assertEquals("2. Procediment de Desenvolupament Apropiat: Utilitzar cicles de vida de desenvolupament de software (SDLC) com Agile o Waterfall per gestionar el flux de treball.", content.get(1));
        // ... altres assercions per a cada línia ...

        // Verificar la mida de la llista (nombre de línies en el fitxer)
        assertEquals(7, content.size());
    }
}
