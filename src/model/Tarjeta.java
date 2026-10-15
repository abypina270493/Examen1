package model;

/** Tarjeta de pago. Solo se conserva el número para validarlo y enmascararlo. */
public class Tarjeta {
    private final String numero;

    public Tarjeta(String numero) {
        this.numero = numero == null ? "" : numero.replaceAll("[\\s-]", "");
    }

    /** Las tarjetas solo son válidas si tienen 16 dígitos. */
    public boolean esValida() {
        return numero.matches("\\d{16}");
    }

    public String ultimosCuatroDigitos() {
        return numero.substring(numero.length() - 4);
    }

    public String enmascarada() {
        return "**** **** **** " + ultimosCuatroDigitos();
    }
}
