package model;

/** Cliente del servicio. */
public class Cliente {
    private final String numeroServicio;
    private final String nombre;
    private final String direccion;

    public Cliente(String numeroServicio, String nombre, String direccion) {
        this.numeroServicio = numeroServicio;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNumeroServicio() { return numeroServicio; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }

    @Override
    public String toString() {
        return numeroServicio + "  -  " + nombre;
    }
}
