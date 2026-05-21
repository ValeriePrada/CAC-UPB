package co.edu.upb.proyecto.client.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import co.edu.upb.proyecto.server.rmi.SistemaCACRemote;
import co.edu.upb.proyecto.server.security.Sesion;
import co.edu.upb.proyecto.model.*;

/**
 * Ventana utilizada para crear nuevas citas
 *
 * Puede ser utilizada tanto por administradores y clientes
 *
 * Incluye validaciones de
 *  - horario permitido
 *  - anticipación mínima
 *  - existencia del cliente
*/
public class PanelFormularioCita extends JFrame {

    /**
     * Constructor del formulario de citas
     *
     * Configura todos los componentes necesarios
     * para registrar una nueva cita
     *
     * @param sistema referencia remota al sistema CAC
    */
    public PanelFormularioCita(SistemaCACRemote sistema) {

        setTitle("Solicitar Cita");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        JTextField campoId = new JTextField();
        boolean esAdmin = Sesion.isAdmin();

        if (esAdmin) {
            add(new JLabel("ID Cliente:"));
            add(campoId);
        }

        SpinnerDateModel modeloFecha = new SpinnerDateModel();
        JSpinner spinnerFecha = new JSpinner(modeloFecha);
        spinnerFecha.setEditor(new JSpinner.DateEditor(
                spinnerFecha, "dd/MM/yyyy hh:mm a"));

        JComboBox<TipoCita> tipo = new JComboBox<>(TipoCita.values());
        JTextField motivo = new JTextField();

        add(new JLabel("Fecha y Hora:"));
        add(spinnerFecha);

        add(new JLabel("Tipo:"));
        add(tipo);

        add(new JLabel("Motivo:"));
        add(motivo);

        JButton crear = new JButton("Crear Cita");
        JButton cancelar = new JButton("Cancelar");

        add(crear);
        add(cancelar);

        try {
            JOptionPane.showMessageDialog(this, sistema.infoCitas());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error conexión");
        }

        /**
         * Evento del botón "Crear Cita"
         *
         * Valida cliente existente, horario permitido y tiempo mínimo de anticipación
         *
         * Luego crea y registra la cita
        */
        crear.addActionListener(e -> {

            try {

                Cliente cliente;

                if (esAdmin) {
                    int id = Integer.parseInt(campoId.getText());

                    cliente = sistema.buscarCliente(id);

                    if (cliente == null) {
                        JOptionPane.showMessageDialog(this,
                                "Cliente no encontrado");
                        return;
                    }

                } else {

                    if (!(Sesion.getUsuario() instanceof Cliente)) {
                        JOptionPane.showMessageDialog(this,
                                "Solo clientes pueden crear citas");
                        return;
                    }

                    cliente = (Cliente) Sesion.getUsuario();
                }

                Date fecha = (Date) spinnerFecha.getValue();

                long diff = fecha.getTime() - new Date().getTime();
                long minutos = diff / (1000 * 60);

                if(minutos < 10){
                    JOptionPane.showMessageDialog(this,
                        "Debes agendar con al menos 10 minutos de anticipación");
                    return;
                }

                int hora = fecha.getHours();
                int minutosHora = fecha.getMinutes();

                if (hora < 6 || hora > 18 || (hora == 18 && minutosHora > 0)) {
                    JOptionPane.showMessageDialog(this,
                            "Horario: 6:00 AM a 6:00 PM");
                    return;
                }

                TipoCita tipoSeleccionado = (TipoCita) tipo.getSelectedItem();

                Cita cita = new Cita(
                        (int) (Math.random() * 10000),
                        fecha,
                        EstadoCita.ACTIVA,
                        cliente,
                        tipoSeleccionado,
                        motivo.getText().trim()
                );

                if (!sistema.crearCita(cita)) {
                    JOptionPane.showMessageDialog(this,
                            "Error al crear cita");
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Cita creada correctamente");
                    dispose();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error");
            }
        });

        /**
         * Evento del botón cancelar
         *
         * Cierra la ventana actual
        */
        cancelar.addActionListener(e -> dispose());

        setVisible(true);
    }
}
