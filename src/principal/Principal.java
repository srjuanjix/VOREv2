package principal;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import ventana.FramePrincipal;

public class Principal {

	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, UnsupportedLookAndFeelException {
		FramePrincipal miVentana = new FramePrincipal();
                /**Declaramos el objeto*/
                FramePrincipal miVentanaPrincipal;
           /*
                try {
            
                javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (InstantiationException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
            */
                 miVentana.setVentanaPrincipal(miVentana);
		 miVentana.setVisible(true);
	}
}
