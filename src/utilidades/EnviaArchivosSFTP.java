package utilidades;

import adminservidor.AdminServidor;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnviaArchivosSFTP {
    /*Método que configura la conexión por SFTP y recibe como parametro un archivo properties*/
    private ChannelSftp configurarConexionSFTP(Properties prop) {
        EncriptacionDatos encriptador = new EncriptacionDatos(); /*Creo un objeto para poder acceder al metodo de desencriptar texto */
        try {
            JSch jsch = new JSch();
            jsch.setKnownHosts("~/.ssh/known_hosts");/* Se configura los host conocidos del equipo local*/
            /* El método getSession recibe como parametro el Usuario SFTP y la IP del servidor remoto
               Desencripta dichos datos desde el archivo properties*/
            Session jschSession = jsch.getSession(encriptador.desencriptarTexto(prop.getProperty("srU")), encriptador.desencriptarTexto(prop.getProperty("srIp")));
            /*El método setPassword recibe el password del usuario del servidor remoto*/
            jschSession.setPassword(encriptador.desencriptarTexto(prop.getProperty("srPss")));
            jschSession.setTimeout(600000);
            jschSession.connect(); /*Realiza la coneccion */
            return (ChannelSftp) jschSession.openChannel("sftp"); /* Devuelvo el canal abierto SFTP*/
        } catch (JSchException ex) {
            Logger.getLogger(EnviaArchivosSFTP.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void enviarArchivo(Properties prop, String archivoLocal, String directorioRemoto){
        ChannelSftp channelSftp = null;
        try {
            channelSftp = this.configurarConexionSFTP(prop); /*Configuro la conexion usando el metodo de esta misma clase */
            channelSftp.connect();

            channelSftp.put(archivoLocal, directorioRemoto); /*Se envia el archivo */
            System.out.println("Archivo enviado exitosamente");
            
        } catch (JSchException ex) {
            Logger.getLogger(AdminServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SftpException ex) {
            Logger.getLogger(AdminServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
