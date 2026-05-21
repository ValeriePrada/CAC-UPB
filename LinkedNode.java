package co.edu.upb.proyecto.client.ui;

import javax.swing.*;
import java.awt.*;

import co.edu.upb.proyecto.model.Usuario;
import co.edu.upb.proyecto.server.rmi.SistemaCACRemote;
import co.edu.upb.proyecto.server.security.Sesion;

/**
 * Ventana de inicio de sesión del sistema CAC
 *
 * Permite autenticarse y abrir el formulario de registro
 * Si el login es exitoso guarda la sesión actual y abre el Dashboard principal
*/
public class LoginUI extends JFrame {

    /**
     * Constructor de la interfaz de login
     *
     * Inicializa todos los componentes gráficos y
     * configura las acciones de autenticación y registro
     *
     * @param sistema referencia remota al sistema CAC
    */
    public LoginUI(SistemaCACRemote sistema){

        setTitle("CAC - Login");
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();

        JButton login = new JButton("Ingresar");
        JButton registro = new JButton("Registrarse");

        panel.add(new JLabel("Usuario"));
        panel.add(user);
        panel.add(new JLabel("Contraseña"));
        panel.add(pass);
        panel.add(login);
        panel.add(registro);

        add(panel);

        /**
         * Evento del botón "Ingresar"
         *
         * Realiza el proceso de autenticación
         *  1. Envía usuario y contraseña al servidor
         *  2. Si son válidos inicia sesión y abre el dashboard
         *  3. Si son inválidos muestra mensaje de error
        */
        login.addActionListener(e -> {
        try {

            Usuario u = sistema.login(user.getText(),new String(pass.getPassword()));

            if(u != null){

                Sesion.login(u);

                dispose();
                new DashboardUI(sistema);

            } else {
                JOptionPane.showMessageDialog(this,"Credenciales incorrectas");
            }

        } catch (Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,"Error: " + ex.getMessage());
        }
    });

        /**
         * Evento del botón "Registrarse"
         *
         * Abre la ventana de registro de usuarios
        */
        registro.addActionListener(e -> new RegistroUI(sistema));

        setVisible(true);
    }
}
