package co.edu.upb.proyecto.client;

import co.edu.upb.proyecto.client.ui.KioscoUI;
import co.edu.upb.proyecto.server.rmi.SistemaCACRemote;

/**
 * Clase principal encargada de iniciar la interfaz de la maquina kiosco del sistema
 * 
 * la maquina kiosco permite a los clientes consultar información, generar tickets 
 * e interactuar con el sistema de turnos
 *
 * Esta clase realiza la conexión con el servidor
 * antes de abrir la interfaz gráfica del kiosco
*/
public class MainKiosco {

    /**
     * Método principal de ejecución de la maquina kiosco
     * 1. Muestra un mensaje indicando el inicio de conexión
     * 2. Se conecta al servidor RMI utilizando ClienteRMI
     * 3. Verifica si la conexión fue exitosa
     * 4. Si la conexión funciona abre la interfaz KioscoUI
     * 5. Si ocurre un error muestra mensaje de fallo de conexión
     * 
     * @param args Argumentos recibidos desde consola
    */
    public static void main(String[] args) {

        System.out.println("Conectando kiosco...");

        SistemaCACRemote sistema = ClienteRMI.conectar();

        if (sistema != null) {
            new KioscoUI(sistema);
        } else {
            System.out.println("Error conexión");
        }
    }
}
