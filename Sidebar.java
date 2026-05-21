package co.edu.upb.proyecto.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import co.edu.upb.proyecto.server.rmi.SistemaCACRemote;

/**
 * Clase cliente encargada de realizar la conexión con el servidor RMI del sistema
 * 
 * Esta clase centraliza todo el proceso de conexión
 * remota permitiendo que otras partes del sistema
 * puedan obtener una referencia al servidor
 * 
 * - conectarse al registro RMI
 * - buscar el servicio remoto "CAC"
 * - retornar el objeto remoto del sistema
 * - manejar errores de conexión
*/
public class ClienteRMI {

    /**
     * Establece la conexión con el servidor RMI
     * 1. Muestra un mensaje indicando que se intenta conectar
     * 2. Obtiene el registro RMI usando la IP y el puerto del servidor
     * 3. Busca el servicio remoto registrado con el nombre "CAC"
     * 4. Convierte el objeto remoto obtenido a SistemaCACRemote
     * 5. Retorna el stub remoto para poder utilizar los métodos del servidor
     * 
     * Si ocurre un error muestra un mensaje de fallo, imprime la excepción y retorna null
     * 
     * @return Referencia remota al sistema o null si falla la conexión
    */
    public static SistemaCACRemote conectar() {

        try {
            System.out.println("🔍 Connecting to RMI...");

            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);

            SistemaCACRemote stub = (SistemaCACRemote) registry.lookup("CAC");

            System.out.println("Connected!");
            return stub;

        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
            return null;
        }
    }
}