package co.edu.upb.proyecto.client.ui;

import javax.swing.*;
import java.awt.*;
import co.edu.upb.proyecto.server.rmi.SistemaCACRemote;
import co.edu.upb.proyecto.server.security.Sesion;
import co.edu.upb.proyecto.model.Rol;

/**
 * Ventana principal del sistema CAC-UPB
 *
 * Esta clase representa el dashboard principal que se muestra
 * después de iniciar sesión
 *
 * Según el rol del usuario autenticado ADMIN o CLIENTE
 * carga el panel correspondiente
 *
 * Utiliza un JPanel central llamado "content" para cambiar
 * dinámicamente las vistas del sistema
*/
public class DashboardUI extends JFrame {

    /**
     * Panel principal donde se cargan las vistas del sistema
    */
    private JPanel content;

    /**
     * Constructor del dashboard principal
     * Configura la ventana principal y determina qué panel
     * mostrar según el rol del usuario autenticado
     *
     * Si el usuario es administrador carga PanelAdmin
     * Si el usuario es cliente carga PanelCliente
     *
     * @param sistema referencia remota al sistema CAC
    */
    public DashboardUI(SistemaCACRemote sistema){

        setTitle("Centro de atención al cliente CAC-UPB");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        content = new JPanel();
        content.setLayout(new BorderLayout());
        add(content, BorderLayout.CENTER);

        Rol rol = Sesion.getUsuario().getRol();

        if(rol == Rol.ADMIN){
            setPanel(new PanelAdmin(sistema));
        } else {
            setPanel(new PanelCliente(sistema));
        }

        setVisible(true);
    }

    /**
     * Cambia el panel actualmente visible dentro del dashboard
     * Primero elimina el contenido anterior y luego agrega
     * el nuevo panel recibido como parámetro
     *
     * revalidate() recalcula el layout que significa que el nuevo panel se organizará correctamente
     * repaint() vuelve a dibujar la interfaz
     *
     * @param panel nuevo panel a mostrar
    */
    public void setPanel(JPanel panel){
        content.removeAll();
        content.add(panel, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }
}