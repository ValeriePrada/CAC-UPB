package co.edu.upb.proyecto.client.ui;

import javax.swing.*;
import java.awt.*;
import co.edu.upb.proyecto.server.rmi.SistemaCACRemote;
import co.edu.upb.proyecto.server.security.Sesion;
import co.edu.upb.proyecto.model.*;

/**
 * Panel principal del cliente
 * Permite
 *  - solicitar citas
 *  - visualizar citas
 *  - cancelar citas
 *  - generar tickets
 *  - consultar posición
 *  - abrir monitor
*/
public class PanelCliente extends JPanel {

    /**
     * Guarda el último ticket generado por el cliente
    */
    private Ticket ultimoTicket = null;

    /**
     * Constructor del panel del cliente
     *
     * Configura todos los botones y eventos para el usuario cliente
     *
     * @param sistema referencia remota al sistema CAC
    */
    public PanelCliente(SistemaCACRemote sistema){

        setLayout(new GridLayout(7,1,15,15));

        JButton solicitar = new JButton("Solicitar Cita");
        JButton ver = new JButton("Ver mis citas");
        JButton cancelar = new JButton("Cancelar cita");
        JButton ticket = new JButton("Generar ticket");
        JButton posicion = new JButton("Ver mi posición");
        JButton monitor = new JButton("Ver Monitor");
        JButton salir = new JButton("Salir");

        add(solicitar); 
        add(ver); 
        add(cancelar);
        add(ticket); 
        add(posicion); 
        add(monitor); 
        add(salir);

        solicitar.addActionListener(e -> new PanelFormularioCita(sistema));

        /**
         * Muestra las citas del cliente en un mensaje emergente
         * Si ocurre un error de conexión, muestra un mensaje de error
         */
        ver.addActionListener(e -> {
            try {
                JOptionPane.showMessageDialog(this,
                    sistema.listarMisCitas(Sesion.getUsuario().getId()));
            } catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Error conexión");
            }
        });

        /**
         * Pide al cliente el ID de la cita a cancelar y llama al sistema para cancelarla
         * Si la cancelación es exitosa, muestra un mensaje de confirmación
         * Si ocurre un error (ID inválido, error de conexión, etc), muestra un mensaje de error
         */
        cancelar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("ID"));
                sistema.cancelarCitaCliente(id);
                JOptionPane.showMessageDialog(this,"Cancelada");
            } catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Error");
            }
        });

        /**
         * Genera un ticket para el cliente llamando al sistema remoto
         * Si el ticket se genera correctamente, guarda el ticket en la variable ultimoTicket 
         * y muestra su id en un mensaje emergente
         * Si ocurre un error (error de conexión, etc), muestra un mensaje de error
         */
        ticket.addActionListener(e -> {
            try {
                var res = sistema.generarTicketConMensaje(
                        Sesion.getUsuario().getId());

                if(res.ticket != null){
                    ultimoTicket = res.ticket;
                    JOptionPane.showMessageDialog(this,
                        "Ticket: " + res.ticket.getIdTicket());
                } else {
                    JOptionPane.showMessageDialog(this,res.mensaje);
                }

            } catch(Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,"Error: " + ex.getMessage());
            }
        });

        /**
         * Consulta la posición del cliente en la fila llamando al sistema 
         * remoto con el id del último ticket generado
         * Si el cliente no ha generado un ticket, muestra un mensaje indicando 
         * que debe generar uno primero
         * Si el sistema devuelve la posición correctamente, 
         * muestra la posición en un mensaje emergente
         */
        posicion.addActionListener(e -> {
            try {
                if(ultimoTicket == null){
                    JOptionPane.showMessageDialog(this,"Genera ticket primero");
                } else {
                    JOptionPane.showMessageDialog(this,
                        sistema.verPosicion(ultimoTicket.getIdTicket()));
                }
            } catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Error");
            }
        });

        /**
         * Abre la interfaz del monitor para que el cliente pueda ver el estado de la fila
         */
        monitor.addActionListener(e -> new MonitorUI(sistema));

        /**
         * Pregunta al cliente si desea salir de la aplicación, si confirma, cierra la aplicación
         * Si cancela, no hace nada y el cliente puede seguir usando la aplicación
         */
        salir.addActionListener(e -> {
            int op = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que deseas salir?");
            if(op == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}

