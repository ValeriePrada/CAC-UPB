package co.edu.upb.proyecto.client.ui;

import javax.swing.*;
import co.edu.upb.proyecto.server.rmi.SistemaCACRemote;

/**
 * Panel encargado de mostrar la cola actual de turnos del sistema
*/
public class PanelCola extends JPanel {

    /**
     * Constructor del panel de cola
     *
     * Obtiene la información de la cola desde el servidor y la muestra en pantalla
     *
     * @param sistema referencia remota al sistema CAC
    */
    public PanelCola(SistemaCACRemote sistema){

        /**
         * Se muestra un título y luego se obtiene la información de la cola desde el servidor
         */
        add(new JLabel("Cola en tiempo real"));

        try {
            String cola = sistema.verCola();

            JTextArea area = new JTextArea(10,30);
            area.setText(
                (cola == null || cola.isEmpty()) ? "Sin datos" : cola
            );

            add(area);

        } catch(Exception e){
            add(new JLabel("Error conexión"));
        }
    }
}