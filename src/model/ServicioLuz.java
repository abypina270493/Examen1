package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Modelo principal: contiene los clientes y sus consumos
 * y ejecuta la lógica de negocio del caso de uso "Pagar servicio de Luz".
 */
public class ServicioLuz {
    private final List<Cliente> clientes = new ArrayList<>();
    private final Map<String, Consumo> consumos = new LinkedHashMap<>();
    private int siguienteFolio = 1001;

    public ServicioLuz() {
        cargarDatosDemo();
    }

    private void cargarDatosDemo() {
        LocalDate limite = LocalDate.of(2026, 10, 30);
        agregar("412345678901", "María Fernanda López Ruiz", "Calle Sonora 120, Navojoa", 320, 1.85, limite);
        agregar("412345678955", "José Luis Ramírez Soto", "Av. Guerrero 45, Navojoa", 215, 1.85, limite);
        agregar("412398765432", "Ana Patricia Valenzuela", "Blvd. Allende 890, Navojoa", 540, 2.10, limite);
        agregar("498765432100", "Carlos Eduardo Armenta", "Calle Rosales 33, Huatabampo", 180, 1.70, limite);
        agregar("498765432177", "Lucía Guadalupe Félix", "Calle Hidalgo 210, Etchojoa", 410, 2.05, limite);
        agregar("501234567890", "Ramón Alberto Cota", "Av. Obregón 17, Cd. Obregón", 275, 1.85, limite);
        agregar("501234500012", "Sofía Isabel Moroyoqui", "Calle Mina 76, Navojoa", 150, 1.70, limite);
        agregar("623456789012", "Héctor Manuel Quintero", "Calle Juárez 305, Álamos", 620, 2.30, limite);
    }

    private void agregar(String numero, String nombre, String dir, int kwh, double tarifa, LocalDate limite) {
        clientes.add(new Cliente(numero, nombre, dir));
        consumos.put(numero, new Consumo("Ago - Sep 2026", kwh, tarifa, limite));
    }

    /** Clientes cuyo número de servicio inicia con el criterio tecleado. */
    public List<Cliente> buscarClientes(String criterio) {
        List<Cliente> resultado = new ArrayList<>();
        if (criterio == null || criterio.isBlank()) {
            return resultado;
        }
        String c = criterio.trim();
        for (Cliente cliente : clientes) {
            if (cliente.getNumeroServicio().startsWith(c)) {
                resultado.add(cliente);
            }
        }
        return resultado;
    }

    public Consumo obtenerConsumo(Cliente cliente) {
        return consumos.get(cliente.getNumeroServicio());
    }

    /**
     * Realiza el pago y genera el recibo.
     * @throws IllegalArgumentException si la tarjeta no es válida.
     */
    public Recibo pagar(Cliente cliente, Tarjeta tarjeta) {
        if (!tarjeta.esValida()) {
            throw new IllegalArgumentException("El número de tarjeta debe tener 16 dígitos.");
        }
        Consumo consumo = obtenerConsumo(cliente);
        return new Recibo(siguienteFolio++, cliente, consumo, tarjeta.enmascarada(),
                LocalDateTime.now(), consumo.calcularImporte());
    }
}
