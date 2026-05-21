package co.edu.upb.proyecto.client.ui;

import javax.swing.*;
import java.awt.*;
import co.edu.upb.proyecto.model.Rol;
import co.edu.upb.proyecto.server.rmi.SistemaCACRemote;

/**
 * Barra lateral de navegación del sistema
 *
 * Permite cambiar entre
 *  - módulo de citas
 *  - turnos
 *  - cola
 *  - salir del sistema
*/
public class Sidebar extends JPanel {

    /**
     * Constructor del menú lateral
     *
     * Configura los botones de navegación
     * y sus respectivos eventos
     *
     * @param sistema referencia remota al sistema
     * @param frame ventana principal
     * @param rol rol del usuario autenticado
    */
    public Sidebar(SistemaCACRemote sistema, DashboardUI frame, Rol rol){

        setLayout(new GridLayout(10,1));
        setBackground(new Color(30,30,30));
        setPreferredSize(new Dimension(200,0));

        JButton citas = new JButton("Citas");
        JButton turnos = new JButton("Turnos");
        JButton cola = new JButton("Cola");
        JButton salir = new JButton("Salir");

        add(citas);
        add(turnos);
        add(cola);
        add(salir);

        citas.addActionListener(e -> {
            if(rol == Rol.ADMIN)
                frame.setPanel(new PanelAdmin(sistema));
            else
                frame.setPanel(new PanelCliente(sistema));
        });

        turnos.addActionListener(e -> {
            frame.setPanel(new PanelTurnos(sistema, rol));
        });

        cola.addActionListener(e -> {
            frame.setPanel(new PanelCola(sistema));
        });

        salir.addActionListener(e -> System.exit(0));
    }
}
