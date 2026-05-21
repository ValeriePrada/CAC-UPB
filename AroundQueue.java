package co.edu.upb.proyecto.client.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import co.edu.upb.proyecto.server.rmi.SistemaCACRemote;
import co.edu.upb.proyecto.server.security.Sesion;

/**
 * Panel encargado de mostrar las citas del cliente autenticado
*/
public class PanelCitas extends JPanel {

    /**
     * Referencia remota al sistema CAC
    */
    private SistemaCACRemote sistema;

    /**
     * Modelo de datos utilizado por la tabla
    */
    private DefaultTableModel modelo;

    /**
     * Constructor del panel de citas
     *
     * Configura la tabla y el botón encargado de cargar las citas del usuario
     *
     * @param sistema referencia remota al sistema
    */
    public PanelCitas(SistemaCACRemote sistema){
        this.sistema = sistema;

        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new String[]{"ID","Fecha"},0);
        JTable tabla = new JTable(modelo);

        JButton cargar = new JButton("Ver Mis Citas");

        add(cargar, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargar.addActionListener(e -> load());
    }

    /**
     * Carga las citas del usuario autenticado
     *
     * Obtiene la información desde el servidor y la agrega a la tabla
    */
    private void load(){
        modelo.setRowCount(0);

        try {
            int id = Sesion.getUsuario().getId();
            String data = sistema.listarMisCitas(id);
            String[] lineas = data.split("\n");

            for(String l : lineas){
                if(!l.isEmpty()){
                    modelo.addRow(new Object[]{l,""});
                }
            }

        } catch(Exception e){
            JOptionPane.showMessageDialog(this,"Error conexión");
        }
    }
}
