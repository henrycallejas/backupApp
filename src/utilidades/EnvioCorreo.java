package utilidades;

import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author henry
 */
public class EnvioCorreo {
    
    /* Función que envia el correo electrónico 
    Se hace uso de Properties para obtener los datos encriptados del servidor de email, puerto, etc.
    */
    public void enviarCorreo(Properties prop, String correo) {
        Date fecha = new Date();
        EncriptacionDatos encriptador = new EncriptacionDatos();
        Email email = new SimpleEmail();
        email.setHostName(encriptador.desencriptarTexto(prop.getProperty("srName")));
        email.setSmtpPort(Integer.parseInt(encriptador.desencriptarTexto(prop.getProperty("srPor"))));
        email.setAuthentication(encriptador.desencriptarTexto(prop.getProperty("srUs")), encriptador.desencriptarTexto(prop.getProperty("srPs")));
        email.setSSLOnConnect(true);
        try {
            email.setFrom(encriptador.desencriptarTexto(prop.getProperty("srUs")));
            email.setSubject(prop.getProperty("asun"));            
            email.addTo(correo); /* Y aqui establecemos el correo electronico al que se va a enviar el codigo*/
            email.setMsg("Nuevo backup automático realizado exitosamente\nHora: "+fecha.getHours() + ":" + fecha.getMinutes());
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(EnvioCorreo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
