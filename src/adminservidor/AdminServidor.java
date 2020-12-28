package adminservidor;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import propiedades.Propiedades;
import utilidades.EnviaArchivosSFTP;
import utilidades.EnvioCorreo;

public class AdminServidor {

    public static void main(String[] args) {
        EnvioCorreo email = new EnvioCorreo();
        Properties prop = new Properties();
        EnviaArchivosSFTP envia = new EnviaArchivosSFTP();
        /*Cargo el archivo properties para tener acceso a los datos encriptados */
        try {
            prop.load(new Propiedades().obtenerRecurso("config.properties"));
        } catch (IOException ex) {
            Logger.getLogger(AdminServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /* Creo variables que vendran como parametro al ejecutar el archivo jar*/
        String directorioLocal = args[0];
        String directorioRemoto = args[1];
        String correo = args[2];
        
        /* Valido si el directorio pasado como parametro es un directorio y si lo es obtengo el listado
        de archivos de dicho directorio y con el ciclo for se llama al metodo que envia cada archivo por SFTP*/
        File dirLocal = new File(directorioLocal);
        if(dirLocal.isDirectory()){
            File[] archivos = dirLocal.listFiles();
            for(File archivo : archivos){
                System.out.println(archivo.toString());
                envia.enviarArchivo(prop, archivo.toString(), directorioRemoto);
            }
            email.enviarCorreo(prop, correo); /*Al final envia el correo electronico */
        }
        /*Al terminar finalizado la JVM, ya que si no se hace queda ejecutandose */
        System.exit(0);

    }
}
