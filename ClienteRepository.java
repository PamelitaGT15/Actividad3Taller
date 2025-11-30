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
 * Capa simple de acceso a datos para clientes usando CSV.
 */
public class ClienteRepository {

    private static final String ARCHIVO = "clientes.csv";

    private final List<Cliente> clientes = new ArrayList<>();

    public void cargarDesdeArchivo() {
        File file = new File(ARCHIVO);
        if (!file.exists()) {
            // EXT-04: si no existe, se crea vacio
            guardarEnArchivo();
            return;
        }
        clientes.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Cliente c = Cliente.desdeLineaCsv(linea);
                if (c != null) {
                    clientes.add(c);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer archivo de clientes: " + e.getMessage());
        }
    }

    public void guardarEnArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Cliente c : clientes) {
                bw.write(c.toLineaCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar archivo de clientes: " + e.getMessage());
        }
    }

    public void agregar(Cliente cliente) {
        clientes.add(cliente);
        guardarEnArchivo();
    }

    public List<Cliente> obtenerTodos() {
        return new ArrayList<>(clientes);
    }

    public List<Cliente> buscar(String termino) {
        String t = termino.toLowerCase();
        List<Cliente> resultado = new ArrayList<>();
        for (Cliente c : clientes) {
            if ((c.getNombre() != null && c.getNombre().toLowerCase().contains(t))
                    || (c.getEmpresa() != null && c.getEmpresa().toLowerCase().contains(t))
                    || (c.getTelefono() != null && c.getTelefono().toLowerCase().contains(t))) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public void actualizar(int indice, Cliente cliente) {
        if (indice >= 0 && indice < clientes.size()) {
            clientes.set(indice, cliente);
            guardarEnArchivo();
        }
    }

    public void eliminar(int indice) {
        if (indice >= 0 && indice < clientes.size()) {
            clientes.remove(indice);
            guardarEnArchivo();
        }
    }
}
