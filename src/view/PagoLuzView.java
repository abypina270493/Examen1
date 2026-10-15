package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import model.Cliente;
import model.Consumo;
import model.Recibo;

/** Vista del caso de uso "Pagar servicio de Luz". */
public class PagoLuzView extends JFrame {
    private final JTextField txtNumeroServicio = new JTextField(14);
    private final JTextField txtTarjeta = new JTextField(16);
    private final JButton btnPagar = new JButton("Pagar");
    private final DefaultListModel<Cliente> listaModelo = new DefaultListModel<>();
    private final JList<Cliente> listaClientes = new JList<>(listaModelo);
    private final JTextArea areaDetalle = new JTextArea();   // consumo -> recibo (misma zona)

    public PagoLuzView() {
        super("Pagar servicio de Luz");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2, 10, 10));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        add(crearPanelIzquierdo());
        add(crearPanelDerecho());

        setSize(820, 480);
        setLocationRelativeTo(null);
        habilitarPago(false);
    }

    private JPanel crearPanelIzquierdo() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JPanel servicio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        servicio.setBorder(new TitledBorder("Número de servicio"));
        servicio.add(txtNumeroServicio);

        JPanel tarjeta = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tarjeta.setBorder(new TitledBorder("Número de tarjeta de pago"));
        tarjeta.add(txtTarjeta);
        tarjeta.add(btnPagar);

        p.add(servicio);
        p.add(Box.createVerticalStrut(10));
        p.add(tarjeta);
        p.add(Box.createVerticalGlue());
        return p;
    }

    private JPanel crearPanelDerecho() {
        JPanel p = new JPanel(new GridLayout(2, 1, 0, 10));

        listaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollLista = new JScrollPane(listaClientes);
        scrollLista.setBorder(new TitledBorder("Clientes"));

        areaDetalle.setEditable(false);
        areaDetalle.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollDetalle = new JScrollPane(areaDetalle);
        scrollDetalle.setBorder(new TitledBorder("Consumo / Recibo de pago"));

        p.add(scrollLista);
        p.add(scrollDetalle);
        return p;
    }

    // Registro de listeners (el controlador se conecta aquí)
    public void agregarListenerNumeroServicio(DocumentListener l) { txtNumeroServicio.getDocument().addDocumentListener(l); }
    public void agregarListenerSeleccionCliente(ListSelectionListener l) { listaClientes.addListSelectionListener(l); }
    public void agregarListenerPagar(ActionListener l) { btnPagar.addActionListener(l); }

    // Lectura de datos capturados
    public String getNumeroServicio() { return txtNumeroServicio.getText(); }
    public String getTarjeta() { return txtTarjeta.getText(); }
    public Cliente getClienteSeleccionado() { return listaClientes.getSelectedValue(); }

    // Salidas hacia el usuario 
    public void mostrarClientes(List<Cliente> clientes) {
        listaModelo.clear();
        for (Cliente c : clientes) {
            listaModelo.addElement(c);
        }
    }

    public void mostrarConsumo(Cliente c, Consumo consumo) {
        areaDetalle.setText(String.format(Locale.US,
                "DATOS DE CONSUMO%n%n"
                + "Cliente:         %s%n"
                + "No. de servicio: %s%n"
                + "Dirección:       %s%n"
                + "Periodo:         %s%n"
                + "Consumo:         %d kWh%n"
                + "Tarifa:          $%.2f / kWh%n"
                + "Fecha límite:    %s%n"
                + "IMPORTE A PAGAR: $%,.2f",
                c.getNombre(), c.getNumeroServicio(), c.getDireccion(), consumo.getPeriodo(),
                consumo.getKwhConsumidos(), consumo.getTarifaPorKwh(),
                consumo.getFechaLimite().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                consumo.calcularImporte()));
        areaDetalle.setCaretPosition(0);
    }

    public void mostrarRecibo(Recibo r) {
        areaDetalle.setText(String.format(Locale.US,
                "RECIBO DE PAGO - SERVICIO DE LUZ%n"
                + "================================%n"
                + "Folio:           %d%n"
                + "Fecha de pago:   %s%n"
                + "Cliente:         %s%n"
                + "No. de servicio: %s%n"
                + "Periodo:         %s%n"
                + "Consumo:         %d kWh%n"
                + "Tarjeta:         %s%n"
                + "--------------------------------%n"
                + "TOTAL PAGADO:    $%,.2f%n"
                + "================================%n"
                + "¡Gracias por su pago!",
                r.getFolio(),
                r.getFechaPago().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                r.getCliente().getNombre(), r.getCliente().getNumeroServicio(),
                r.getConsumo().getPeriodo(), r.getConsumo().getKwhConsumidos(),
                r.getTarjetaEnmascarada(), r.getTotal()));
        areaDetalle.setCaretPosition(0);
    }

    public void limpiarDetalle() {
        areaDetalle.setText("");
        habilitarPago(false);
    }

    public void habilitarPago(boolean habilitado) {
        txtTarjeta.setEnabled(habilitado);
        btnPagar.setEnabled(habilitado);
        if (!habilitado) {
            txtTarjeta.setText("");
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
    }
}
