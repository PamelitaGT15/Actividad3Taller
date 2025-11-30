package com.thermoaire.agenda;

/**
 * Representa a un proveedor de Thermo Aire.
 */
public class Proveedor {

    private String nombre;
    private String empresa;
    private String giro;
    private String telefono;
    private String correo;
    private String notas;
    private String estatus; // ACTIVO o INACTIVO

    public Proveedor(String nombre, String empresa, String giro, String telefono, String correo, String notas, String estatus) {
        this.nombre = nombre;
        this.empresa = empresa;
        this.giro = giro;
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

    public String getGiro() {
        return giro;
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
                escapar(giro),
                escapar(telefono),
                escapar(correo),
                escapar(notas),
                escapar(estatus));
    }

    public String toLineaLista() {
        return nombre + " | " + empresa + " | " + giro + " | Tel: " + telefono + " | " + estatus;
    }

    public static Proveedor desdeLineaCsv(String linea) {
        String[] partes = linea.split(",", -1);
        if (partes.length < 7) {
            return null;
        }
        return new Proveedor(
                desescapar(partes[0]),
                desescapar(partes[1]),
                desescapar(partes[2]),
                desescapar(partes[3]),
                desescapar(partes[4]),
                desescapar(partes[5]),
                desescapar(partes[6])
        );
    }

    private static String escapar(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("\\", "\\\\").replace(",", "\\,");
    }

    private static String desescapar(String texto) {
        return texto.replace("\\,", ",").replace("\\\\", "\\");
    }
}
