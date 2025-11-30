package com.thermoaire.agenda;

/**
 * Representa a un cliente de Thermo Aire.
 * Campos basicos sin logica avanzada.
 */
public class Cliente {

    private String nombre;
    private String empresa;
    private String telefono;
    private String correo;
    private String notas;
    private String estatus; // ACTIVO o INACTIVO

    public Cliente(String nombre, String empresa, String telefono, String correo, String notas, String estatus) {
        this.nombre = nombre;
        this.empresa = empresa;
        this.telefono = telefono;
        this.correo = correo;
        this.notas = notas;
        this.estatus = estatus;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNotas() {
        return notas;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String toLineaCsv() {
        return String.join(",",
                escapar(nombre),
                escapar(empresa),
                escapar(telefono),
                escapar(correo),
                escapar(notas),
                escapar(estatus));
    }

    public String toLineaLista() {
        return nombre + " | " + empresa + " | Tel: " + telefono + " | " + estatus;
    }

    public static Cliente desdeLineaCsv(String linea) {
        String[] partes = linea.split(",", -1);
        if (partes.length < 6) {
            return null;
        }
        return new Cliente(
                desescapar(partes[0]),
                desescapar(partes[1]),
                desescapar(partes[2]),
                desescapar(partes[3]),
                desescapar(partes[4]),
                desescapar(partes[5])
        );
    }

    private static String escapar(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("\\", "\\\\").replace(",", "\\,");
    }

    private static String desescapar(String texto) {
        // Para este proyecto sencillo asumimos que no hay caracteres especiales complejos
        return texto.replace("\\,", ",").replace("\\\\", "\\");
    }
}
