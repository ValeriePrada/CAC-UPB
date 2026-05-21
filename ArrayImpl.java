package co.edu.upb.proyecto.client;

import co.edu.upb.proyecto.client.ui.MonitorUI;
import co.edu.upb.proyecto.server.rmi.SistemaCACRemote;

/**
 * Clase principal encargada que inicia la interfaz del monitor del sistema
 * 
 * El monitor permite ver los turnos actuales, el estado de atención y la cola de clientes
 * 
 * Antes de iniciar la interfaz, esta clasa establece conexión con el servidor RMI
*/
public class MainMonitor {

    /**
     * Método principal de ejecución del monitor
     * 1. Muestra mensaje de conexión
     * 2. Intenta conectarse al servidor RMI
     * 3. Verifica si la conexión fue exitosa
     * 4. Si la conexión funciona abre la interfaz MonitorUI
     * 5. Si falla muestra mensaje de error
     * 
     * @param args Argumentos recibidos desde consola
    */
    public static void main(String[] args) {

        System.out.println("Conectando monitor...");

        SistemaCACRemote sistema = ClienteRMI.conectar();

        if (sistema != null) {
            new MonitorUI(sistema);
        } else {
            System.out.println("Error conexión");
        }
    }
}
