package co.edu.upb.proyecto.client.ui;

import javax.swing.*;
import java.awt.*;
import co.edu.upb.proyecto.server.rmi.SistemaCACRemote;

/**
 * Ventana de registro de clientes
 *
 * Permite crear nuevas cuentas dentro del sistema CAC
*/
public class RegistroUI extends JFrame {

    /**
     * Constructor de la ventana de registro
     *
     * Configura todos los campos necesarios para registrar un nuevo cliente
     *
     * @param sistema referencia remota al sistema CAC
    */
    public RegistroUI(SistemaCACRemote sistema){

        setTitle("Registro - CuidaMed");
        setSize(400,450);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(12,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JTextField id = new JTextField();
        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();
        JTextField nombre = new JTextField();
        JTextField edad = new JTextField();

        JCheckBox premium = new JCheckBox("¿Cliente premium?");

        JButton registrar = new JButton("Registrarse");

        panel.add(new JLabel("ID"));
        panel.add(id);

        panel.add(new JLabel("Usuario"));
        panel.add(user);

        panel.add(new JLabel("Contraseña"));
        panel.add(pass);

        panel.add(new JLabel("Nombre"));
        panel.add(nombre);

        panel.add(new JLabel("Edad"));
        panel.add(edad);

        panel.add(premium);

        panel.add(registrar);

        add(panel);

        /**
         * Evento del botón "Registrarse"
         *
         * Envía la información al servidor para crear un nuevo cliente
        */
        registrar.addActionListener(e -> {
            try {
                sistema.registrarUsuarioCliente(
                    Integer.parseInt(id.getText()),
                    user.getText(),
                    new String(pass.getPassword()),
                    nombre.getText(),
                    Integer.parseInt(edad.getText()),
                    premium.isSelected() 
                );


                JOptionPane.showMessageDialog(this,"Registrado correctamente");
                dispose();

            } catch(Exception ex){
                JOptionPane.showMessageDialog(this,
                        "Error en los datos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}

