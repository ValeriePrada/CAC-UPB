package co.edu.upb.proyecto.client.ui;

import javax.swing.*;
import co.edu.upb.proyecto.model.Rol;
import co.edu.upb.proyecto.server.rmi.SistemaCACRemote;

/**
 * Panel relacionado con la gestión de turnos
 *
 * El comportamiento cambia según el rol
 *  - ADMIN: puede atender turnos
 *  - CLIENTE: solo recibe información
*/
public class PanelTurnos extends JPanel {

    /**
     * Constructor del panel de turnos
     *
     * @param sistema referencia remota al sistema
     * @param rol rol del usuario autenticado
    */
    public PanelTurnos(SistemaCACRemote sistema, Rol rol){

        add(new JLabel("Turnos"));

        if(rol == Rol.ADMIN){
            JButton atender = new JButton("Atender siguiente");

            add(atender);

            atender.addActionListener(e -> {
                try {
                    JOptionPane.showMessageDialog(this,
                        sistema.atenderSiguienteTexto());
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(this,"Error conexión");
                }
            });

        } else {
            add(new JLabel("Genera tu ticket desde el módulo Citas"));
        }
    }
}
