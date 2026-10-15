package model;

import java.time.LocalDate;

/** Datos de consumo. */
public class Consumo {
    private final String periodo;
    private final int kwhConsumidos;
    private final double tarifaPorKwh;
    private final LocalDate fechaLimite;

    public Consumo(String periodo, int kwhConsumidos, double tarifaPorKwh, LocalDate fechaLimite) {
        this.periodo = periodo;
        this.kwhConsumidos = kwhConsumidos;
        this.tarifaPorKwh = tarifaPorKwh;
        this.fechaLimite = fechaLimite;
    }

    public String getPeriodo() { return periodo; }
    public int getKwhConsumidos() { return kwhConsumidos; }
    public double getTarifaPorKwh() { return tarifaPorKwh; }
    public LocalDate getFechaLimite() { return fechaLimite; }

    /** Importe total a pagar = kWh consumidos x tarifa. */
    public double calcularImporte() {
        return kwhConsumidos * tarifaPorKwh;
    }
}
