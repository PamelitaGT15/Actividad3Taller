package com.thermoaire.agenda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Capa simple de acceso a datos para proveedores usando CSV.
 */
public class ProveedorRepository {

    private static final String ARCHIVO = "proveedores.csv";

    private final List<Proveedor> proveedores = new ArrayList<>();

    public void cargarDesdeArchivo() {
        File file = new File(ARCHIVO);
        if (!file.exists()) {
            guardarEnArchivo();
            return;
        }
        proveedores.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Proveedor p = Proveedor.desdeLineaCsv(linea);
                if (p != null) {
                    proveedores.add(p);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer archivo de proveedores: " + e.getMessage());
        }
    }

    public void guardarEnArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Proveedor p : proveedores) {
                bw.write(p.toLineaCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar archivo de proveedores: " + e.getMessage());
        }
    }

    public void agregar(Proveedor proveedor) {
        proveedores.add(proveedor);
        guardarEnArchivo();
    }

    public List<Proveedor> obtenerTodos() {
        return new ArrayList<>(proveedores);
    }

    public List<Proveedor> buscar(String termino) {
        String t = termino.toLowerCase();
        List<Proveedor> resultado = new ArrayList<>();
        for (Proveedor p : proveedores) {
            if ((p.getNombre() != null && p.getNombre().toLowerCase().contains(t))
                    || (p.getEmpresa() != null && p.getEmpresa().toLowerCase().contains(t))
                    || (p.getGiro() != null && p.getGiro().toLowerCase().contains(t))) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public void actualizar(int indice, Proveedor proveedor) {
        if (indice >= 0 && indice < proveedores.size()) {
            proveedores.set(indice, proveedor);
            guardarEnArchivo();
        }
    }
}
