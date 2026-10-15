package controller;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;

import model.Cliente;
import model.Consumo;
import model.Recibo;
import model.ServicioLuz;
import model.Tarjeta;
import view.PagoLuzView;

/** Controlador del caso de uso "Pagar servicio de Luz". */
public class PagoLuzController {
    private final ServicioLuz modelo;
    private final PagoLuzView vista;
    private Cliente clienteActual;

    public PagoLuzController(ServicioLuz modelo, PagoLuzView vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.agregarListenerNumeroServicio(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { buscarClientes(); }
            public void removeUpdate(DocumentEvent e) { buscarClientes(); }
            public void changedUpdate(DocumentEvent e) { buscarClientes(); }
        });
        vista.agregarListenerSeleccionCliente(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarCliente(vista.getClienteSeleccionado());
            }
        });
        vista.agregarListenerPagar(e -> pagar());
    }

    /** Paso 1: al teclear el número de servicio se filtra la lista de clientes. */
    public void buscarClientes() {
        List<Cliente> coincidencias = modelo.buscarClientes(vista.getNumeroServicio());
        clienteActual = null;
        vista.mostrarClientes(coincidencias);
        vista.limpiarDetalle();
    }

    /** Pasos 2 y 3: se selecciona el cliente y se muestran sus datos de consumo. */
    public void seleccionarCliente(Cliente cliente) {
        clienteActual = cliente;
        if (cliente == null) {
            vista.limpiarDetalle();
            return;
        }
        Consumo consumo = modelo.obtenerConsumo(cliente);
        vista.mostrarConsumo(cliente, consumo);
        vista.habilitarPago(true);   // Paso 4: se solicita el número de tarjeta
    }

    /** Pasos 4 y 5: se paga con la tarjeta y se muestra el recibo. */
    public void pagar() {
        if (clienteActual == null) {
            vista.mostrarMensaje("Seleccione un cliente.");
            return;
        }
        try {
            Recibo recibo = modelo.pagar(clienteActual, new Tarjeta(vista.getTarjeta()));
            vista.mostrarRecibo(recibo);
            vista.habilitarPago(false);
        } catch (IllegalArgumentException ex) {
            vista.mostrarMensaje(ex.getMessage());
        }
    }
}
