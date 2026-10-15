package model;

import java.time.LocalDateTime;

/** Recibo de pago generado. */
public class Recibo {
    private final int folio;
    private final Cliente cliente;
    private final Consumo consumo;
    private final String tarjetaEnmascarada;
    private final LocalDateTime fechaPago;
    private final double total;

    public Recibo(int folio, Cliente cliente, Consumo consumo, String tarjetaEnmascarada,
                  LocalDateTime fechaPago, double total) {
        this.folio = folio;
        this.cliente = cliente;
        this.consumo = consumo;
        this.tarjetaEnmascarada = tarjetaEnmascarada;
        this.fechaPago = fechaPago;
        this.total = total;
    }

    public int getFolio() { return folio; }
    public Cliente getCliente() { return cliente; }
    public Consumo getConsumo() { return consumo; }
    public String getTarjetaEnmascarada() { return tarjetaEnmascarada; }
    public LocalDateTime getFechaPago() { return fechaPago; }
    public double getTotal() { return total; }
}
