package co.edu.upb.proyecto.client;

import co.edu.upb.proyecto.client.ui.LoginUI;
import co.edu.upb.proyecto.server.rmi.SistemaCACRemote;

/**
 * Clase principal encargada de iniciar la aplicación usuario del sistema
 * 
 * Esta clase se conecta al servidor RMI, verifica que la conexión sea exitosa y abre 
 * la interfaz gráfica de inicio de sesión
*/
public class MainUsuario {

    /**
     * Método principal de ejecución del usuario
     * 1. Muestra un mensaje indicando que se iniciará la conexión
     * 2. Llama a ClienteRMI para conectarse al servidor
     * 3. Verifica si la conexión fue exitosa
     * 4. Si la conexión funciona muestra mensaje de éxito, abre la ventana LoginUI
     * 5. Si falla muestra mensaje de error
     * 
     * @param args Argumentos recibidos desde consola
    */
    public static void main(String[] args) {

        System.out.println("Connecting to RMI...");

        SistemaCACRemote sistema = ClienteRMI.conectar();

        if (sistema != null) {
            System.out.println("Connected!");

            new LoginUI(sistema);

        } else {
            System.out.println("Could not connect to server");
        }
    }
}