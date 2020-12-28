
package utilidades;

import org.jasypt.util.text.BasicTextEncryptor;

public class EncriptacionDatos {
    BasicTextEncryptor encriptador;
    private static final String LLVDEFAULT = ""; /* Le pones la llave que quieras para encriptar los datos*/

    /* Constructor que recibe una llave como parametro*/
    public EncriptacionDatos(String llave) {
        encriptador = new BasicTextEncryptor();
        if(llave == null || llave.length()==0){
            llave = LLVDEFAULT;
            encriptador.setPassword(llave);
        }
    }
    /*Constrcutor por defecto que crea el encriptador con la llave de la constante LLVDEFAULT arriba especificada*/
    public EncriptacionDatos() {
        encriptador = new BasicTextEncryptor();
        encriptador.setPassword(LLVDEFAULT);
    }
    
    /* Método que recibe un texto sin encriptar y devuelve el texto encriptado*/
    public String encriptarTexto(String texto){
        return encriptador.encrypt(texto);
    }
    
    /*Método que recibe como parametro un texto encriptado y devuelve el texto desenciptado */
    public String desencriptarTexto(String textoEncriptado){
        return encriptador.decrypt(textoEncriptado);
    }
}
