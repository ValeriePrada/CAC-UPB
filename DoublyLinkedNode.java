package co.edu.upb.proyecto.client.ui;

import javax.swing.*;
import java.awt.*;
import co.edu.upb.proyecto.server.rmi.SistemaCACRemote;

/**
 * Interfaz gráfica del kiosco de turnos
 *
 * Permite a los clientes ingresar su id, generar un ticket y consultar su posición en la cola
 *
 * La comunicación se realiza mediante RMI
*/
public class KioscoUI extends JFrame {

    /**
     * Campo donde el usuario ingresa su identificación
    */
    private JTextField inputId;

    /**
     * Área donde se muestran los mensajes del sistema, información del ticket y posición en la cola
    */
    private JTextArea pantalla;

    /**
     * Botón encargado de generar el ticket
    */
    private JButton generarBtn;

    /**
     * Botón que permite consultar la posición actual del ticket generado
    */
    private JButton verPosBtn;

    /**
     * Guarda el id del último ticket generado
     *
     * Se utiliza para consultar posteriormente la posición en la cola
    */
    private int ultimoTicket = -1;

    /**
     * Guarda el id del último ticket generado
     * Se utiliza para consultar posteriormente la posición en la cola
    */
    public KioscoUI(SistemaCACRemote sistema) {

        setTitle("KIOSCO DE TURNOS");
        setSize(400,400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new GridLayout(3,1));

        JLabel titulo = new JLabel("Ingrese su ID", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));

        inputId = new JTextField();

        generarBtn = new JButton("Generar Ticket");

        top.add(titulo);
        top.add(inputId);
        top.add(generarBtn);

        pantalla = new JTextArea();
        pantalla.setEditable(false);
        pantalla.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JPanel bottom = new JPanel();

        verPosBtn = new JButton("Ver mi posición");
        bottom.add(verPosBtn);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(pantalla), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        /**
         * Evento del botón "Generar Ticket"
         *  1. Lee el id ingresado
         *  2. Solicita al servidor la generación del ticket
         *  3. Guarda el ID del ticket generado
         *  4. Muestra la información en pantalla
         *
         * Si ocurre un error se informa al usuario
        */
        generarBtn.addActionListener(e -> {
            try {

                int id = Integer.parseInt(inputId.getText());

                var res = sistema.generarTicketConMensaje(id);

                if (res.ticket != null) {

                    ultimoTicket = res.ticket.getIdTicket();

                    pantalla.setText(
                        "TICKET GENERADO\n\n" +
                        "Número: " + ultimoTicket +
                        "\nPrioridad: " + res.ticket.getPrioridad() +
                        "\n\n" + res.mensaje
                    );

                } else {
                    pantalla.setText(res.mensaje);
                }

            } catch (Exception ex) {
                pantalla.setText("Error al generar ticket");
            }
        });

        /**
         * Evento del botón "Ver mi posición"
         *
         * Consulta al servidor la posición actual del ticket previamente generado
         *
         * Si no existe ticket generado, muestra un mensaje
        */
        verPosBtn.addActionListener(e -> {
            try {

                if (ultimoTicket == -1) {
                    pantalla.setText("Primero genera un ticket");
                    return;
                }

                String pos = sistema.verPosicion(ultimoTicket);

                pantalla.setText(pos);

            } catch (Exception ex) {
                pantalla.setText("Error al consultar posición");
            }
        });

        setVisible(true);
    }
}
