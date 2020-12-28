
package propiedades;

import java.io.InputStream;

/**
 *
 * @author henry
 */
public class Propiedades {
    public InputStream obtenerRecurso(String nombre){
        return Propiedades.class.getResourceAsStream(nombre);
    }
}
