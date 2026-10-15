import javax.swing.SwingUtilities;

import controller.PagoLuzController;
import model.ServicioLuz;
import view.PagoLuzView;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ServicioLuz modelo = new ServicioLuz();
            PagoLuzView vista = new PagoLuzView();
            new PagoLuzController(modelo, vista);
            vista.setVisible(true);
        });
    }
}
