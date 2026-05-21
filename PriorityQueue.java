package co.edu.upb.proyecto.client.ui;

import javax.swing.*;
import java.awt.*;
import co.edu.upb.proyecto.model.*;
import co.edu.upb.proyecto.server.rmi.SistemaCACRemote;

/**
 * Panel principal del administrador
 *
 * Desde aquí el administrador puede
 *  - crear citas
 *  - editar citas
 *  - cancelar citas
 *  - atender turnos
 *  - gestionar membresías
 *  - crear administradores
 *  - visualizar reportes
 *  - abrir el monitor
*/
public class PanelAdmin extends JPanel {

    /**
     * Constructor del panel administrador
     *
     * Inicializa todos los botones y eventos
     * correspondientes a las funciones del administrador
     *
     * @param sistema referencia remota al sistema CAC
    */
    public PanelAdmin(SistemaCACRemote sistema){

        setLayout(new GridLayout(11,1,15,15));
        setBackground(Color.WHITE);

        JButton crear = new JButton("Crear Cita");
        JButton ver = new JButton("Ver todas las citas");
        JButton editar = new JButton("Editar Cita");
        JButton cancelar = new JButton("Cancelar Cita");
        JButton atender = new JButton("Atender Turno");
        JButton reporte = new JButton("Reporte No Asistidos");
        JButton crearAdmin = new JButton("Crear Admin");
        JButton monitor = new JButton("Monitor");
        JButton buscar = new JButton("Buscar Cita");
        JButton membresia = new JButton("Cambiar Membresía");
        JButton salir = new JButton("Salir");
        JButton finalizar = new JButton("Finalizar Turno");

        add(crear); 
        add(ver); 
        add(editar); 
        add(cancelar);
        add(atender); 
        add(reporte); 
        add(crearAdmin);
        add(monitor); 
        add(buscar); 
        add(membresia); 
        add(salir); 
        add(finalizar);

        /**
         * Evento para crear una nueva cita
         *
         * Abre el formulario de creación de citas
        */
        crear.addActionListener(e -> new PanelFormularioCita(sistema));

        /**
         * Evento para ver todas las citas registradas
         *
         * Solicita la información al servidor y la muestra
         * en una ventana emergente
        */
        ver.addActionListener(e -> {
            try {
                JOptionPane.showMessageDialog(this, sistema.listarTodasLasCitas());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,"Error conexión");
            }
        });

        /**
         * Evento para editar una cita existente
         *
         * Permite cambiar la fecha, tipo y motivo de una cita dada su ID
         * Solo se puede editar si la cita está activa
        */
        editar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("ID Cita:"));

                JTextField fecha = new JTextField();
                JComboBox<TipoCita> tipo = new JComboBox<>(TipoCita.values());
                JTextField motivo = new JTextField();

                Object[] form = {
                    "Fecha (dd/MM/yyyy hh:mm a):", fecha,
                    "Tipo:", tipo,
                    "Motivo:", motivo
                };

                if (JOptionPane.showConfirmDialog(null, form) == JOptionPane.OK_OPTION) {

                    boolean ok = sistema.editarCitaCliente(
                            id,
                            fecha.getText()
                    );

                    JOptionPane.showMessageDialog(this,
                            ok ? "Cita actualizada" : "No se pudo editar");
                }

            } catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Error");
            }
        });

        /**
         * Evento para cancelar una cita
         *
         * Solicita el ID de la cita y envía la petición
         * de cancelación al servidor
        */
        cancelar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(
                    JOptionPane.showInputDialog("ID de la cita:")
                );

                boolean ok = sistema.cancelarCitaCliente(id);

                if (ok) {
                    JOptionPane.showMessageDialog(this,
                        "Cita cancelada correctamente");
                } else {
                    JOptionPane.showMessageDialog(this,
                        "No se pudo cancelar la cita (no existe o no está activa)");
                }

            } catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Error en la operación");
            }
        });

        /**
         * Evento para atender el siguiente turno
         *
         * El administrador selecciona el banco disponible
         * donde será atendido el cliente
        */
        atender.addActionListener(e -> {
            try {

                String bancos = sistema.listarBancosDisponibles();

                String input = JOptionPane.showInputDialog(
                        this,
                        bancos + "\nIngrese el número de banco:"
                );

                int idBanco = Integer.parseInt(input);

                String resultado = sistema.atenderTurnoConBanco(idBanco);

                JOptionPane.showMessageDialog(this, resultado);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error");
            }
        });

        /**
         * Evento para generar el reporte de citas no asistidas
        */
        reporte.addActionListener(e -> {
            try {
                JOptionPane.showMessageDialog(this,
                    sistema.reporteNoAsistidos());
            } catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Error conexión");
            }
        });

        /**
         * Evento para registrar un nuevo administrador
        */
        crearAdmin.addActionListener(e -> {
            try {
                JTextField id = new JTextField();
                JTextField user = new JTextField();
                JPasswordField pass = new JPasswordField();

                Object[] form = {"ID:", id, "User:", user, "Pass:", pass};

                if (JOptionPane.showConfirmDialog(null, form) == JOptionPane.OK_OPTION) {

                    boolean ok = sistema.registrarAdmin(
                            Integer.parseInt(id.getText()),
                            user.getText(),
                            new String(pass.getPassword())
                    );

                    JOptionPane.showMessageDialog(null, ok ? "Creado" : "Error");
                }
            } catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Error");
            }
        });

        /**
         * Evento para abrir el monitor de turnos
        */
        monitor.addActionListener(e -> new MonitorUI(sistema));

        /**
         * Evento para buscar una cita específica mediante su id
        */
        buscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("ID:"));
                JOptionPane.showMessageDialog(this,
                    sistema.buscarCitaTexto(id));
            } catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Error");
            }
        });

        /**
         * Evento para cambiar la membresía de un cliente
         *
         * Permite activar o desactivar el estado premium
        */
        membresia.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("ID Cliente:"));

                int op = JOptionPane.showConfirmDialog(null,
                        "¿Hacer premium?");

                boolean premium = (op == JOptionPane.YES_OPTION);

                boolean ok = sistema.cambiarMembresia(id, premium);

                JOptionPane.showMessageDialog(this,
                    ok ? "Actualizado" : "No encontrado");

            } catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Error");
            }
        });

        /**
         * Evento para finalizar un turno asociado a una cita específica
        */
        finalizar.addActionListener(e -> {
            try {

                String input = JOptionPane.showInputDialog("ID de la cita a finalizar:");

                if (input == null) return;

                int idCita = Integer.parseInt(input);

                String mensaje = sistema.finalizarTurnoPorCita(idCita);

                JOptionPane.showMessageDialog(this, mensaje);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error");
            }
        });

        /**
         * Evento para salir del sistema
         *
         * Solicita confirmación antes de cerrar
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

