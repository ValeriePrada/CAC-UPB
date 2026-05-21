package co.edu.upb.proyecto.client.ui;

import javax.swing.*;
import java.awt.*;
import co.edu.upb.proyecto.server.rmi.SistemaCACRemote;

/**
 * Monitor visual de turnos en tiempo real
 *
 * Muestra el turno actual en atención y la cola de espera
 *
 * Se actualiza automáticamente cada 2 segundos
*/
public class MonitorUI extends JFrame {

    /**
     * Label donde se muestra el turno actual
    */
    private JLabel turnoActual;

    /**
     * Área de texto donde se visualiza la cola completa
    */
    private JTextArea cola;

    /**
     * Guarda el último turno mostrado
     * Se utiliza para detectar cambios y mostrar notificaciones visuales
    */
    private String ultimoTurno = "";

    /**
     * Constructor del monitor de turnos
     *
     * Configura la interfaz y el temporizador encargado
     * de actualizar la información automáticamente
     *
     * @param sistema referencia remota al sistema CAC
    */
    public MonitorUI(SistemaCACRemote sistema){

        setTitle("MONITOR DE TURNOS");
        setSize(600,450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        turnoActual = new JLabel("Esperando turnos...", SwingConstants.CENTER);
        turnoActual.setFont(new Font("Arial", Font.BOLD, 22));
        turnoActual.setForeground(Color.BLUE);

        cola = new JTextArea();
        cola.setEditable(false);
        cola.setFont(new Font("Monospaced", Font.PLAIN, 14));

        add(turnoActual, BorderLayout.NORTH);
        add(new JScrollPane(cola), BorderLayout.CENTER);

        /**
         * Timer que actualiza automáticamente el turno actual y la cola de espera
         * también detecta cambios de turno
        */
        new Timer(2000, e -> {
            try {

                String nuevoTurno = sistema.verTurnoActualGlobal();
                String nuevaCola = sistema.verColaGlobal();

                turnoActual.setText(nuevoTurno);
                cola.setText(nuevaCola);

                if (!nuevoTurno.equals(ultimoTurno)) {

                    turnoActual.setForeground(Color.RED);

                    new Timer(500, ev -> turnoActual.setForeground(Color.BLUE)).start();

                    ultimoTurno = nuevoTurno;
                }

            } catch (Exception ex) {
                turnoActual.setText("Error conexión");
                cola.setText("");
            }
        }).start();

        setVisible(true);
    }
}